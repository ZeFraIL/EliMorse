# Class: MorsePlayer

## 1. General information
*   **Class Name:** `MorsePlayer`
*   **Type:** Normal Class (Helper/Utility)
*   **Purpose:** This class is responsible for the physical output of Morse code. It can play Morse code as audio beeps or as flashes of the phone's camera light. It encapsulates the complex timing rules of Morse code so that any activity can easily "play" a Morse string.
*   **Interactions:** Used by `ListeningExerciseActivity` and potentially any other screen that needs to output Morse. It interacts with the device's hardware through `MediaPlayer` and `CameraManager`.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `DOT_DURATION` | `int` | Length of a dot (250ms). | Base unit for all timings. |
| `DASH_DURATION`| `int` | Length of a dash (750ms). | Defined as 3 * DOT. |
| `handler` | `Handler` | Manages timing and delays. | Used to schedule the next signal in a sequence. |
| `isPlaying` | `boolean` | State flag. | Prevents multiple sequences from playing at once. |
| `cameraManager` | `CameraManager` | Flashlight controller. | Used in `flash()`. |

## 3. Classroom Methods

### Method name: `playAsSound` / `playAsFlash`
*   **Type:** `public`
*   **Return value:** `void`
*   **Parameters:** `morseCode (String)` — the dots and dashes.
*   **Logic:**
    1. Checks if already playing or if the input is empty.
    2. Sets `isPlaying` to true.
    3. Starts the `playSequence` recursion.
*   **When called:** When the user clicks a "Play" button in an exercise.

### Method name: `playSequence`
*   **Type:** `private`
*   **Logic:**
    1. Base Case: If the end of the string is reached, set `isPlaying = false` and stop.
    2. Read current character (index).
    3. If '.', call `playSound` or `flash` for `DOT_DURATION`.
    4. If '-', call `playSound` or `flash` for `DASH_DURATION`.
    5. After the signal is done, use the `handler` to wait for a pause and then call `playSequence` for index + 1.
*   **What is important to understand:** This is a recursive loop with delays. It ensures that signals don't overlap.

### Method name: `stop`
*   **Type:** `public`
*   **Logic:** Immediately stops all scheduled signals by clearing the `handler` and setting `isPlaying` to false.
*   **When called:** When an activity is closed or a "Stop" button is pressed.

## 4. Lifecycle
*   **N/A:** Exists as long as the activity that created it exists.

## 5. Interface Interaction (UI)
*   **None:** This class works entirely behind the scenes with hardware.

## 6. Interaction with other components
*   **MediaPlayer:** Used to play the actual beep files (like `dot1.raw`).
*   **CameraManager:** Used to toggle the flashlight state.

## 7. General logic of the class
`MorsePlayer` is the **Executioner**. It takes the abstract "dots and dashes" from the translator and turns them into something you can hear or see. It strictly follows the timing rules (e.g., a dash is three times longer than a dot).

## 8. Simplified explanation
Think of `MorsePlayer` as a **Music Box**. You give it a roll of paper with holes (the dots and dashes), and it plays the music. It knows exactly how fast to spin the roll so that the song sounds right.

---
**Bugs/Improvements:**
*   **Sound files:** The `playSound` method currently uses `R.raw.dot1` for both dots and dashes, relying on the `MediaPlayer` completion and `Handler` delays to handle the timing. While this works, using distinct sound files or a generated sine wave might produce a cleaner "telegraph" sound.
*   **Error Handling:** If the camera is in use by another app, `setTorchMode` might fail. Adding a try-catch block inside the `flash` method prevents the app from crashing.
