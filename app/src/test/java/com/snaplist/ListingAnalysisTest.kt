package com.snaplist

import com.snaplist.data.ai.ListingAnalysis
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ListingAnalysisTest {

    // Canned response matching the output_config schema.
    private val fixture = """
        {
          "title": "Levi's 501 Jeans W32 L34 Dark Blue",
          "description": "Classic Levi's 501 in dark blue. Lightly worn, no flaws. Slight fading at the knees.",
          "brand": "Levi's",
          "category_path": ["Men", "Clothing", "Jeans", "Straight jeans"],
          "condition": "very_good",
          "size": "W32 L34",
          "colors": ["Blue"],
          "material": "100% cotton",
          "price_suggested": 24.0,
          "price_low": 18.0,
          "price_high": 32.0,
          "price_reasoning": "501s in very good condition typically sell for 18-32 EUR on Vinted.",
          "defects_noted": ["slight fading at knees"]
        }
    """.trimIndent()

    @Test
    fun `parses full response`() {
        val a = ListingAnalysis.parse(fixture)
        assertEquals("Levi's 501 Jeans W32 L34 Dark Blue", a.title)
        assertEquals("Levi's", a.brand)
        assertEquals(listOf("Men", "Clothing", "Jeans", "Straight jeans"), a.categoryPath)
        assertEquals("very_good", a.condition)
        assertEquals("W32 L34", a.size)
        assertEquals(24.0, a.priceSuggested, 0.001)
        assertEquals(listOf("slight fading at knees"), a.defectsNoted)
    }

    @Test
    fun `parses nullable fields as null`() {
        val json = """
            {
              "title": "T-shirt", "description": "A t-shirt.",
              "brand": null, "category_path": ["Men","Clothing","T-shirts"],
              "condition": "good", "size": null, "colors": ["White"], "material": null,
              "price_suggested": 5, "price_low": 3, "price_high": 8,
              "price_reasoning": "Basic unbranded tee.", "defects_noted": []
            }
        """.trimIndent()
        val a = ListingAnalysis.parse(json)
        assertNull(a.brand)
        assertNull(a.size)
        assertNull(a.material)
        assertTrue(a.defectsNoted.isEmpty())
    }

    @Test
    fun `tolerates surrounding whitespace`() {
        val a = ListingAnalysis.parse("\n  $fixture  \n")
        assertEquals("very_good", a.condition)
    }
}
