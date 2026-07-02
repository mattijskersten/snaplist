# Extracts Vinted's catalog tree from a homepage HTML file (Next.js flight
# payload) and emits the top two levels as JSON: [{id, name, children:[{id, name}]}]
#
# Usage: ruby extract_tree.rb <homepage.html>
require 'json'

html = File.read(ARGV[0], encoding: 'UTF-8')

# Locate the flight chunk containing the catalog tree (escaped JSON inside a
# JS string literal: self.__next_f.push([1,"...catalogTree\":[..."])).
marker = html.index('catalogTree\\":[')
abort 'catalogTree not found' unless marker
chunk_start = html.rindex('self.__next_f.push([1,"', marker)
abort 'chunk start not found' unless chunk_start
str_start = chunk_start + 'self.__next_f.push([1,'.length
str_end = html.index('"])</script>', marker)
abort 'chunk end not found' unless str_end

payload = JSON.parse(html[str_start..str_end]) # unescape the JS string literal

bytes = payload.b # byte-wise scan: char indexing on UTF-8 strings is O(n)
idx = bytes.index('"catalogTree":['.b)
abort 'catalogTree not in chunk' unless idx
start = idx + '"catalogTree":'.length

# Extract the balanced JSON array starting at `start`.
QUOTE = '"'.ord; BSLASH = '\\'.ord; LBRACK = '['.ord; RBRACK = ']'.ord
depth = 0
in_str = false
esc = false
i = start
loop do
  c = bytes.getbyte(i)
  abort 'unbalanced (tree spans chunks?)' if c.nil?
  if in_str
    if esc then esc = false
    elsif c == BSLASH then esc = true
    elsif c == QUOTE then in_str = false
    end
  else
    case c
    when QUOTE then in_str = true
    when LBRACK then depth += 1
    when RBRACK
      depth -= 1
      break if depth.zero?
    end
  end
  i += 1
end

tree = JSON.parse(bytes[start..i].force_encoding('UTF-8'))

slim = tree.map do |top|
  {
    id: top['id'],
    name: top['title'],
    children: (top['catalogs'] || []).map { |c| { id: c['id'], name: c['title'] } },
  }
end

puts JSON.pretty_generate(slim)
