package com.snaplist

import com.snaplist.data.ai.ListingPrompt
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ListingPromptTest {

    @Test
    fun `system prompt embeds country, currency and language`() {
        val prompt = ListingPrompt.systemPrompt("Germany", "EUR", "German")
        assertTrue(prompt.contains("Germany"))
        assertTrue(prompt.contains("EUR"))
        assertTrue(prompt.contains("in German"))
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
    fun `Poland gets anchored Polish category taxonomy`() {
        val prompt = ListingPrompt.systemPrompt("Poland", "PLN", "Polish")
        // Top level anchored verbatim (verified against vinted.pl)
        assertTrue(prompt.contains("Kobiety"))
        assertTrue(prompt.contains("Przedmioty designerskie"))
        assertTrue(prompt.contains("Hobby i kolekcjonerstwo"))
        // Second level anchored
        assertTrue(prompt.contains("Ubrania dla dziewczynek"))
        // Generic English instruction replaced, placeholder resolved
        assertTrue(!prompt.contains("Midi dresses"))
        assertTrue(!prompt.contains("{CATEGORY_GUIDANCE}"))
    }

    @Test
    fun `markets without an anchor keep the generic category instruction`() {
        val prompt = ListingPrompt.systemPrompt("Netherlands", "EUR", "Dutch")
        assertTrue(prompt.contains("Midi dresses"))
        assertTrue(!prompt.contains("Kobiety"))
        assertTrue(!prompt.contains("{CATEGORY_GUIDANCE}"))
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
