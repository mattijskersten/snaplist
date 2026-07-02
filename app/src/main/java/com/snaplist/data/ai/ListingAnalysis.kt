package com.snaplist.data.ai

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * The structured listing draft Claude returns. Field names match the JSON schema
 * sent via output_config, so the response is guaranteed to parse.
 */
@Serializable
data class ListingAnalysis(
    val title: String,
    val description: String,
    val brand: String? = null,
    @SerialName("category_path") val categoryPath: List<String> = emptyList(),
    val condition: String,
    val size: String? = null,
    val colors: List<String> = emptyList(),
    val material: String? = null,
    @SerialName("price_suggested") val priceSuggested: Double,
    @SerialName("price_low") val priceLow: Double,
    @SerialName("price_high") val priceHigh: Double,
    @SerialName("price_reasoning") val priceReasoning: String,
    @SerialName("defects_noted") val defectsNoted: List<String> = emptyList(),
) {
    companion object {
        private val json = Json { ignoreUnknownKeys = true }

        fun parse(text: String): ListingAnalysis = json.decodeFromString(serializer(), text.trim())
    }
}
