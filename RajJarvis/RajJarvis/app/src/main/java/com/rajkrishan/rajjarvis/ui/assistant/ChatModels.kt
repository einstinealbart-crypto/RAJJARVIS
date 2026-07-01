package com.rajkrishan.rajjarvis.ui.assistant

data class ChatMessage(
    val id: Long,
    val text: String,
    val isUser: Boolean,
    val timestamp: String
)

/**
 * Round 1 (UI scaffold): canned responses so the chat screen is fully
 * demoable without a network call. Swap [mockReply] for a real call to
 * the Gemini or OpenAI client (selected in Settings) in the next round.
 */
fun mockReply(userText: String): String {
    val lower = userText.lowercase()
    return when {
        "weather" in lower -> "It's 29°C and partly cloudy in Kolkata right now, with a light breeze."
        "reminder" in lower -> "Got it — I've noted that reminder. (Wiring to the real Calendar/Reminders API comes in a later round.)"
        "call" in lower || "contact" in lower -> "I found a matching contact. (Contacts access will be wired up once runtime permissions are added.)"
        "hello" in lower || "hi" == lower.trim() -> "Hello Raj. Systems nominal — what can I do for you?"
        else -> "Understood. I'm currently running in UI-preview mode, so this is a placeholder reply — once the Gemini/OpenAI backend is connected in Settings, I'll answer this for real."
    }
}
