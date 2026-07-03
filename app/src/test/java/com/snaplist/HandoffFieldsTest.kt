package com.snaplist

import com.snaplist.data.db.Condition
import com.snaplist.data.db.ListingDraft
import com.snaplist.ui.handoff.handoffFields
import org.junit.Assert.assertEquals
import org.junit.Test

class HandoffFieldsTest {

    @Test
    fun `full draft yields all fields in form order`() {
        val draft = ListingDraft(
            title = "Levi's 501 W32",
            description = "Great jeans",
            brand = "Levi's",
            categoryPath = listOf("Men", "Clothing", "Jeans"),
            condition = Condition.VERY_GOOD,
            size = "W32 L34",
            colors = listOf("Blue"),
            material = "Denim",
            price = 25.0,
        )
        assertEquals(
            listOf("Title", "Description", "Brand", "Category", "Condition", "Size", "Colors", "Material", "Price"),
            draft.handoffFields().map { it.first },
        )
        assertEquals("Men › Clothing › Jeans", draft.handoffFields()[3].second)
        assertEquals("Very good", draft.handoffFields()[4].second)
    }

    @Test
    fun `blank optional fields are omitted`() {
        val draft = ListingDraft(title = "T", description = "D")
        assertEquals(listOf("Title", "Description"), draft.handoffFields().map { it.first })
    }
}
