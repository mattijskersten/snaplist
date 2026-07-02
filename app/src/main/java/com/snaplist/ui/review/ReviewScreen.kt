package com.snaplist.ui.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.snaplist.appContainer
import com.snaplist.data.db.Condition
import com.snaplist.data.db.DraftStatus
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    draftId: Long,
    onPost: () -> Unit,
    onAddPhotos: () -> Unit,
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    val container = context.appContainer()
    val vm: ReviewViewModel = viewModel(factory = ReviewViewModel.Factory(container, draftId))
    val draft by vm.draft.collectAsState()
    val settings by container.settings.settings.collectAsState()

    LaunchedEffect(draftId) { vm.analyzeIfNeeded() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Review listing") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { vm.delete(onDeleted = onBack) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete draft")
                    }
                },
            )
        },
    ) { padding ->
        val current = draft
        when {
            current == null -> Box(Modifier.fillMaxSize().padding(padding))

            current.status == DraftStatus.ANALYZING -> {
                Column(
                    Modifier.fillMaxSize().padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(16.dp))
                    Text("Analyzing your photos…")
                    Spacer(Modifier.height(16.dp))
                    OutlinedButton(onClick = { vm.cancelAnalysis() }) { Text("Cancel") }
                }
            }

            else -> {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    if (current.status == DraftStatus.ERROR) {
                        Text(
                            current.errorMessage.ifBlank { "Analysis failed." },
                            color = MaterialTheme.colorScheme.error,
                        )
                    }

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(current.photoPaths) { path ->
                            AsyncImage(
                                model = File(path),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(72.dp).clip(MaterialTheme.shapes.small),
                            )
                        }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(onClick = onAddPhotos) { Text("Photos…") }
                        OutlinedButton(onClick = { vm.analyze() }) {
                            Icon(Icons.Default.Refresh, contentDescription = null)
                            Text(" Re-analyze")
                        }
                    }

                    OutlinedTextField(
                        value = current.title,
                        onValueChange = { v -> vm.update { it.copy(title = v) } },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    OutlinedTextField(
                        value = current.description,
                        onValueChange = { v -> vm.update { it.copy(description = v) } },
                        label = { Text("Description") },
                        minLines = 4,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = current.brand,
                            onValueChange = { v -> vm.update { it.copy(brand = v) } },
                            label = { Text("Brand") },
                            modifier = Modifier.weight(1f),
                        )
                        OutlinedTextField(
                            value = current.size,
                            onValueChange = { v -> vm.update { it.copy(size = v) } },
                            label = { Text("Size") },
                            modifier = Modifier.weight(1f),
                        )
                    }
                    OutlinedTextField(
                        value = current.categoryPath.joinToString(" › "),
                        onValueChange = { v ->
                            vm.update { it.copy(categoryPath = v.split("›").map(String::trim).filter(String::isNotEmpty)) }
                        },
                        label = { Text("Category (Vinted path)") },
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Text("Condition", style = MaterialTheme.typography.labelLarge)
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Condition.entries.forEach { cond ->
                            FilterChip(
                                selected = current.condition == cond,
                                onClick = { vm.update { it.copy(condition = cond) } },
                                label = {
                                    Text(cond.label, style = MaterialTheme.typography.labelSmall)
                                },
                            )
                        }
                    }
                    if (current.defectsNoted.isNotEmpty()) {
                        Text(
                            "Flaws noticed: ${current.defectsNoted.joinToString("; ")}",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = current.colors.joinToString(", "),
                            onValueChange = { v ->
                                vm.update { it.copy(colors = v.split(",").map(String::trim).filter(String::isNotEmpty).take(2)) }
                            },
                            label = { Text("Colors (max 2)") },
                            modifier = Modifier.weight(1f),
                        )
                        OutlinedTextField(
                            value = current.material,
                            onValueChange = { v -> vm.update { it.copy(material = v) } },
                            label = { Text("Material") },
                            modifier = Modifier.weight(1f),
                        )
                    }

                    OutlinedTextField(
                        value = current.price?.let { formatPrice(it) } ?: "",
                        onValueChange = { v ->
                            vm.update { it.copy(price = v.replace(",", ".").toDoubleOrNull()) }
                        },
                        label = { Text("Price (${settings.currency})") },
                        supportingText = {
                            val low = current.priceLow
                            val high = current.priceHigh
                            if (low != null && high != null) {
                                Text(
                                    "AI suggests ${formatPrice(low)}–${formatPrice(high)} ${settings.currency}. " +
                                        current.priceReasoning
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Button(
                        onClick = onPost,
                        enabled = current.title.isNotBlank() && current.photoPaths.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Post to Vinted")
                    }
                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}

private fun formatPrice(value: Double): String =
    if (value == value.toLong().toDouble()) value.toLong().toString() else "%.2f".format(value)
