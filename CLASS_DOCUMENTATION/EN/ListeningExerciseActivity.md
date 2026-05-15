# Class: ListeningExerciseActivity

## 1. General information
*   **Class Name:** `ListeningExerciseActivity`
*   **Type:** Activity
*   **Purpose:** This screen allows users to practice decoding Morse code sounds. The app plays a beep sequence, and the user must type the corresponding letters or words. This tests the user's ability to "hear" Morse code.
*   **Interactions:** Inherits from `BaseActivity`. Uses `MorsePlayer` for audio playback, `QuestionRepository` for generating questions, and `HelperDB` to save results.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `questionIndex` | `int` | Progress counter (0-9). | Tracks which question the user is on. |
| `failedAttempts`| `int` | Total mistakes made. | Saved to database at the end. |
| `nOfTry` | `int` | Attempts for the current beep. | Used to show correct answer after 3 fails. |
| `etAnswer` | `EditText` | Text input for the user's guess. | Compared against the correct answer. |
| `morsePlayer` | `MorsePlayer` | Audio control object. | Plays the dots and dashes as sound. |
| `currentQuestion`| `Question` | The current active question. | Provides the answer string. |
| `executorService`| `ExecutorService`| Background thread manager. | Used for saving results to DB. |

## 3. Classroom Methods

### Method name: `onCreate`
*   **Type:** `protected`
*   **Logic:**
    1. Sets the layout.
    2. Initializes components via `initElements()`.
    3. Starts the first question via `loadNextQuestion()`.
    4. Sets listener for "Play Sound" to trigger `morsePlayer`.
    5. Sets listener for "Submit" to check the answer.
*   **When called:** When the user selects "Listening" in the Exercise menu.

### Method name: `loadNextQuestion`
*   **Type:** `private`
*   **Logic:**
    1. Checks if there are more questions.
    2. If yes, updates UI (progress text, clears input).
    3. Plays the Morse sound for the new question after a 1-second delay.
    4. If no more questions, saves results and shows the end-of-game dialog.
*   **When called:** During setup and after a correct answer.

### Method name: `checkAnswer`
*   **Type:** `private`
*   **Logic:**
    1. Compares the `EditText` content with the `questionText`.
    2. **If correct:** Increments index and loads next question.
    3. **If wrong:** Increments mistakes and used tries. After 3 tries, shows the failed dialog.
*   **When called:** When the user clicks "Submit".

### Method name: `saveResults`
*   **Type:** `private`
*   **Logic:** Writes the exercise type ("Listening"), mistake count, and date to the SQLite database.
*   **When called:** When the exercise session ends.

## 4. Lifecycle
*   **`onCreate()`**: Prepares the session.
*   **`onStop()`**: Crucial: calls `morsePlayer.stop()`. This ensures that if the user leaves the screen, the beeping stops immediately.

## 5. Interface Interaction (UI)
*   **Button (`bPlaySound`):** Replays the current Morse sequence.
*   **EditText (`etAnswer`):** Where the user types the decoded text.
*   **Button (`bSubmit`):** Confirms the user's guess.

## 6. Interaction with other components
*   **MorsePlayer:** Encapsulates the logic of timing and MediaPlayer use.
*   **QuestionRepository:** Provides the "secret" words for the user to hear.

## 7. General logic of the class
The `ListeningExerciseActivity` follows an **Auditory Loop**: Play Beeps -> User Types -> Check Logic. It bridges the gap between hearing (audio) and understanding (text). It requires the user to translate in their head from Morse back to English.

## 8. Simplified explanation
Think of this as a **Digital Dictation**. The app acts like a telegraph operator sending you a message. Your job is to listen carefully to the "beeps" and write down what the operator is saying on your notepad (the text field).

---
**Bugs/Improvements:**
*   The `saveResults` method currently uses `user.getUserPassword()` as the user identifier in the database column `COLUMN_EXERCISE_USER`. This is a bug (or at least bad practice); it should use `user.getUserName()`.
*   Similar to `ExerciseGame`, the `updateTryIndicators` logic is missing from the snippet.
