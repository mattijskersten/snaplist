package com.snaplist.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer

enum class DraftStatus { NEW, ANALYZING, READY, POSTED, ERROR }

/** Vinted's five condition tiers. */
enum class Condition(val label: String) {
    NEW_WITH_TAGS("New with tags"),
    NEW_WITHOUT_TAGS("New without tags"),
    VERY_GOOD("Very good"),
    GOOD("Good"),
    SATISFACTORY("Satisfactory");
}

@Entity(tableName = "drafts")
data class ListingDraft(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val status: DraftStatus = DraftStatus.NEW,
    val photoPaths: List<String> = emptyList(),
    val title: String = "",
    val description: String = "",
    val brand: String = "",
    val categoryPath: List<String> = emptyList(),
    val condition: Condition? = null,
    val size: String = "",
    val colors: List<String> = emptyList(),
    val material: String = "",
    val priceSuggested: Double? = null,
    val priceLow: Double? = null,
    val priceHigh: Double? = null,
    val priceReasoning: String = "",
    val defectsNoted: List<String> = emptyList(),
    /** Final user-chosen price. */
    val price: Double? = null,
    val errorMessage: String = "",
)

class Converters {
    private val json = Json
    private val listSerializer = ListSerializer(String.serializer())

    @TypeConverter
    fun fromStringList(value: List<String>): String = json.encodeToString(listSerializer, value)

    @TypeConverter
    fun toStringList(value: String): List<String> =
        if (value.isBlank()) emptyList() else json.decodeFromString(listSerializer, value)

    @TypeConverter
    fun fromStatus(value: DraftStatus): String = value.name

    @TypeConverter
    fun toStatus(value: String): DraftStatus = DraftStatus.valueOf(value)

    @TypeConverter
    fun fromCondition(value: Condition?): String? = value?.name

    @TypeConverter
    fun toCondition(value: String?): Condition? = value?.let { Condition.valueOf(it) }
}
