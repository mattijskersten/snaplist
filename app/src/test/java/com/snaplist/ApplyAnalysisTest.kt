package com.snaplist

import com.snaplist.data.ai.ListingAnalysis
import com.snaplist.data.db.Condition
import com.snaplist.data.db.DraftStatus
import com.snaplist.data.db.ListingDraft
import com.snaplist.ui.review.ReviewViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ApplyAnalysisTest {

    private val analysis = ListingAnalysis(
        title = "Zara Midi Dress M Black",
        description = "Black Zara midi dress. Worn twice.",
        brand = "Zara",
        categoryPath = listOf("Women", "Clothing", "Dresses", "Midi dresses"),
        condition = "very_good",
        size = "M",
        colors = listOf("Black", "White", "Red"), // model over-supplied: must clamp to 2
        material = "Polyester",
        priceSuggested = 12.0,
        priceLow = 9.0,
        priceHigh = 16.0,
        priceReasoning = "Zara dresses in very good condition sell around 9-16 EUR.",
        defectsNoted = emptyList(),
    )

    @Test
    fun `maps analysis into draft and marks ready`() {
        val result = ReviewViewModel.applyAnalysis(ListingDraft(id = 1), analysis)
        assertEquals(DraftStatus.READY, result.status)
        assertEquals("Zara Midi Dress M Black", result.title)
        assertEquals(Condition.VERY_GOOD, result.condition)
        assertEquals(listOf("Black", "White"), result.colors) // clamped to Vinted's max of 2
        assertEquals(12.0, result.price!!, 0.001) // final price defaults to suggestion
    }

    @Test
    fun `condition strings map to Vinted tiers`() {
        assertEquals(Condition.NEW_WITH_TAGS, ReviewViewModel.conditionFrom("new_with_tags"))
        assertEquals(Condition.NEW_WITHOUT_TAGS, ReviewViewModel.conditionFrom("new_without_tags"))
        assertEquals(Condition.VERY_GOOD, ReviewViewModel.conditionFrom("very_good"))
        assertEquals(Condition.GOOD, ReviewViewModel.conditionFrom("good"))
        assertEquals(Condition.SATISFACTORY, ReviewViewModel.conditionFrom("satisfactory"))
        assertNull(ReviewViewModel.conditionFrom("mint"))
    }
}
