# Class: TTS_Service

## 1. General information
*   **Class Name:** `TTS_Service`
*   **Type:** Service (Background Service)
*   **Purpose:** This class provides Text-to-Speech (TTS) capabilities to the entire application. It runs in the background and converts text strings (like "You are going to Exercise section") into spoken words using the phone's speakers.
*   **Interactions:** It is started by activities (like `BaseActivity` or `Welcome`) via Intents. It manages the system's TTS engine.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `tts` | `TextToSpeech` | The Android system object that does the actual speaking. | Initialized in `onCreate` and used in `speak`. |
| `isInitialized` | `boolean` | Flag to check if the engine is ready. | Checked before calling `speak`. |
| `pendingSpeaks` | `ArrayList<String>` | A queue for messages sent before initialization was done. | Populated in `onStartCommand` and emptied in `onInit`. |

## 3. Classroom Methods

### Method name: `onCreate`
*   **Type:** `public`
*   **Logic:**
    1. Creates a new `TextToSpeech` object.
    2. Registers the service as a listener to know when the engine finishes loading.
*   **When called:** When the service is first started.

### Method name: `onStartCommand`
*   **Type:** `public`
*   **Parameters:** `intent (Intent)` — carries the text to speak in the "what" extra.
*   **Logic:**
    1. Extracts the text from the intent.
    2. If the engine is ready (`isInitialized`), it calls `speak()` immediately.
    3. If not ready, it adds the text to `pendingSpeaks` to be spoken later.
*   **When called:** Every time an Activity sends a request to the service.

### Method name: `onInit`
*   **Type:** `public`
*   **Parameters:** `status (int)` — success or error.
*   **Logic:**
    1. Checks if the engine started correctly.
    2. Sets the language to US English.
    3. Sets `isInitialized` to true.
    4. Loops through `pendingSpeaks` and says everything that was waiting.
*   **When called:** Automatically by the system when the TTS engine is ready.

### Method name: `speak`
*   **Type:** `private`
*   **Logic:** Calls the system's `tts.speak()` method using `QUEUE_FLUSH`. This means if a new request comes in, it cancels the current one and says the new one immediately (good for responsive UI).
*   **When called:** Inside `onStartCommand` or `onInit`.

## 4. Lifecycle
*   **`onCreate()`**: Setup phase.
*   **`onDestroy()`**: Shutdown phase. It's very important to call `tts.shutdown()` here to stop the voice and release memory.

## 5. Interface Interaction (UI)
*   **None:** Works purely with audio.

## 6. Interaction with other components
*   **TextToSpeech API:** The Android system-level tool for voice.
*   **Intents:** The "messenger" used by Activities to talk to this service.

## 7. General logic of the class
`TTS_Service` is a **Voice Messenger**. It's like a person waiting in the background with a megaphone. Whenever an activity sends a note with some text, the person picks up the megaphone and reads the text out loud. If the megaphone is still warming up, the person holds onto the note until it's ready.

## 8. Simplified explanation
Think of `TTS_Service` as the **App's Voice**. The app doesn't have its own vocal cords, so it hires this service to talk to the user. Since the voice takes a second to "wake up" when the app first opens, the service is smart enough to remember what it needs to say and says it as soon as it's awake.

---
**Bugs/Improvements:**
*   **Language Support:** Currently hardcoded to `Locale.US`. It would be better to match the user's phone language or the app's chosen language.
*   **Queueing:** `QUEUE_FLUSH` is used, which is great for navigation, but if multiple important alerts happen at once, some might be cut off.
