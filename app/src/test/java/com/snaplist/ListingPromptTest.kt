package com.snaplist

import com.snaplist.data.ai.ListingPrompt
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ListingPromptTest {

    @Test
    fun `system prompt embeds country and currency`() {
        val prompt = ListingPrompt.systemPrompt("Germany", "EUR")
        assertTrue(prompt.contains("Germany"))
        assertTrue(prompt.contains("EUR"))
        assertTrue(prompt.contains("vinted.com"))
    }

    @Test
    fun `schema is a closed object requiring every field`() {
        val schema = ListingPrompt.schema
        assertEquals("object", schema["type"])
        assertEquals(false, schema["additionalProperties"])

        @Suppress("UNCHECKED_CAST")
        val properties = schema["properties"] as Map<String, Any?>
        @Suppress("UNCHECKED_CAST")
        val required = schema["required"] as List<String>
        assertEquals(properties.keys, required.toSet())
    }

    @Test
    fun `condition enum matches Vinted tiers`() {
        @Suppress("UNCHECKED_CAST")
        val properties = ListingPrompt.schema["properties"] as Map<String, Any?>
        @Suppress("UNCHECKED_CAST")
        val condition = properties["condition"] as Map<String, Any?>
        assertEquals(
            listOf("new_with_tags", "new_without_tags", "very_good", "good", "satisfactory"),
            condition["enum"],
        )
    }
}
