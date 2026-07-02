package com.snaplist.data.ai

/**
 * Per-market category taxonomy anchors for the analysis prompt.
 *
 * Where we have verified Vinted's real category tree, the prompt pins the top
 * levels to the exact names the seller sees in their Vinted app, and the model
 * only guesses the deeper leaf. Markets without an anchor fall back to a
 * generic best-guess instruction.
 *
 * Currently anchored: Poland — full top level + complete second level for all
 * nine trees (verified against vinted.pl, 2026-07-02; catalog roots: Kobiety 1904,
 * Mężczyźni 5, Przedmioty designerskie 2993, Dzieci 1193, Dom 1918,
 * Elektronika 2994, Rozrywka 2309, Hobby i kolekcjonerstwo 4824, Sport 4332).
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
          * under Przedmioty designerskie: Damskie przedmioty designerskie;
            Męskie przedmioty designerskie
          * under Dzieci: Ubrania dla dziewczynek; Ubrania dla chłopców; Zabawki;
            Pielęgnacja i karmienie; Wózki spacerowe, nosidełka i foteliki samochodowe;
            Akcesoria do sypialni; Meble i dekoracje; Kąpiel i przewijanie;
            Artykuły szkolne; Zdrowie i ciąża; Blokady i zabezpieczenia;
            Inne przedmioty dla dzieci
          * under Dom: Akcesoria i ozdoby; Akcesoria stołowe; Tekstylia; Zwierzęta;
            Narzędzia i majsterkowanie; Uroczystości i święta; Akcesoria kuchenne;
            Małe AGD kuchenne; Artykuły biurowe; Wyposażenie ogrodowe;
            AGD i akcesoria do sprzątania; Naczynia do gotowania i pieczenia
          * under Elektronika: Gry wideo i konsole; Telefony komórkowe i komunikacja;
            Komputery i akcesoria; Audio i słuchawki; Urządzenia do pielęgnacji urody;
            Aparaty fotograficzne i akcesoria; Urządzenia ubieralne;
            Tablety, czytniki e-booków i akcesoria; Telewizor i kino domowe;
            Inne urządzenia i akcesoria
          * under Rozrywka: Książki; Muzyka; Wideo; Czasopisma
          * under Hobby i kolekcjonerstwo: Karty kolekcjonerskie; Plastyka i rękodzieło;
            Puzzle; Gry planszowe; Pamiątki kolekcjonerskie; Monety i banknoty;
            Przechowywanie przedmiotów kolekcjonerskich; Instrumenty i sprzęt muzyczny;
            Znaczki pocztowe; Pocztówki; Gry stołowe i bitewne; Akcesoria do gier
          * under Sport: Jazda na rowerze; Jazda konna; Fitness, bieganie i joga;
            Sporty outdoorowe; Sporty zimowe; Sporty wodne; Deskorolki i hulajnogi;
            Sporty zespołowe; Boks i sztuki walki; Sporty rakietowe;
            Gry rekreacyjne; Golf
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
