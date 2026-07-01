package com.rajkrishan.rajjarvis.ui.search

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
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.StickyNote2
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.rajkrishan.rajjarvis.ui.components.GlassCard
import com.rajkrishan.rajjarvis.ui.components.GlowText
import com.rajkrishan.rajjarvis.ui.components.ParticleBackground
import com.rajkrishan.rajjarvis.ui.theme.NeonCyan
import com.rajkrishan.rajjarvis.ui.theme.TextMuted
import com.rajkrishan.rajjarvis.ui.theme.TextPrimary
import com.rajkrishan.rajjarvis.ui.theme.TextSecondary

private data class SearchResult(val icon: ImageVector, val category: String, val title: String, val subtitle: String)

// Mock cross-category results — real round will query Contacts/Files/
// Calendar/Notes providers plus a web search call, merged by relevance.
private val mockResults = listOf(
    SearchResult(Icons.Filled.Description, "Files", "Invoice_March.pdf", "Downloads • 2.1 MB"),
    SearchResult(Icons.Filled.Contacts, "Contacts", "Rajesh Kumar", "+91 98XXX XXXXX"),
    SearchResult(Icons.Filled.Event, "Calendar", "Invoice review meeting", "Tomorrow, 11:00 AM"),
    SearchResult(Icons.Filled.StickyNote2, "Notes", "Invoice follow-up", "Edited 2 days ago"),
    SearchResult(Icons.Filled.Language, "Web", "How to raise an invoice dispute", "example.com")
)

@Composable
fun SearchScreen() {
    var query by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        ParticleBackground(modifier = Modifier.fillMaxSize(), particleCount = 30)

        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(24.dp))
            GlowText(
                text = "UNIVERSAL SEARCH",
                color = NeonCyan,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            GlassCard(cornerRadius = 24) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Search, contentDescription = null, tint = NeonCyan)
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Search files, contacts, notes, web…", color = TextMuted) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = TextMuted,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            cursorColor = NeonCyan
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = if (query.isBlank()) "SUGGESTED" else "RESULTS ACROSS ALL SOURCES",
                color = TextSecondary,
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(mockResults) { result -> SearchResultRow(result) }
                item { Spacer(modifier = Modifier.height(90.dp)) }
            }
        }
    }
}

@Composable
private fun SearchResultRow(result: SearchResult) {
    GlassCard(modifier = Modifier.fillMaxWidth(), cornerRadius = 16) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(result.icon, contentDescription = result.category, tint = NeonCyan)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(result.title, color = TextPrimary, style = MaterialTheme.typography.bodyLarge)
                Text(
                    "${result.category} • ${result.subtitle}",
                    color = TextMuted,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
