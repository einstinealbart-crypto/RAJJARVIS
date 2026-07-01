package com.rajkrishan.rajjarvis.ui.assistant

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rajkrishan.rajjarvis.ui.components.GlassCard
import com.rajkrishan.rajjarvis.ui.components.GlowText
import com.rajkrishan.rajjarvis.ui.components.NeonIconButton
import com.rajkrishan.rajjarvis.ui.components.ParticleBackground
import com.rajkrishan.rajjarvis.ui.theme.NeonCyan
import com.rajkrishan.rajjarvis.ui.theme.NeonPurple
import com.rajkrishan.rajjarvis.ui.theme.TextMuted
import com.rajkrishan.rajjarvis.ui.theme.TextPrimary
import com.rajkrishan.rajjarvis.ui.theme.VoidBlack
import kotlinx.coroutines.delay

@Composable
fun AssistantScreen() {
    val messages = remember {
        mutableStateListOf(
            ChatMessage(0, "Hello Raj. Systems nominal — what can I do for you?", isUser = false, timestamp = "9:41 PM")
        )
    }
    var input by remember { mutableStateOf("") }
    var isTyping by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.size - 1)
    }

    fun send() {
        val text = input.trim()
        if (text.isEmpty()) return
        val nextId = (messages.maxOfOrNull { it.id } ?: 0) + 1
        messages.add(ChatMessage(nextId, text, isUser = true, timestamp = "now"))
        input = ""
        isTyping = true
    }

    // Simulated reply after a short "thinking" delay
    LaunchedEffect(messages.size) {
        val last = messages.lastOrNull()
        if (last != null && last.isUser) {
            delay(700)
            val reply = mockReply(last.text)
            messages.add(ChatMessage(last.id + 1, reply, isUser = false, timestamp = "now"))
            isTyping = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ParticleBackground(modifier = Modifier.fillMaxSize(), particleCount = 30)

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlowText(
                    text = "JARVIS ASSISTANT",
                    color = NeonCyan,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(messages, key = { it.id }) { msg -> ChatBubble(msg) }
                if (isTyping) {
                    item { TypingIndicator() }
                }
            }

            ChatInputBar(
                value = input,
                onValueChange = { input = it },
                onSend = { send() }
            )
        }
    }
}

@Composable
private fun ChatBubble(message: ChatMessage) {
    val arrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = arrangement) {
        if (!message.isUser) {
            Icon(
                imageVector = Icons.Filled.SmartToy,
                contentDescription = "Jarvis",
                tint = NeonPurple,
                modifier = Modifier
                    .padding(end = 8.dp, top = 4.dp)
                    .height(20.dp)
            )
        }
        GlassCard(
            modifier = Modifier.widthIn(max = 280.dp),
            cornerRadius = if (message.isUser) 18 else 18,
            borderGlow = !message.isUser
        ) {
            Text(
                text = message.text,
                color = if (message.isUser) VoidBlack.copy(alpha = 0.92f) else TextPrimary,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun TypingIndicator() {
    Row {
        Icon(
            imageVector = Icons.Filled.SmartToy,
            contentDescription = "Jarvis is typing",
            tint = NeonPurple,
            modifier = Modifier
                .padding(end = 8.dp, top = 4.dp)
                .height(20.dp)
        )
        GlassCard {
            Text("● ● ●", color = TextMuted, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
private fun ChatInputBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Message Jarvis…", color = TextMuted) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NeonCyan,
                unfocusedBorderColor = TextMuted,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = NeonCyan
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        NeonIconButton(
            icon = Icons.Filled.Mic,
            contentDescription = "Voice input",
            size = 48,
            onClick = { /* wire to Android Speech API in a later round */ }
        )
        Spacer(modifier = Modifier.width(8.dp))
        NeonIconButton(
            icon = Icons.Filled.Send,
            contentDescription = "Send",
            size = 48,
            onClick = onSend
        )
    }
}
