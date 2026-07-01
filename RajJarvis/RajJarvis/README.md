# RAJ JARVIS — Round 1 (UI/UX Scaffold)

A real, buildable Android Studio project for the RAJ JARVIS assistant, focused on the
full futuristic interface with mock data. Nothing is wired to live device data or a
real AI backend yet — that's Round 2+.

## What's in this round

- Kotlin + Jetpack Compose (Material 3), MVVM-ready folder structure
- Animated splash/boot screen with progress %, then Home
- Home HUD dashboard: status chips, universal search bar, animated stat cards,
  upcoming events, notifications — all mock data
- Assistant chat screen with a working (but canned) conversation loop, typing indicator
- Search and Files screens styled to match, with sample results
- Settings screen with a **Gemini ⇄ OpenAI switch** and password-style API key fields
  (stored in memory only for now — see "Round 2" below)
- Dark-only cyberpunk/glassmorphism theme: neon cyan/purple/blue, glowing borders,
  drifting particle background, glass-panel cards, bottom nav

## 1. Install Android Studio

1. Download Android Studio (the "Ladybug" or newer release) from
   `https://developer.android.com/studio` and install it.
2. On first launch, let the Setup Wizard install the Android SDK (API 35),
   an emulator image, and platform tools — accept the defaults.
3. Open Android Studio → **Open** → select the `RajJarvis` folder (the one
   containing `settings.gradle.kts`).
4. Android Studio will offer to generate the Gradle wrapper (`gradlew`) —
   accept that, or run `gradle wrapper` from a terminal in this folder if
   you have a system Gradle install. Either way, let it sync (it will
   download Gradle 8.9 automatically the first time).
5. Once sync finishes, pick a device: either create a **Virtual Device**
   (Tools → Device Manager → Create Device — a Pixel 8, API 35 image is a
   good default) or connect your own Android phone with USB debugging
   enabled (Settings → About phone → tap "Build number" 7 times → Developer
   options → USB debugging).
6. Click the green ▶ Run button. First build takes a few minutes; after
   that it's fast.

## 2. Getting API keys (for Round 2, when the AI is actually wired up)

You don't need these yet to run this UI round, but since you asked for
guidance:

**Gemini (Google AI Studio)**
1. Go to `https://aistudio.google.com/apikey`
2. Sign in with a Google account, click **Create API key**.
3. Copy the key — you'll paste it into the Settings screen's Gemini field
   once Round 2 wires up real network calls.
4. Gemini has a free tier with rate limits; check current limits on that
   page since they change over time.

**OpenAI**
1. Go to `https://platform.openai.com/api-keys`
2. Sign in, click **Create new secret key**, copy it immediately (it's
   only shown once).
3. OpenAI requires billing to be set up on the account (no meaningful free
   tier as of recent pricing) — add a payment method under **Billing** if
   you plan to use it.

Either key is enough to start; the Settings screen already lets you store
both and flip a switch, so you can add the other later.

## Project layout

```
app/src/main/java/com/rajkrishan/rajjarvis/
├── MainActivity.kt              # Scaffold, bottom nav, theme root
├── navigation/                  # Screen routes + NavHost
├── ui/theme/                    # Colors, typography, Material3 theme
├── ui/components/                # ParticleBackground, GlassCard, NeonButton, GlowText
├── ui/splash/                   # Boot sequence screen
├── ui/home/                     # HUD dashboard
├── ui/assistant/                # Chat screen + mock reply logic
├── ui/search/                   # Universal search UI
├── ui/files/                    # File explorer UI
└── ui/settings/                 # AI backend switch, API keys, toggles
```

## What's deliberately NOT in this round

To keep this a working, reviewable build instead of a wall of half-wired
code, these are stubbed with comments pointing to where they'll go:

- Real permissions (contacts, SMS, storage, calendar, notifications) —
  none are requested yet, so none of the "search my contacts / find that
  PDF" commands actually touch your device data yet
- Real network calls to Gemini/OpenAI — chat replies are canned (see
  `ui/assistant/ChatModels.kt` → `mockReply()`)
- Wake word / continuous voice listening, TTS playback
- Room database, encrypted local memory, Hilt DI
- File manager operations (copy/move/delete/zip), OCR, barcode/QR scanning
- Overlay chat head, Quick Settings tile, home screen widget

## Suggested next rounds

1. **Runtime permissions + real device data**: contacts, calendar, files,
   notifications — swap each mock list for the real Android API/provider.
2. **AI backend wiring**: real Gemini/OpenAI calls from the Settings
   screen's saved key, streaming replies into the chat screen.
3. **Voice**: Android SpeechRecognizer + TextToSpeech, then wake-word
   detection.
4. **Automation + file manager**: flashlight/alarm/app-launch intents,
   full file browser actions.
5. **Persistence & security**: Room for notes/memory, EncryptedSharedPreferences
   for API keys, biometric lock.

Happy to build out any of these next — just say which one.
