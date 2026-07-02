package com.snaplist

import com.snaplist.data.settings.VintedMarkets
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class VintedMarketsTest {

    @Test
    fun `country lookup returns its market defaults`() {
        val nl = VintedMarkets.forCountry("Netherlands")!!
        assertEquals("EUR", nl.currency)
        assertEquals("Dutch", nl.language)
        assertNull(VintedMarkets.forCountry("Atlantis"))
    }

    @Test
    fun `every market currency and language appears in the dropdown lists`() {
        VintedMarkets.all.forEach { market ->
            assertTrue(market.currency, VintedMarkets.currencies.contains(market.currency))
            assertTrue(market.language, VintedMarkets.languages.contains(market.language))
        }
    }

    @Test
    fun `countries are unique`() {
        assertEquals(VintedMarkets.countries.size, VintedMarkets.countries.toSet().size)
    }
}
