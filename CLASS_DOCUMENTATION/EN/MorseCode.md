# Class: MorseCode

## 1. General information
*   **Class Name:** `MorseCode`
*   **Type:** Normal Class (Data Model)
*   **Purpose:** This class represents a single entry in the Morse code dictionary. It pairs a character (like 'A') with its Morse representation (like '.-').
*   **Interactions:** It is used by `GuideList` to display the dictionary items and can be passed between activities using serialization.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `mCode` | `String` | The Morse code sequence (dots/dashes). | Displayed in lists. |
| `mLetter` | `String` | The plain text character. | Displayed in lists. |

## 3. Classroom Methods

### Method name: `MorseCode` (Constructor)
*   **Type:** `public`
*   **Logic:** Simple assignment of values.
*   **When called:** When reading the dictionary file in `GuideList`.

### Method name: `toString`
*   **Type:** `public`
*   **Return value:** `String`
*   **Logic:** Returns a string formatted as "code   letter" for easy display.
*   **When called:** When adding items to the `ListView` in `GuideList`.

## 4. Lifecycle
*   **N/A:** Simple object.

## 5. Interaction with other components
*   **Serializable:** Allows the object to be sent through Intents.

## 6. General logic of the class
`MorseCode` is a **Label**. One side says ".-" and the other side says "A". It's a way to keep these two pieces of information glued together.

## 7. Simplified explanation
Think of `MorseCode` as a **Duo-Card**. On one side is a picture (Morse code), and on the other is the word (the Letter). The app uses these cards to help you study.
