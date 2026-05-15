# Class: MorseTranslator

## 1. General information
*   **Class Name:** `MorseTranslator`
*   **Type:** Normal Class (Utility/Logic)
*   **Purpose:** This is the "brain" of the translation features. It knows the entire Morse code alphabet and provides methods to convert English sentences into Morse (dots and dashes) and back.
*   **Interactions:** It is used by the `Translate` activity and the `QuestionRepository`. It does not interact with the UI directly.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `morseMap` | `Map<String, String>` | Dictionary for English -> Morse. | Used in `toMorse` for quick lookup. |
| `textMap` | `Map<String, String>` | Dictionary for Morse -> English. | Used in `toText` for quick lookup. |

## 3. Classroom Methods

### Method name: `toMorse`
*   **Type:** `public`
*   **Return value:** `String` (The Morse code version)
*   **Parameters:** `text (String)` — The English text.
*   **Logic:**
    1. Converts text to uppercase.
    2. Loops through each letter.
    3. Finds the letter in `morseMap`.
    4. Appends the dots/dashes to a `StringBuilder`.
    5. Adds a single space between letters and a `/` between words.
*   **When called:** When the user translates text or a question is generated.

### Method name: `toText`
*   **Type:** `public`
*   **Return value:** `String` (The English version)
*   **Parameters:** `morseCode (String)` — String of dots and dashes.
*   **Logic:**
    1. Splits the string by `/` to get words.
    2. Splits each word by spaces to get individual Morse letters.
    3. Finds the symbol in `textMap`.
    4. If not found, adds a `?`.
*   **When called:** When the user translates Morse code.

### Method name: `initMorseMap`
*   **Type:** `private`
*   **Logic:** Manually populates the maps with the International Morse Code standard (A=.-, B=-..., etc.).
*   **When called:** Automatically in the constructor when the object is created.

## 4. Lifecycle
*   **N/A:** Exists as a utility object in memory.

## 5. Interface Interaction (UI)
*   **None:** This class is pure logic.

## 6. Interaction with other components
*   **Translate Activity:** Relies on this class to perform the core task of the screen.

## 7. General logic of the class
`MorseTranslator` acts as an **Automated Dictionary**. It handles all the rules of formatting (like adding spaces between letters) so that other parts of the app don't have to worry about the details of Morse code grammar.

## 8. Simplified explanation
Think of `MorseTranslator` as a **Secret Decoder Ring**. You give it a word, and it turns it into code. You give it a code, and it turns it back into a word. It's the only part of the app that actually "knows" how to speak Morse code.

---
**Bugs/Improvements:**
*   **Efficiency:** The `toText` method uses `replaceAll("\\s+", " ")`, which is a bit slow for very long strings but fine for typical Morse messages.
*   **Special Characters:** Currently, it only handles A-Z and 0-9. Adding common punctuation (like periods or commas) would make the translator more robust.
