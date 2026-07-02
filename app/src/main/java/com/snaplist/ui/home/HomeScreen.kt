package com.snaplist.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.snaplist.appContainer
import com.snaplist.data.db.DraftStatus
import com.snaplist.data.db.ListingDraft
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNewListing: (Long) -> Unit,
    onOpenDraft: (Long) -> Unit,
    onSettings: () -> Unit,
) {
    val context = LocalContext.current
    val container = context.appContainer()
    val drafts by container.draftDao.observeAll()
        .collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SnapList") },
                actions = {
                    IconButton(onClick = onSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                scope.launch {
                    val id = container.draftDao.insert(ListingDraft())
                    onNewListing(id)
                }
            }) {
                Icon(Icons.Default.Add, contentDescription = "New listing")
            }
        },
    ) { padding ->
        if (drafts.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text(
                    "No listings yet.\nTap + to photograph an item.",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize().padding(padding),
            ) {
                items(drafts, key = { it.id }) { draft ->
                    DraftCard(draft, onClick = { onOpenDraft(draft.id) })
                }
            }
        }
    }
}

@Composable
private fun DraftCard(draft: ListingDraft, onClick: () -> Unit) {
    Card(onClick = onClick) {
        Column {
            Box(Modifier.fillMaxWidth().aspectRatio(1f)) {
                val photo = draft.photoPaths.firstOrNull()
                if (photo != null) {
                    AsyncImage(
                        model = File(photo),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().clip(MaterialTheme.shapes.medium),
                    )
                } else {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No photos", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            Column(Modifier.padding(8.dp)) {
                Text(
                    draft.title.ifBlank { "Untitled draft" },
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                AssistChip(
                    onClick = onClick,
                    label = {
                        Text(
                            when (draft.status) {
                                DraftStatus.NEW -> "Draft"
                                DraftStatus.ANALYZING -> "Analyzing…"
                                DraftStatus.READY -> "Ready to post"
                                DraftStatus.POSTED -> "Posted"
                                DraftStatus.ERROR -> "Needs attention"
                            }
                        )
                    },
                )
            }
        }
    }
}
