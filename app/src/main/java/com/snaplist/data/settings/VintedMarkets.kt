package com.snaplist.data.settings

/**
 * Countries Vinted operates in, with the currency and default listing
 * language for each market. Picking a country pre-fills the other two;
 * both stay individually overridable.
 */
data class VintedMarket(val country: String, val currency: String, val language: String)

object VintedMarkets {
    val all: List<VintedMarket> = listOf(
        VintedMarket("Austria", "EUR", "German"),
        VintedMarket("Belgium", "EUR", "French"),
        VintedMarket("Canada", "CAD", "English"),
        VintedMarket("Croatia", "EUR", "Croatian"),
        VintedMarket("Czech Republic", "CZK", "Czech"),
        VintedMarket("Denmark", "DKK", "Danish"),
        VintedMarket("Finland", "EUR", "Finnish"),
        VintedMarket("France", "EUR", "French"),
        VintedMarket("Germany", "EUR", "German"),
        VintedMarket("Greece", "EUR", "Greek"),
        VintedMarket("Hungary", "HUF", "Hungarian"),
        VintedMarket("Ireland", "EUR", "English"),
        VintedMarket("Italy", "EUR", "Italian"),
        VintedMarket("Lithuania", "EUR", "Lithuanian"),
        VintedMarket("Luxembourg", "EUR", "French"),
        VintedMarket("Netherlands", "EUR", "Dutch"),
        VintedMarket("Poland", "PLN", "Polish"),
        VintedMarket("Portugal", "EUR", "Portuguese"),
        VintedMarket("Romania", "RON", "Romanian"),
        VintedMarket("Slovakia", "EUR", "Slovak"),
        VintedMarket("Spain", "EUR", "Spanish"),
        VintedMarket("Sweden", "SEK", "Swedish"),
        VintedMarket("United Kingdom", "GBP", "English"),
        VintedMarket("United States", "USD", "English"),
    )

    val countries: List<String> = all.map { it.country }
    val currencies: List<String> = all.map { it.currency }.distinct().sorted()
    val languages: List<String> = all.map { it.language }.distinct().sorted()

    fun forCountry(country: String): VintedMarket? = all.find { it.country == country }
}
