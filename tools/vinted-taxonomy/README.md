# Vinted taxonomy tooling

Regenerates `app/src/main/java/com/snaplist/data/ai/VintedCatalog.kt` from the
live Vinted market sites. Vinted occasionally reorganizes its catalog, so
re-run this periodically (or when users report category mismatches) and diff
the result.

The catalog tree is one global structure — catalog IDs are shared across every
market, only display names are translated. The homepage of each market embeds
the full tree in its Next.js flight payload; `extract_tree.rb` pulls out the
top two levels, `gen_catalog_kt.rb` merges one domain per language into the
Kotlin file.

## Regenerate

```sh
cd /tmp/vinted-taxonomy && mkdir -p . && cd $_

UA="Mozilla/5.0 (X11; Linux x86_64; rv:127.0) Gecko/20100101 Firefox/127.0"
# one domain per language, plus be (Belgian French bulky-item names),
# com (US English overrides), and any extra domains you want to cross-check
for d in uk de fr be lu cz dk fi gr hr hu it lt nl pl pt ro sk es se com at ie; do
  dom=$d; [ $d = uk ] && dom=co.uk
  curl -s -m 60 -A "$UA" "https://www.vinted.$dom/" -o "$d.html" &
done; wait

for d in *.html; do ruby extract_tree.rb "$d" > "${d%.html}.json"; done

ruby gen_catalog_kt.rb . path/to/app/src/main/java/com/snaplist/data/ai/VintedCatalog.kt
```

Then run the unit tests (`./gradlew test`) — `VintedCatalogTest` asserts the
structural invariants and every market still resolving.

## Notes

- vinted.ca is bot-protected; Canada reuses the US English overrides
  (unverified).
- "Furniture" (3154) and "Large appliances" (3475) exist only in markets with
  bulky-item shipping (UK, NL, LT, BE as of 2026-07); `gen_catalog_kt.rb`
  hardcodes that list — re-check it when regenerating (the structural
  comparison in the issue-#1 notes shows how).
- vinted.se displays "Gold" for Golf (id 4339) — Vinted's own typo, kept
  verbatim because it is what the seller sees.
