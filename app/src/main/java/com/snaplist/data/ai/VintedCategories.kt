package com.snaplist.data.ai

/**
 * Per-market category taxonomy anchors for the analysis prompt.
 *
 * Where we have verified Vinted's real category tree, the prompt pins the top
 * levels to the exact names the seller sees in their Vinted app, and the model
 * only guesses the deeper leaf. Markets without an anchor fall back to a
 * generic best-guess instruction.
 *
 * Currently anchored: Poland (verified against vinted.pl, 2026-07-02).
 * Other markets: https://github.com/mattijskersten/snaplist/issues — taxonomy
 * extension tracked there.
 */
object VintedCategories {

    private val POLAND_GUIDANCE = """
        - category_path: the Vinted category hierarchy from broad to specific, in Polish,
          exactly matching Vinted Poland's catalog. 3-4 levels.
          The FIRST element must be one of these top-level categories, verbatim:
          Kobiety; Mężczyźni; Przedmioty designerskie; Dzieci; Dom; Elektronika;
          Rozrywka; Hobby i kolekcjonerstwo; Sport.
          The SECOND element must be the matching one of these, verbatim:
          * under Kobiety: Ubrania; Obuwie; Akcesoria; Torby; Kosmetyki
          * under Mężczyźni: Ubrania; Obuwie; Akcesoria, dodatki; Kosmetyki
          * under Dzieci: Ubrania dla dziewczynek; Ubrania dla chłopców; Zabawki;
            Pielęgnacja i karmienie; Wózki spacerowe, nosidełka i foteliki samochodowe;
            Akcesoria do sypialni; Meble i dekoracje; Kąpiel i przewijanie;
            Artykuły szkolne; Zdrowie i ciąża; Blokady i zabezpieczenia;
            Inne przedmioty dla dzieci
          * under other top-level categories: your best guess in Polish.
          Deeper levels: your best guess in Polish, matching Vinted's naming style.
    """.trimIndent()

    /**
     * Category-path prompt section for the given market, or null when the
     * market has no verified anchor (caller falls back to the generic line).
     */
    fun guidanceFor(country: String): String? = when (country) {
        "Poland" -> POLAND_GUIDANCE
        else -> null
    }
}
