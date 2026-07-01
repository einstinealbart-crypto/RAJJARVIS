package com.rajkrishan.rajjarvis.ui.home

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Wifi
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rajkrishan.rajjarvis.navigation.Screen
import com.rajkrishan.rajjarvis.ui.components.GlassCard
import com.rajkrishan.rajjarvis.ui.components.GlowText
import com.rajkrishan.rajjarvis.ui.components.NeonIconButton
import com.rajkrishan.rajjarvis.ui.components.ParticleBackground
import com.rajkrishan.rajjarvis.ui.theme.NeonCyan
import com.rajkrishan.rajjarvis.ui.theme.NeonPurple
import com.rajkrishan.rajjarvis.ui.theme.StatusOnline
import com.rajkrishan.rajjarvis.ui.theme.TextMuted
import com.rajkrishan.rajjarvis.ui.theme.TextPrimary
import com.rajkrishan.rajjarvis.ui.theme.TextSecondary

private data class StatChip(val icon: ImageVector, val label: String, val value: String)
private data class DashboardCard(val icon: ImageVector, val title: String, val value: String, val sub: String)

// ---- Mock data (Round 1: UI only, no live sensors/network wired yet) ----
private val statChips = listOf(
    StatChip(Icons.Filled.BatteryChargingFull, "Battery", "82%"),
    StatChip(Icons.Filled.Wifi, "WiFi", "Connected"),
    StatChip(Icons.Filled.Bluetooth, "BT", "Off"),
    StatChip(Icons.Filled.LocationOn, "Location", "Kolkata")
)

private val dashboardCards = listOf(
    DashboardCard(Icons.Filled.Memory, "RAM", "4.2 / 8 GB", "52% used"),
    DashboardCard(Icons.Filled.Sensors, "CPU", "34%", "Normal load"),
    DashboardCard(Icons.Filled.Thermostat, "Temp", "36°C", "Nominal"),
    DashboardCard(Icons.Filled.Cloud, "Weather", "29°C", "Partly cloudy, Kolkata")
)

private val upcomingEvents = listOf(
    "10:30 AM  —  Team Standup",
    "1:00 PM  —  Call with Amit",
    "6:00 PM  —  Gym Session"
)

private val recentNotifications = listOf(
    "WhatsApp — 3 new messages from Team Group",
    "Gmail — Invoice #4021 received",
    "Calendar — Reminder: Mom's Birthday tomorrow"
)

@Composable
fun HomeScreen(navController: NavHostController) {
    var query by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        ParticleBackground(modifier = Modifier.fillMaxSize())

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item { Spacer(modifier = Modifier.height(24.dp)) }

            item { HomeHeader() }

            item {
                LazyRowStats()
            }

            item {
                UniversalSearchBar(
                    query = query,
                    onQueryChange = { query = it },
                    onVoiceClick = { navController.navigate(Screen.Assistant.route) }
                )
            }

            item {
                Text(
                    text = "SYSTEM STATUS",
                    color = TextSecondary,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.height(220.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(dashboardCards) { card -> DashboardStatCard(card) }
                }
            }

            item {
                SectionCard(
                    title = "UPCOMING EVENTS",
                    icon = Icons.Filled.CalendarMonth,
                    lines = upcomingEvents
                )
            }

            item {
                SectionCard(
                    title = "RECENT NOTIFICATIONS",
                    icon = Icons.Filled.Notifications,
                    lines = recentNotifications
                )
            }

            item { Spacer(modifier = Modifier.height(90.dp)) }
        }

        // Floating voice orb, bottom-right
        NeonIconButton(
            icon = Icons.Filled.Mic,
            contentDescription = "Talk to Jarvis",
            size = 64,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .padding(bottom = 70.dp),
            onClick = { navController.navigate(Screen.Assistant.route) }
        )
    }
}

@Composable
private fun HomeHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            GlowText(
                text = "GOOD EVENING, RAJ",
                color = NeonCyan,
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 18.sp)
            )
            Text(
                text = "Tuesday, 1 July  •  9:42 PM",
                color = TextMuted,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Box(
            modifier = Modifier
                .height(10.dp)
                .width(10.dp)
        ) {
            // Online status dot
            androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(color = StatusOnline)
            }
        }
    }
}

@Composable
private fun LazyRowStats() {
    androidx.compose.foundation.lazy.LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        androidx.compose.foundation.lazy.items(statChips) { chip -> StatChipView(chip) }
    }
}

@Composable
private fun StatChipView(chip: StatChip) {
    GlassCard(cornerRadius = 50) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(chip.icon, contentDescription = chip.label, tint = NeonCyan, modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Column {
                Text(chip.value, color = TextPrimary, style = MaterialTheme.typography.bodyMedium)
                Text(chip.label, color = TextMuted, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
private fun UniversalSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onVoiceClick: () -> Unit
) {
    GlassCard(cornerRadius = 24, borderGlow = true) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Ask Jarvis or search everything…", color = TextMuted) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonCyan,
                    unfocusedBorderColor = TextMuted,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    cursorColor = NeonCyan
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
            NeonIconButton(
                icon = Icons.Filled.Mic,
                contentDescription = "Voice input",
                size = 44,
                onClick = onVoiceClick
            )
        }
    }
}

@Composable
private fun DashboardStatCard(card: DashboardCard) {
    GlassCard(modifier = Modifier.fillMaxSize()) {
        Column {
            Icon(card.icon, contentDescription = card.title, tint = NeonPurple)
            Spacer(modifier = Modifier.height(8.dp))
            Text(card.value, color = TextPrimary, style = MaterialTheme.typography.titleMedium)
            Text(card.title, color = TextSecondary, style = MaterialTheme.typography.labelSmall)
            Spacer(modifier = Modifier.height(2.dp))
            Text(card.sub, color = TextMuted, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
private fun SectionCard(title: String, icon: ImageVector, lines: List<String>) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = title, tint = NeonCyan, modifier = Modifier.height(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(title, color = TextSecondary, style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.height(10.dp))
            lines.forEach { line ->
                Text(
                    text = "•  $line",
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 3.dp)
                )
            }
        }
    }
}
