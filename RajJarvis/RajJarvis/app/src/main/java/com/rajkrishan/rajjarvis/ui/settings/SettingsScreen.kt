package com.rajkrishan.rajjarvis.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.rajkrishan.rajjarvis.ui.components.GlassCard
import com.rajkrishan.rajjarvis.ui.components.GlowText
import com.rajkrishan.rajjarvis.ui.components.NeonButton
import com.rajkrishan.rajjarvis.ui.components.ParticleBackground
import com.rajkrishan.rajjarvis.ui.theme.NeonCyan
import com.rajkrishan.rajjarvis.ui.theme.NeonPurple
import com.rajkrishan.rajjarvis.ui.theme.TextMuted
import com.rajkrishan.rajjarvis.ui.theme.TextPrimary
import com.rajkrishan.rajjarvis.ui.theme.TextSecondary

private enum class AiBackend { GEMINI, OPENAI }

/**
 * Round 1: keys are held in local Compose state only, so they reset when
 * the app restarts. Before shipping, replace this with EncryptedSharedPreferences
 * (see README "Round 2" notes) so keys persist safely and never touch plain
 * SharedPreferences or a log.
 */
@Composable
fun SettingsScreen() {
    var backend by remember { mutableStateOf(AiBackend.GEMINI) }
    var geminiKey by remember { mutableStateOf("") }
    var openAiKey by remember { mutableStateOf("") }
    var wakeWordEnabled by remember { mutableStateOf(true) }
    var offlineFallback by remember { mutableStateOf(true) }
    var saved by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        ParticleBackground(modifier = Modifier.fillMaxSize(), particleCount = 24)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                GlowText(
                    text = "SETTINGS",
                    color = NeonCyan,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        SectionLabel("AI BACKEND")
                        Spacer(modifier = Modifier.height(8.dp))
                        BackendOption(
                            label = "Google Gemini",
                            selected = backend == AiBackend.GEMINI,
                            onSelect = { backend = AiBackend.GEMINI }
                        )
                        BackendOption(
                            label = "OpenAI",
                            selected = backend == AiBackend.OPENAI,
                            onSelect = { backend = AiBackend.OPENAI }
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "Switch anytime — both keys can be stored, only the selected backend is used for requests.",
                            color = TextMuted,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }

            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        SectionLabel("API KEYS")
                        Spacer(modifier = Modifier.height(10.dp))
                        ApiKeyField(
                            label = "Gemini API key",
                            value = geminiKey,
                            onValueChange = { geminiKey = it }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        ApiKeyField(
                            label = "OpenAI API key",
                            value = openAiKey,
                            onValueChange = { openAiKey = it }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            "Keys stay on this device. See the README for exactly where to generate a Gemini or OpenAI key.",
                            color = TextMuted,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }

            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        SectionLabel("VOICE & AUTOMATION")
                        Spacer(modifier = Modifier.height(8.dp))
                        ToggleRow(
                            label = "Wake word (\"Hey Jarvis\")",
                            checked = wakeWordEnabled,
                            onCheckedChange = { wakeWordEnabled = it }
                        )
                        ToggleRow(
                            label = "Offline fallback when no internet",
                            checked = offlineFallback,
                            onCheckedChange = { offlineFallback = it }
                        )
                    }
                }
            }

            item {
                NeonButton(
                    text = if (saved) "SAVED ✓" else "SAVE SETTINGS",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { saved = true }
                )
            }

            item {
                GlassCard(modifier = Modifier.fillMaxWidth(), borderGlow = false) {
                    Column {
                        SectionLabel("ABOUT")
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("RAJ JARVIS", color = TextPrimary, style = MaterialTheme.typography.titleMedium)
                        Text("Your Personal AI Assistant", color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
                        Text("Made by Rajkrishan", color = TextMuted, style = MaterialTheme.typography.labelSmall)
                        Text("Version 0.1.0 (UI preview)", color = TextMuted, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(90.dp)) }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(text, color = TextSecondary, style = MaterialTheme.typography.labelSmall)
}

@Composable
private fun BackendOption(label: String, selected: Boolean, onSelect: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(selectedColor = NeonCyan, unselectedColor = TextMuted)
        )
        Text(label, color = TextPrimary, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun ApiKeyField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label, color = TextMuted) },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = NeonPurple,
            unfocusedBorderColor = TextMuted,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            cursorColor = NeonCyan
        )
    )
}

@Composable
private fun ToggleRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = TextPrimary, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = NeonCyan,
                checkedTrackColor = NeonPurple
            )
        )
    }
}
