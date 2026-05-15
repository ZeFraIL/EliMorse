# Class: Translate

## 1. General information
*   **Class Name:** `Translate`
*   **Type:** Activity
*   **Purpose:** This screen is the core tool for Morse code translation. It allows users to convert plain text into Morse code and vice versa. It also features the ability to "output" the translation as a series of audio beeps or flashlight pulses.
*   **Interactions:** Inherits from `BaseActivity`. Uses `MorseTranslator` for the logic, `SoundPool` for audio, and `CameraManager` for controlling the device's flashlight.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `etTranslateIncome`| `EditText` | User input field for text or Morse. | Input for translation. |
| `tvTranslateOutcome`| `TextView` | Displays the translation result. | Output of translation. |
| `what_switch` | `int` | Tracks the translation mode. | Used in `updateSwitchButton` (Even: Text->Morse, Odd: Morse->Text). |
| `cameraManager` | `CameraManager` | Controls the device's camera hardware. | Used to turn the torch on and off. |
| `cameraId` | `String` | ID of the back camera torch. | Needed for `setTorchMode`. |
| `soundPool` | `SoundPool` | Efficiently plays short audio clips. | Plays the dot and dash beep sounds. |
| `translator` | `MorseTranslator`| The logic engine. | Performs the actual string conversion. |
| `isPlaying` | `boolean` | Lock flag for playback. | Prevents starting a new sequence while one is already playing. |

## 3. Classroom Methods

### Method name: `onCreate`
*   **Type:** `protected`
*   **Logic:**
    1. Sets the layout.
    2. Requests camera permissions (needed for flashlight).
    3. Initializes components and the `SoundPool`.
    4. Sets listeners for: Switch (change mode), Translate (execute), Audio (play sound), and Flashlight (blink torch).
*   **When called:** When the activity starts.

### Method name: `toMorse` / `toText`
*   **Type:** `private`
*   **Logic:** Takes the text from the input field, passes it to the `translator` object, and sets the result in the outcome `TextView`.
*   **When called:** When the "Translate" button is clicked.

### Method name: `playSequence` / `flashMorseCode`
*   **Type:** `private`
*   **Parameters:** `morseCode (String)`, `index (int)`
*   **Logic:** These are recursive methods. They look at the character at the current `index`. If it's a `.` or `-`, they trigger the sound or light for a specific duration, then use a `Handler` to call themselves again for the *next* character after a short delay.
*   **When called:** When the Audio or Flashlight buttons are clicked.

### Method name: `flash`
*   **Type:** `private`
*   **Parameters:** `duration (int)`, `onCompletion (Runnable)`
*   **Logic:** Turns the torch ON, waits for the specified `duration`, then turns the torch OFF and executes the `onCompletion` code.
*   **When called:** Inside `flashMorseCode`.

## 4. Lifecycle
*   **`onCreate()`**: Prepares the translation environment and hardware access.
*   **`onDestroy()`**: Releases the `SoundPool` resources to free up memory.

## 5. Interface Interaction (UI)
*   **EditText (`etTranslateIncome`):** Where users type.
*   **Button (`bSwitch`):** Toggles between Text-to-Morse and Morse-to-Text.
*   **Button (`bAudio` / `bFlashlight`):** Triggers physical outputs.

## 6. Interaction with other components
*   **CameraManager:** A system service used for the flashlight.
*   **SoundPool:** An Android API for playing short, low-latency sounds.
*   **MorseTranslator:** A custom helper class containing the dictionary of Morse symbols.

## 7. General logic of the class
`Translate` is a **Bridge** between our language and Morse code. It takes input, processes it through a logic class, and then "broadcasts" it to the real world through sound or light. It handles the complex timing of Morse signals (dots vs. dashes) using recursion and delays.

## 8. Simplified explanation
Think of `Translate` as a **Universal Translator** combined with a **Signal Lamp**. You type a secret message, it turns it into dots and dashes on the screen. Then, you can press a button to act like a telegraph operator (Sound) or a ship signaling with a light (Flashlight).

---
**Bugs/Improvements:**
*   **Recursive depth:** For very long texts, recursion might eventually cause issues, although unlikely for Morse lengths. Using a queue or an iterator might be cleaner.
*   **Permissions:** While requested, the code doesn't strictly check if the user *granted* them before trying to use the camera. This could cause a crash if permission is denied.
*   **Stopping playback:** There is no "Stop" button to interrupt a long sound or light sequence once it starts.
