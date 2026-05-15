# Class: GameConstants

## 1. General information
*   **Class Name:** `GameConstants`
*   **Type:** Normal Class (Constants Container)
*   **Purpose:** This class acts as a single point of truth for the "Game Types" in the app. It uses integer numbers to represent different activities (Numbers, Words, Letters, Listening).
*   **Interactions:** Used by `Exercise`, `ExerciseGame`, and `QuestionRepository` to make sure they are all talking about the same activity type.

## 2. Variables (class fields)
| Name | Type | Value | Purpose |
| :--- | :--- | :--- | :--- |
| `GAME_TYPE_NUMBERS` | `int` | 0 | Identifier for the Numbers exercise. |
| `GAME_TYPE_WORDS` | `int` | 1 | Identifier for the Words exercise. |
| `GAME_TYPE_LETTERS` | `int` | 2 | Identifier for the Letters exercise. |
| `GAME_TYPE_LISTENING`| `int` | 3 | Identifier for the Listening exercise. |

## 3. Classroom Methods
*   **None:** This class only holds data.

## 4. Lifecycle
*   **N/A:** Exists as long as the app is in memory.

## 5. Interaction with other components
*   **Intents:** These constants are often passed as "Extras" in Intents to tell the next screen which game to load.

## 6. General logic of the class
`GameConstants` is the **Rulebook**. Instead of writing "0" in one file and "Numbers" in another, everyone just looks at this rulebook to see that "0" means "Numbers". This prevents mistakes where one part of the app thinks 0 is one thing and another part thinks it's something else.

## 7. Simplified explanation
Think of `GameConstants` as a **Menu with Numbers**. Instead of shouting "I want a Hamburger" at the chef, you say "I want a number 1". The waiter and the chef both have the same menu, so they both know exactly what a "number 1" is.
