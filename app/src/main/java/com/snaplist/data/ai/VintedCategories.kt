package com.snaplist.data.ai

import com.snaplist.data.settings.VintedMarkets

/**
 * Builds the category-path section of the analysis prompt from the verified
 * catalog taxonomy in [VintedCatalog].
 *
 * Vinted's category tree is one global structure (catalog IDs are shared by
 * every market; only display names are translated), so the prompt pins the top
 * two levels to the exact names the seller sees in their market's Vinted app
 * and lets the model guess only the deeper leaf. Category names are shown in
 * the market's own language — the path has to match the market's catalog UI —
 * independent of the language chosen for the listing text.
 */
object VintedCategories {

    /**
     * Category-path prompt section for the given market, or null when the
     * market's names aren't covered (caller falls back to a generic line).
     */
    fun guidanceFor(country: String): String? {
        val market = VintedMarkets.forCountry(country) ?: return null
        val names = namesFor(market.language, country) ?: return null
        val tops = VintedCatalog.tree.filter { it.availableIn(country) }

        val topLine = tops.joinToString("; ") { names.getValue(it.id) }
        val childLines = tops.joinToString("\n") { top ->
            val children = top.children
                .filter { it.availableIn(country) }
                .joinToString("; ") { names.getValue(it.id) }
            "  * under ${names.getValue(top.id)}: $children"
        }

        return "- category_path: the Vinted category hierarchy from broad to specific, in ${market.language},\n" +
            "  exactly matching the catalog on Vinted ${country}. 3-4 levels.\n" +
            "  The FIRST element must be one of these top-level categories, verbatim:\n" +
            "  $topLine.\n" +
            "  The SECOND element must be the matching one of these, verbatim:\n" +
            "$childLines\n" +
            "  Deeper levels: your best guess in ${market.language}, matching Vinted's naming style."
    }

    private fun CatalogNode.availableIn(country: String) =
        onlyIn == null || country in onlyIn

    /** id → display name for the market, or null if the language isn't covered. */
    private fun namesFor(language: String, country: String): Map<Int, String>? {
        val base = mutableMapOf<Int, String>()
        VintedCatalog.tree.forEach { top ->
            base[top.id] = top.name
            top.children.forEach { base[it.id] = it.name }
        }
        if (language != "English") {
            val translated = VintedCatalog.translations[language] ?: return null
            base.putAll(translated)
        }
        VintedCatalog.countryOverrides[country]?.let { base.putAll(it) }
        return base
    }
}
