# Class: TextToSpeechHelper

## 1. General information
*   **Class Name:** `TextToSpeechHelper`
*   **Type:** Normal Class (Utility)
*   **Purpose:** This class provides a simplified, static way to use the Text-to-Speech engine without needing to start a service. It allows other parts of the code to "speak" with just one line of code.
*   **Interactions:** Manages a static instance of the system `TextToSpeech` object.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `textToSpeech` | `TextToSpeech` | The engine instance. | Used for `speak()` and `shutdown()`. |

## 3. Classroom Methods

### Method name: `speak`
*   **Type:** `public static`
*   **Logic:** Checks if the engine is initialized, and if so, tells it to speak the provided text immediately.
*   **When called:** When an activity needs a quick voice announcement.

### Method name: `shutdown`
*   **Type:** `public static`
*   **Logic:** Stops any speech and releases the system resources.
*   **When called:** When the app is closing.

## 4. Lifecycle
*   **N/A:** Simple static container.

## 5. General logic of the class
`TextToSpeechHelper` is a **Megaphone on the Shelf**. You don't need to hire a whole service (like `TTS_Service`) to use it; you just grab it, say something, and put it back.

## 6. Simplified explanation
Think of this as a **Shortcut**. Usually, talking requires a lot of steps (opening the mouth, breathing, etc.). This class lets the app "speak" by just pushing a single button.

---
**Bugs/Improvements:**
*   **Initialization:** The initialization code is currently commented out. This means the `textToSpeech` variable will always be `null`, and the `speak` method will never actually do anything in its current state. The initialization logic from the comments should be fixed and enabled.
