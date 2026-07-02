package com.snaplist

import com.snaplist.data.ai.VintedCatalog
import com.snaplist.data.ai.VintedCategories
import com.snaplist.data.settings.VintedMarkets
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class VintedCatalogTest {

    @Test
    fun `canonical tree has the verified shape`() {
        assertEquals(9, VintedCatalog.tree.size)
        assertEquals(75, VintedCatalog.tree.sumOf { it.children.size })
        // Root ids verified against the live sites (identical in every market).
        assertEquals(
            listOf(1904, 5, 2993, 1193, 1918, 2994, 2309, 4824, 4332),
            VintedCatalog.tree.map { it.id },
        )
    }

    @Test
    fun `every listing language is either canonical English or fully translated`() {
        val markets = VintedMarkets.all
        markets.map { it.language }.distinct().filter { it != "English" }.forEach { language ->
            val table = VintedCatalog.translations[language]
            assertNotNull("missing translation table for $language", table)
            // Every node available in some market of this language must have a name.
            val countries = markets.filter { it.language == language }.map { it.country }
            VintedCatalog.tree.forEach { top ->
                assertTrue("$language missing top ${top.id}", table!!.containsKey(top.id))
                top.children
                    .filter { it.onlyIn == null || countries.any { c -> c in it.onlyIn } }
                    .forEach { assertTrue("$language missing ${it.id}", table.containsKey(it.id)) }
            }
        }
    }

    @Test
    fun `every market resolves to anchored guidance`() {
        VintedMarkets.all.forEach { market ->
            val guidance = VintedCategories.guidanceFor(market.country)
            assertNotNull("no guidance for ${market.country}", guidance)
            assertTrue(guidance!!.contains("verbatim"))
            assertTrue(guidance.contains("in ${market.language}"))
            // 9 top-level bullets
            assertEquals(9, guidance.lines().count { it.trimStart().startsWith("* under") })
        }
    }

    @Test
    fun `bulky-item categories are limited to their markets`() {
        val furniture = VintedCatalog.tree.first { it.id == 1918 }.children.first { it.id == 3154 }
        assertEquals(
            setOf("Belgium", "Lithuania", "Netherlands", "United Kingdom"),
            furniture.onlyIn,
        )
        assertTrue(VintedCategories.guidanceFor("United Kingdom")!!.contains("Furniture"))
        assertTrue(VintedCategories.guidanceFor("Belgium")!!.contains("Mobilier"))
        assertTrue(!VintedCategories.guidanceFor("France")!!.contains("Mobilier"))
    }
}
