# 📱 Translate Activity Documentation

## 1. General Information
*   **Name:** `Translate`
*   **Type:** Activity (Extends `BaseActivity`)
*   **Purpose:** This screen allows users to translate text into Morse code and vice versa. It also controls the device's hardware (Flashlight and Audio) to play the signals.
*   **Interaction:** Uses `MorseTranslator` for logic and `TTS_Service` for voice guidance.

## 2. Variables (Class Fields)
| Name | Type | Purpose | Usage |
| :--- | :--- | :--- | :--- |
| `translator` | `MorseTranslator` | The "brain" of translation. | Performs the actual conversion between text and Morse. |
| `soundPool` | `SoundPool` | High-performance audio player. | Used to play "dot" and "dash" sounds without lag. |
| `what_switch` | `int` | Mode toggle counter. | Even number = Text to Morse; Odd number = Morse to Text. |
| `handler` | `Handler` | Timing manager. | Controls the delays between flashes and beeps. |
| `isPlaying` | `boolean` | State flag. | Prevents starting a new signal while one is already playing. |

## 3. Methods
### Method: `playSequence`
*   **Type:** `private`
*   **Returns:** `void`
*   **Parameters:** `morseCode` (String), `index` (int)
*   **What it does:** This is a **recursive** method. It reads the Morse string character by character. If it sees a `.`, it plays a dot sound; if it sees a `-`, a dash. It then calls itself for the next character after a specific delay.
*   **Important:** It uses a `Handler` to ensure the timing is precise.

### Method: `flash`
*   **Type:** `private`
*   **What it does:** Controls the camera's LED. It turns the light ON, waits for a duration (dot or dash), and then turns it OFF.

## 7. General Logic
The user types text, clicks "Translate," and the app shows the Morse code. The user can then click "Audio" or "Flashlight." The app loops through the Morse symbols and triggers the appropriate hardware (speaker or LED).

## 8. Simple Explanation
Think of this class as a **Telegraph Operator**. It takes your letter, looks up the code in a book (`MorseTranslator`), and then taps it out using a key (sound) or a signal lamp (flashlight).
