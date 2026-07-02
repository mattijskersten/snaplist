package com.snaplist.data.ai

/**
 * Prompt and JSON schema for the listing analysis call. Kept free of Android
 * dependencies so it can be unit-tested on the JVM.
 */
object ListingPrompt {

    fun systemPrompt(country: String, currency: String): String = """
        You are drafting a resale listing for vinted.com from the user's photos of a single item.
        The seller is in $country and prices are in $currency.

        Look carefully at every photo: labels, brand tags, size tags, care/material tags,
        wear, stains, pilling, damage. Then produce a complete listing draft.

        Guidelines:
        - title: short and searchable, like real Vinted titles (brand + item type + key attribute), max 60 chars.
        - description: 2-5 short sentences, friendly, honest. Mention measurements only if visible.
          Always mention visible flaws or wear explicitly - misgraded items cause disputes and refunds.
        - brand: exactly as written on the label; null if no brand is visible.
        - category_path: the Vinted category hierarchy from broad to specific, in English,
          e.g. ["Women", "Clothing", "Dresses", "Midi dresses"]. 3-4 levels.
        - condition: one of the Vinted tiers, judged conservatively from the photos:
          new_with_tags (unworn, original tags visible), new_without_tags (unworn, no tags),
          very_good (lightly used, tiny signs of wear), good (used, visible wear or small flaws,
          stated in description), satisfactory (frequently used, obvious flaws, stated in description).
        - size: from the size tag if visible (e.g. "M", "EU 38", "W32 L34"); null if not visible.
        - colors: 1-2 dominant colors (Vinted allows max two).
        - material: from the care tag if visible; null otherwise.
        - price_suggested / price_low / price_high: realistic secondhand prices in $currency for this
          brand, item type and condition on Vinted (buyers expect bargains; typical range is
          10-40% of new price depending on brand tier and condition). price_reasoning: one sentence.
        - defects_noted: each visible flaw as a short phrase; empty array if none.
    """.trimIndent()

    const val USER_INSTRUCTION =
        "Create the Vinted listing draft for the item in these photos."

    /** Nullable string as anyOf — structured outputs doesn't accept type:["string","null"]. */
    private val nullableString = mapOf(
        "anyOf" to listOf(mapOf("type" to "string"), mapOf("type" to "null")),
    )

    /** JSON schema for output_config (additionalProperties=false, all fields required). */
    val schema: Map<String, Any?> = mapOf(
        "type" to "object",
        "properties" to mapOf(
            "title" to mapOf("type" to "string"),
            "description" to mapOf("type" to "string"),
            "brand" to nullableString,
            "category_path" to mapOf("type" to "array", "items" to mapOf("type" to "string")),
            "condition" to mapOf(
                "type" to "string",
                "enum" to listOf(
                    "new_with_tags", "new_without_tags", "very_good", "good", "satisfactory",
                ),
            ),
            "size" to nullableString,
            "colors" to mapOf("type" to "array", "items" to mapOf("type" to "string")),
            "material" to nullableString,
            "price_suggested" to mapOf("type" to "number"),
            "price_low" to mapOf("type" to "number"),
            "price_high" to mapOf("type" to "number"),
            "price_reasoning" to mapOf("type" to "string"),
            "defects_noted" to mapOf("type" to "array", "items" to mapOf("type" to "string")),
        ),
        "required" to listOf(
            "title", "description", "brand", "category_path", "condition", "size",
            "colors", "material", "price_suggested", "price_low", "price_high",
            "price_reasoning", "defects_noted",
        ),
        "additionalProperties" to false,
    )
}
