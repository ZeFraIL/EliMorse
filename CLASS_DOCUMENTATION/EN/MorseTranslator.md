# 🧩 MorseTranslator Documentation

## 1. General Information
*   **Name:** `MorseTranslator`
*   **Type:** Normal Java Class (Helper/Logic)
*   **Purpose:** This class contains the dictionaries and algorithms needed to convert human language (English/Numbers) into Morse code and back. It is the "engine" of the app.
*   **Interaction:** Used by `Translate`, `ExerciseGame`, and `QuestionRepository`.

## 2. Variables (Class Fields)
| Name | Type | Purpose |
| :--- | :--- | :--- |
| `morseMap` | `Map<String, String>` | A dictionary where the **Key** is a Letter (e.g., "A") and the **Value** is Morse (e.g., ".-"). |
| `textMap` | `Map<String, String>` | The reverse dictionary (Key = Morse, Value = Letter). Used for decoding. |

## 3. Methods
### Method: `toMorse`
*   **Type:** `public`
*   **Returns:** `String` (The Morse code)
*   **Parameters:** `text` (The user's sentence)
*   **What it does:** 
    1. Converts text to Uppercase.
    2. Loops through every character.
    3. Looks up the character in `morseMap`.
    4. If found, adds the Morse symbols to a `StringBuilder`.
    5. Returns the final result as a string.

### Method: `toText`
*   **Type:** `public`
*   **What it does:** Splits a Morse string by spaces. For each sequence, it looks up the letter in `textMap`. If a sequence is not recognized (e.g., `.......`), it adds a `?` to the result.

## 7. General Logic
This class acts like a translator at an airport. It doesn't show any screens; it just takes a "question" (input string) and provides an "answer" (translated string).

## 8. Simple Explanation
Think of `MorseTranslator` as a **Bilingual Dictionary**. It's like a book where one half translates English to Morse, and the other half translates Morse back to English. When the app needs to know what "HELLO" sounds like, it opens this "book."
