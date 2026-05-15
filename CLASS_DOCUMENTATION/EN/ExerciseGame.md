# Class: ExerciseGame

## 1. General information
*   **Class Name:** `ExerciseGame`
*   **Type:** Activity
*   **Purpose:** This activity provides the interactive practice environment for encoding text into Morse code. The user is shown a word, letter, or number and must enter the correct dots and dashes. The class tracks mistakes and saves the final performance to the database.
*   **Interactions:** It inherits from `BaseActivity`. It uses `QuestionRepository` to get random questions, `HelperDB` to save results, and returns to `DashBoard` when finished.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `questionNum` | `int` | Index of the current question (0 to 9). | Used to track progress in the list of questions. |
| `failedAttempts`| `int` | Total number of mistakes across all questions. | Saved to the database at the end. |
| `nOfTry` | `int` | Number of attempts for the *current* question. | Used to show failed dialog after 3 attempts. |
| `tvQuestion` | `TextView` | Displays the target text (e.g., "CAT"). | Updated in `resetAfterQuestion`. |
| `tvInputNumber` | `TextView` | Displays the Morse code entered by the user. | Updated in `appendToAnswer`. |
| `stAnswer` | `String` | Stores the user's Morse code input. | Used for comparison in `checkAnswer`. |
| `helperDB` | `HelperDB` | Database assistant. | Used in `saveResults`. |
| `questions` | `List<Question>`| List of 10 questions for the session. | Generated in `initElements`. |
| `currentQuestion`| `Question` | The specific question being answered. | Used to check the answer. |
| `executorService`| `ExecutorService`| Background thread manager. | Used to save results without lagging the UI. |

## 3. Classroom Methods

### Method name: `onCreate`
*   **Type:** `protected`
*   **Logic:**
    1. Sets the layout.
    2. Calls `initElements()` to prepare questions and UI.
    3. Sets listeners for dot (`.`), dash (`-`), and space (`/`) buttons.
    4. Sets listener for "Ready" to check the answer.
    5. Sets listeners for "Clear" and "Remove" buttons.
*   **When called:** When the activity starts.

### Method name: `checkAnswer`
*   **Type:** `private`
*   **Logic:**
    1. Compares `stAnswer` with `currentQuestion.getMorseAnswer()`.
    2. **If correct:** Moves to the next question. If it was the last question, it saves results and shows the completion dialog.
    3. **If wrong:** Increments `failedAttempts` and `nOfTry`. Updates the indicator dots (O/Ø). After 3 failed tries, it shows a "Failed" dialog.
*   **When called:** When the user clicks "Ready".

### Method name: `appendToAnswer`
*   **Type:** `private`
*   **Parameters:** `s (String)` — the character to add.
*   **Logic:** Adds the symbol to `stAnswer` and updates the display, limited to 30 characters.
*   **When called:** When dot, dash, or space buttons are clicked.

### Method name: `saveResults`
*   **Type:** `private`
*   **Logic:** Uses a background thread to insert the exercise kind, mistake count, date, and user into the database using `ContentValues`.
*   **When called:** After successfully completing all 10 questions.

### Method name: `initElements`
*   **Type:** `private`
*   **Logic:**
    1. Gets user and game type from the Intent.
    2. Uses `QuestionRepository` to generate 10 random questions.
    3. Links all UI components.
    4. Calls `resetAfterQuestion()` to start the first question.
*   **When called:** During `onCreate`.

## 4. Lifecycle
*   **`onCreate()`**: Sets up the entire game session.

## 5. Interface Interaction (UI)
*   **Input Buttons:** Dot, Dash, Space.
*   **Control Buttons:** Ready (Confirm), Clear (Delete all), Remove One (Backspace).
*   **Indicators:** `tvTryNum1`, `tvTryNum2`, `tvTryNum3` change color or text to show used attempts.

## 6. Interaction with other components
*   **QuestionRepository:** Essential for getting varied content.
*   **HelperDB:** Records the user's progress for the History screen.
*   **AlertDialog:** Used for "End of Game" and "Failed" notifications.

## 7. General logic of the class
`ExerciseGame` is the **Main Engine** for practice. It follows a loop: Display Question -> Collect Input -> Check Answer -> Next Question. It keeps a tally of how well you are doing and makes sure that data is stored permanently before you leave.

## 8. Simplified explanation
Think of `ExerciseGame` as a **Quiz Master**. The Quiz Master shows you a card with a word. You have a small keyboard with only three symbols. You type the symbols you think are correct. The Quiz Master keeps track of your points and mistakes. At the end, the Master writes your score in a big book (the database) so you can look at it later.

---
**Bugs/Improvements:**
*   The `updateTryIndicators` logic was missing from the provided code snippet (replaced by a comment). It should change the color of the "O" symbols to red when an attempt is failed.
*   The `executorService` should be shut down properly.
