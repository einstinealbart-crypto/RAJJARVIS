package com.rajkrishan.rajjarvis.ui.files

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.rajkrishan.rajjarvis.ui.components.GlassCard
import com.rajkrishan.rajjarvis.ui.components.GlowText
import com.rajkrishan.rajjarvis.ui.components.ParticleBackground
import com.rajkrishan.rajjarvis.ui.theme.NeonCyan
import com.rajkrishan.rajjarvis.ui.theme.NeonPurple
import com.rajkrishan.rajjarvis.ui.theme.TextMuted
import com.rajkrishan.rajjarvis.ui.theme.TextPrimary
import com.rajkrishan.rajjarvis.ui.theme.TextSecondary

private data class FileEntry(val icon: ImageVector, val name: String, val meta: String)

private val quickFolders = listOf(
    "Downloads" to Icons.Filled.Folder,
    "Documents" to Icons.Filled.Folder,
    "Pictures" to Icons.Filled.Folder,
    "Videos" to Icons.Filled.Folder
)

// Storage permission + MediaStore/SAF integration comes in a later round —
// this screen currently shows the target layout with sample entries.
private val recentFiles = listOf(
    FileEntry(Icons.Filled.PictureAsPdf, "Invoice_March.pdf", "2.1 MB • Downloads"),
    FileEntry(Icons.Filled.TableChart, "Budget_2026.xlsx", "540 KB • Documents"),
    FileEntry(Icons.Filled.Description, "Resume_Raj.docx", "88 KB • Documents"),
    FileEntry(Icons.Filled.Image, "Family_Trip.jpg", "3.4 MB • Pictures"),
    FileEntry(Icons.Filled.VideoFile, "Birthday_Clip.mp4", "44 MB • Videos")
)

@Composable
fun FilesScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        ParticleBackground(modifier = Modifier.fillMaxSize(), particleCount = 30)

        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(24.dp))
            GlowText(
                text = "FILE EXPLORER",
                color = NeonCyan,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(18.dp))

            Text("QUICK ACCESS", color = TextSecondary, style = MaterialTheme.typography.labelSmall)
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                quickFolders.forEach { (name, icon) ->
                    GlassCard(cornerRadius = 16, modifier = Modifier.weight(1f)) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            Icon(icon, contentDescription = name, tint = NeonPurple)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(name, color = TextPrimary, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text("RECENT FILES", color = TextSecondary, style = MaterialTheme.typography.labelSmall)
            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(recentFiles) { file -> FileRow(file) }
                item { Spacer(modifier = Modifier.height(90.dp)) }
            }
        }
    }
}

@Composable
private fun FileRow(file: FileEntry) {
    GlassCard(modifier = Modifier.fillMaxWidth(), cornerRadius = 16) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(file.icon, contentDescription = file.name, tint = NeonCyan)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(file.name, color = TextPrimary, style = MaterialTheme.typography.bodyLarge)
                Text(file.meta, color = TextMuted, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
