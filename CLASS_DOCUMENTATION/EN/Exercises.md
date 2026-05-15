# Class: Exercises

## 1. General information
*   **Class Name:** `Exercises`
*   **Type:** Normal Class (Data Model)
*   **Purpose:** This class is a "blueprint" for a finished exercise record. It groups together the type of exercise, how many mistakes were made, when it happened, and who did it.
*   **Interactions:** It is used primarily by the `History` activity to store and display records fetched from the database.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `exKind` | `String` | Category (e.g., "Numbers"). | Displayed in the history list. |
| `exMistake` | `String` | Number of errors. | Displayed in the history list. |
| `exDate` | `String` | Completion date. | Displayed in the history list. |
| `exUser` | `String` | Username of the performer. | Used for filtering in the `History` screen. |

## 3. Classroom Methods

### Method name: `Exercises` (Constructor)
*   **Type:** `public`
*   **Logic:** Assigns the passed values to the class fields.
*   **When called:** When the `History` activity creates a new record object from database results.

### Method name: `get...` / `set...`
*   **Type:** `public`
*   **Logic:** Standard methods to read or change the record data.

## 4. Lifecycle
*   **N/A:** A simple data storage class.

## 5. Interface Interaction (UI)
*   **None directly:** Its data is used to populate `ListView` rows.

## 6. Interaction with other components
*   **History Activity:** The main "consumer" of this class.
*   **HelperDB:** This class matches the structure of the `Exercises` table in the database.

## 7. General logic of the class
`Exercises` is a **Log Entry**. It doesn't "do" anything; it just "holds" information so it can be passed around as a single package instead of four separate variables.

## 8. Simplified explanation
Think of `Exercises` as a **Post-it Note**. After you finish a workout, the app writes: "Monday - Math - 2 mistakes - Elia" on a note. This class is that piece of paper.

---
**Bugs/Improvements:**
*   **Data Type:** The `exMistake` variable is a `String`. Since it represents a count, it would be more logical and efficient to store it as an `int`.
