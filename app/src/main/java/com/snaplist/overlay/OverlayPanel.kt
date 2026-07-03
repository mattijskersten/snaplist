package com.snaplist.overlay

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import com.snaplist.ui.theme.SnapListTheme

/**
 * The floating helper drawn over the Vinted app: a draggable chip that
 * expands into a compact tap-to-copy list of the listing fields.
 */
@Composable
fun OverlayPanel(
    fields: List<Pair<String, String>>,
    copiedLabels: List<String>,
    expanded: Boolean,
    onToggle: () -> Unit,
    onCopy: (String, String) -> Unit,
    onDrag: (Float, Float) -> Unit,
    onClose: () -> Unit,
) {
    SnapListTheme {
        val dragModifier = Modifier.pointerInput(Unit) {
            detectDragGestures { change, amount ->
                change.consume()
                onDrag(amount.x, amount.y)
            }
        }
        if (!expanded) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary,
                shadowElevation = 6.dp,
                modifier = dragModifier
                    .size(48.dp)
                    .clickable(onClick = onToggle),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.ContentCopy,
                        contentDescription = "SnapList fields",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(22.dp),
                    )
                }
            }
        } else {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 8.dp,
                modifier = Modifier.widthIn(max = 300.dp),
            ) {
                Column(Modifier.padding(bottom = 4.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = dragModifier
                            .fillMaxWidth()
                            .padding(start = 16.dp),
                    ) {
                        Text(
                            "Tap to copy",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .weight(1f)
                                .clickable(onClick = onToggle)
                                .padding(vertical = 12.dp),
                        )
                        IconButton(onClick = onClose) {
                            Icon(Icons.Default.Close, contentDescription = "Close helper")
                        }
                    }
                    Column(
                        Modifier
                            .heightIn(max = 380.dp)
                            .verticalScroll(rememberScrollState()),
                    ) {
                        fields.forEach { (label, value) ->
                            val done = label in copiedLabels
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onCopy(label, value) }
                                    .padding(horizontal = 16.dp, vertical = 6.dp),
                            ) {
                                Column(Modifier.weight(1f)) {
                                    Text(label, style = MaterialTheme.typography.labelMedium)
                                    Text(
                                        value,
                                        style = MaterialTheme.typography.bodySmall,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                                Icon(
                                    if (done) Icons.Default.Check else Icons.Default.ContentCopy,
                                    contentDescription = if (done) "$label copied" else "Copy $label",
                                    tint = if (done) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .size(18.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
