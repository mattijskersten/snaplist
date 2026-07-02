package com.snaplist.ui.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.snaplist.AppContainer
import com.snaplist.data.ai.AnalyzerException
import com.snaplist.data.ai.ListingAnalysis
import com.snaplist.data.db.Condition
import com.snaplist.data.db.DraftStatus
import com.snaplist.data.db.ListingDraft
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File

class ReviewViewModel(
    private val container: AppContainer,
    private val draftId: Long,
) : ViewModel() {

    val draft: StateFlow<ListingDraft?> = container.draftDao.observe(draftId)
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private var analysisJob: Job? = null

    fun analyzeIfNeeded() {
        viewModelScope.launch {
            val current = container.draftDao.get(draftId) ?: return@launch
            if (current.status == DraftStatus.NEW && current.photoPaths.isNotEmpty()) {
                analyze()
            }
        }
    }

    fun analyze() {
        if (analysisJob?.isActive == true) return
        analysisJob = viewModelScope.launch {
            val current = container.draftDao.get(draftId) ?: return@launch
            if (current.photoPaths.isEmpty()) return@launch
            container.draftDao.update(current.copy(status = DraftStatus.ANALYZING, errorMessage = ""))
            val settings = container.settings.settings.value
            try {
                val analysis = container.analyzer.analyze(
                    apiKey = settings.apiKey,
                    model = settings.model,
                    photos = current.photoPaths.map { File(it) }.filter { it.exists() },
                    country = settings.country,
                    currency = settings.currency,
                )
                container.draftDao.update(applyAnalysis(current, analysis))
            } catch (e: AnalyzerException) {
                container.draftDao.update(
                    current.copy(status = DraftStatus.ERROR, errorMessage = e.message ?: "Analysis failed")
                )
            } catch (e: Exception) {
                container.draftDao.update(
                    current.copy(status = DraftStatus.ERROR, errorMessage = "Unexpected error: ${e.message}")
                )
            }
        }
    }

    fun cancelAnalysis() {
        analysisJob?.cancel()
        viewModelScope.launch {
            val current = container.draftDao.get(draftId) ?: return@launch
            if (current.status == DraftStatus.ANALYZING) {
                container.draftDao.update(current.copy(status = DraftStatus.NEW))
            }
        }
    }

    fun update(transform: (ListingDraft) -> ListingDraft) {
        viewModelScope.launch {
            val current = container.draftDao.get(draftId) ?: return@launch
            container.draftDao.update(transform(current))
        }
    }

    fun delete(onDeleted: () -> Unit) {
        viewModelScope.launch {
            val current = container.draftDao.get(draftId) ?: return@launch
            container.draftDao.delete(current)
            container.photos.deletePhotos(draftId)
            onDeleted()
        }
    }

    companion object {
        fun applyAnalysis(draft: ListingDraft, analysis: ListingAnalysis): ListingDraft =
            draft.copy(
                status = DraftStatus.READY,
                title = analysis.title,
                description = analysis.description,
                brand = analysis.brand.orEmpty(),
                categoryPath = analysis.categoryPath,
                condition = conditionFrom(analysis.condition),
                size = analysis.size.orEmpty(),
                colors = analysis.colors.take(2),
                material = analysis.material.orEmpty(),
                priceSuggested = analysis.priceSuggested,
                priceLow = analysis.priceLow,
                priceHigh = analysis.priceHigh,
                priceReasoning = analysis.priceReasoning,
                defectsNoted = analysis.defectsNoted,
                price = analysis.priceSuggested,
                errorMessage = "",
            )

        fun conditionFrom(value: String): Condition? = when (value.lowercase()) {
            "new_with_tags" -> Condition.NEW_WITH_TAGS
            "new_without_tags" -> Condition.NEW_WITHOUT_TAGS
            "very_good" -> Condition.VERY_GOOD
            "good" -> Condition.GOOD
            "satisfactory" -> Condition.SATISFACTORY
            else -> null
        }
    }

    class Factory(
        private val container: AppContainer,
        private val draftId: Long,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ReviewViewModel(container, draftId) as T
    }
}
