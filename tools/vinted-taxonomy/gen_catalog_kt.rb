# Generates VintedCatalog.kt from the extracted per-domain catalog JSONs.
# Usage: ruby gen_catalog_kt.rb <jsonDir> <outFile>
require 'json'

DIR = ARGV[0]
OUT = ARGV[1]

LANG_DOMAIN = {
  'German' => 'de', 'French' => 'fr', 'Croatian' => 'hr', 'Czech' => 'cz',
  'Danish' => 'dk', 'Finnish' => 'fi', 'Greek' => 'gr', 'Hungarian' => 'hu',
  'Italian' => 'it', 'Lithuanian' => 'lt', 'Dutch' => 'nl', 'Polish' => 'pl',
  'Portuguese' => 'pt', 'Romanian' => 'ro', 'Slovak' => 'sk', 'Spanish' => 'es',
  'Swedish' => 'se',
}.sort.to_h

# Markets that carry the bulky-item categories (Furniture 3154, Large
# appliances 3475), from per-domain structural comparison.
BULKY_IDS = [3154, 3475]
BULKY_MARKETS = ['Belgium', 'Lithuania', 'Netherlands', 'United Kingdom']

def load(d) = JSON.parse(File.read(File.join(DIR, "#{d}.json")))
def esc(s) = s.gsub('\\', '\\\\\\\\').gsub('"', '\"').gsub('$', '${\'$\'}')

uk = load('uk')
com_flat = load('com').flat_map { |n| [[n['id'], n['name']]] + n['children'].map { |c| [c['id'], c['name']] } }.to_h
uk_flat = uk.flat_map { |n| [[n['id'], n['name']]] + n['children'].map { |c| [c['id'], c['name']] } }.to_h
us_overrides = uk_flat.keys.select { |k| com_flat[k] && com_flat[k] != uk_flat[k] }.map { |k| [k, com_flat[k]] }

k = +''
k << <<~HEADER
  package com.snaplist.data.ai

  /**
   * Vinted's catalog taxonomy: one canonical tree (IDs shared by every Vinted
   * market) plus per-language display names.
   *
   * GENERATED from the live Vinted sites on #{Time.now.strftime('%Y-%m-%d')} — do not edit by
   * hand; regenerate with tools/vinted-taxonomy (extract_tree.rb + gen_catalog_kt.rb).
   * Sources: one domain per language (#{LANG_DOMAIN.values.join(', ')}), canonical
   * English from vinted.co.uk, US English overrides from vinted.com. Structure
   * verified identical across all 23 reachable market domains except the
   * bulky-item categories noted on the nodes below (vinted.ca is bot-protected;
   * Canada assumes US naming, unverified).
   */
  data class CatalogNode(
      val id: Int,
      val name: String,
      /** Markets carrying this node; null = available everywhere. */
      val onlyIn: Set<String>? = null,
      val children: List<CatalogNode> = emptyList(),
  )

  object VintedCatalog {

      /** Top two catalog levels, canonical (UK) English names. */
      val tree: List<CatalogNode> = listOf(
HEADER

uk.each do |top|
  k << "        CatalogNode(\n"
  k << "            id = #{top['id']}, name = \"#{esc(top['name'])}\",\n"
  k << "            children = listOf(\n"
  top['children'].each do |c|
    only = BULKY_IDS.include?(c['id']) ? ", onlyIn = setOf(#{BULKY_MARKETS.map { |m| "\"#{m}\"" }.join(', ')})" : ''
    k << "                CatalogNode(id = #{c['id']}, name = \"#{esc(c['name'])}\"#{only}),\n"
  end
  k << "            ),\n"
  k << "        ),\n"
end

k << <<-MID
    )

    /**
     * Display names by listing language (English = canonical names above).
     * Key: catalog id. Verified against the live market sites.
     */
    val translations: Map<String, Map<Int, String>> = mapOf(
MID

LANG_DOMAIN.each do |lang, dom|
  flat = load(dom).flat_map { |n| [[n['id'], n['name']]] + n['children'].map { |c| [c['id'], c['name']] } }.to_h
  if lang == 'French'
    # vinted.fr lacks the bulky-item nodes Belgium carries; take their French
    # names from vinted.be (names verified identical for all shared ids).
    be = load('be').flat_map { |n| [[n['id'], n['name']]] + n['children'].map { |c| [c['id'], c['name']] } }.to_h
    flat = be.merge(flat)
  end
  k << "        \"#{lang}\" to mapOf(\n"
  uk.each do |top|
    ids = [top['id']] + top['children'].map { |c| c['id'] }
    present = ids.select { |id| flat[id] }
    k << '            ' + present.map { |id| "#{id} to \"#{esc(flat[id])}\"" }.join(', ') + ",\n"
  end
  k << "        ),\n"
end

k << <<-US
    )

    /**
     * Per-country name overrides on top of the language table (US English
     * differs from UK English in a handful of names; Canada is assumed to
     * match the US — vinted.ca could not be verified).
     */
    private val usEnglishOverrides: Map<Int, String> = mapOf(
US
us_overrides.each { |id, name| k << "        #{id} to \"#{esc(name)}\",\n" }
k << <<-FOOTER
    )

    val countryOverrides: Map<String, Map<Int, String>> = mapOf(
        "United States" to usEnglishOverrides,
        "Canada" to usEnglishOverrides,
    )
}
FOOTER

File.write(OUT, k)
puts "wrote #{OUT} (#{k.bytesize} bytes, #{us_overrides.size} US overrides)"
