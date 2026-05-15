# Class: QuestionRepository

## 1. General information
*   **Class Name:** `QuestionRepository`
*   **Type:** Normal Class (Factory / Repository)
*   **Purpose:** This class is the "Factory" that creates the actual content for the exercises. It randomly selects words, letters, or numbers and converts them into `Question` objects by using the translator.
*   **Interactions:** Used by `ExerciseGame` and `ListeningExerciseActivity`. It relies on the `MorseTranslator` class to generate the correct answers.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `translator` | `MorseTranslator` | The encoding engine. | Used to get the Morse version of random words. |
| `e_words` | `String[]` | List of 20 English words. | Source for word exercises. |
| `e_letters` | `String[]` | List of 26 English letters. | Source for letter exercises. |

## 3. Classroom Methods

### Method name: `generateQuestion`
*   **Type:** `public`
*   **Return value:** `Question` (The generated item)
*   **Parameters:** `gameType (int)` — One of the constants from `GameConstants`.
*   **Logic:**
    1. Uses a `switch` statement to check the desired game type.
    2. **Numbers:** Generates a random number between 0 and 100.
    3. **Words:** Picks a random item from the `e_words` array.
    4. **Letters:** Picks a random item from the `e_letters` array.
    5. Calls `translator.toMorse()` on the picked text.
    6. Combines the text and Morse into a new `Question` object.
*   **When called:** When an exercise starts or moves to the next item.

### Method name: `generateQuestions`
*   **Type:** `public`
*   **Return value:** `List<Question>`
*   **Parameters:** `gameType (int)`, `count (int)`
*   **Logic:** Simple loop that calls `generateQuestion` multiple times to fill a list.
*   **When called:** Usually at the very beginning of an exercise to prepare all 10 questions at once.

## 4. Lifecycle
*   **N/A:** Utility class.

## 5. Interaction with other components
*   **MorseTranslator:** Essential for providing the correct answers.
*   **GameConstants:** Ensures the input `gameType` matches the app's standard.

## 6. General logic of the class
`QuestionRepository` is a **Question Generator**. It is like a person with a hat full of words and a dictionary. They pull a word out of the hat, look up the Morse code in the dictionary, and hand you a card with both.

## 7. Simplified explanation
Think of `QuestionRepository` as a **Quiz Maker**. Instead of a human writing the questions, this code "rolls the dice" to pick a random word from its memory and prepares it for your exam.

---
**Bugs/Improvements:**
*   **Duplicate Questions:** Since it uses `Random` without checking, it's possible (though unlikely with 10 questions) to get the same word twice in a single session.
*   **Listening Logic:** In `generateQuestion`, the `GAME_TYPE_LISTENING` case just reuses words. It might be better to have a dedicated list for listening or a mix of types.
