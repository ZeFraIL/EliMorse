# 📱 ExerciseGame Activity Documentation

## 1. General Information
*   **Name:** `ExerciseGame`
*   **Type:** Activity (Extends `BaseActivity`)
*   **Purpose:** This is the main screen for the learning games. It presents the user with a question (a letter, word, or number) and asks them to type the correct Morse code using buttons for "dot", "dash", and "space".
*   **Interaction:** Uses `MorseTranslator` to check answers and `HelperDB` to save the final score.

## 2. Variables (Class Fields)
| Name | Type | Purpose | Usage |
| :--- | :--- | :--- | :--- |
| `questionNum` | `int` | Keeps track of which question the user is on. | Incremented after every correct answer. |
| `failedAttempts` | `int` | Counts the total mistakes in the session. | Saved to the database at the end. |
| `stAnswer` | `String` | Stores the Morse code the user is currently typing. | Updated when "dot" or "dash" buttons are clicked. |
| `questions` | `List<Question>` | A list of 10 random questions generated for the game. | Loaded at the start of the activity. |

## 3. Methods
### Method: `checkAnswer`
*   **Type:** `private`
*   **What it does:** Compares the string in `stAnswer` with the correct Morse code for the current question. 
    *   If **Correct**: Moves to the next question. If it was the last question, it saves results and shows a success dialog.
    *   If **Wrong**: Increases the mistake counter. After 3 wrong tries for one question, it shows a "Game Over" message.
*   **When called:** When the user clicks the "Ready" (Check) button.

### Method: `saveResults`
*   **Type:** `private`
*   **What it does:** Uses a background thread to insert the game type, number of mistakes, and the date into the `Exercises` table in the database.

## 5. UI Interaction
*   **Buttons:** `bDot`, `bDash`, `bSpace` are used for input. `bReady` submits the answer. `bClear` and `bRemove` help correct mistakes.
*   **TextViews:** `tvQuestion` shows the target word, and `tvInputNumber` shows what the user has typed so far.

## 7. General Logic
1. The app picks 10 random words.
2. The user sees a word (e.g., "CAT").
3. The user taps out the Morse code.
4. If correct, the app shows the next word.
5. At the end, the total mistakes are saved so the user can see them in their "History".

## 8. Simple Explanation
Think of `ExerciseGame` as a **Digital Quiz**. It's like a teacher showing you flashcards. If you "write" the correct Morse code, you get to move to the next card. At the end of the lesson, the teacher writes your grade in a **Gradebook** (the database).
