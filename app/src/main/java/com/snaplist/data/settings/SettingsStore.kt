package com.snaplist.data.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Settings(
    val apiKey: String = "",
    val currency: String = "EUR",
    val country: String = "Netherlands",
    val language: String = "Dutch",
    val model: String = "claude-opus-4-8",
)

class SettingsStore(context: Context) {

    private val prefs: SharedPreferences = run {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        EncryptedSharedPreferences.create(
            context,
            "snaplist_settings",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    private val _settings = MutableStateFlow(load())
    val settings: StateFlow<Settings> = _settings

    private fun load() = Settings(
        apiKey = prefs.getString(KEY_API_KEY, "") ?: "",
        currency = prefs.getString(KEY_CURRENCY, "EUR") ?: "EUR",
        country = prefs.getString(KEY_COUNTRY, "Netherlands") ?: "Netherlands",
        language = prefs.getString(KEY_LANGUAGE, "Dutch") ?: "Dutch",
        model = prefs.getString(KEY_MODEL, DEFAULT_MODEL) ?: DEFAULT_MODEL,
    )

    fun save(settings: Settings) {
        prefs.edit()
            .putString(KEY_API_KEY, settings.apiKey)
            .putString(KEY_CURRENCY, settings.currency)
            .putString(KEY_COUNTRY, settings.country)
            .putString(KEY_LANGUAGE, settings.language)
            .putString(KEY_MODEL, settings.model.ifBlank { DEFAULT_MODEL })
            .apply()
        _settings.value = load()
    }

    companion object {
        const val DEFAULT_MODEL = "claude-opus-4-8"
        private const val KEY_API_KEY = "api_key"
        private const val KEY_CURRENCY = "currency"
        private const val KEY_COUNTRY = "country"
        private const val KEY_LANGUAGE = "language"
        private const val KEY_MODEL = "model"
    }
}
