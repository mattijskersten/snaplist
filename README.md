# SnapList

Photograph an item → Claude drafts the whole Vinted listing → review → assisted handoff into the Vinted app.

Listing on Vinted normally means photographing, categorizing, describing, grading condition, and pricing by hand. SnapList collapses that: one AI call turns your photos into a complete draft (title, description, brand, Vinted category path, condition tier, size, colors, material, and a realistic secondhand price with range), which you tweak and then push into the official Vinted app in ~30 seconds.

Vinted has no public listing API, so SnapList deliberately does **not** auto-post. Instead the handoff screen shares your photos straight into the Vinted app and gives you tap-to-copy chips for every field — fast, and no ToS risk.

## Setup

1. Build & install:
   ```sh
   ./gradlew installDebug     # device connected with USB debugging
   ```
   (Needs the Android SDK; `local.properties` points at `~/Android/Sdk`.)
2. In the app, open **Settings** and paste your Anthropic API key (from
   [console.anthropic.com](https://console.anthropic.com)). The key is stored
   encrypted on-device and calls go directly from your phone to the Claude API.
3. Optionally set your Vinted country/currency (used for pricing and category language)
   and the Claude model (default `claude-opus-4-8`).

## Flow

1. **+** → photograph the item (or pick from gallery). Include the brand label,
   size tag, and any flaws — the AI reads them.
2. **Done** → analysis runs (one vision call with all photos, structured JSON output).
3. **Review** → edit anything; the AI's price range and reasoning are shown under the price field.
4. **Post to Vinted** → don't pre-open anything in Vinted; the handoff drives it:
   sharing the photos into Vinted opens Vinted's new-listing flow with the photos
   attached, then you copy/paste each field (split-screen recommended) and mark as
   posted. If sharing doesn't land in Vinted's sell flow on your device, use
   "Save photos to gallery instead" and pick them from Pictures/SnapList inside
   Vinted's own listing form.

## Notes

- Photos are downscaled to ≤1568 px / JPEG q80 before upload; an analysis with
  ~5 photos costs a few cents.
- The AI is prompted to grade condition conservatively and to state visible flaws
  in the description (misgraded condition is the top cause of Vinted disputes).
- Drafts live in a local Room database; photos in app-private storage, exposed to
  Vinted only through a FileProvider at share time.

## Tests

```sh
./gradlew test          # JVM unit tests: schema/prompt/parsing/mapping
./gradlew assembleDebug
```
