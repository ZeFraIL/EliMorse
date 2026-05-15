# Class: Exercise

## 1. General information
*   **Class Name:** `Exercise`
*   **Type:** Activity
*   **Purpose:** This screen allows the user to choose which type of Morse code exercise they want to practice. It presents four options: Numbers, Words, Letters, and Listening.
*   **Interactions:** It inherits from `BaseActivity`. Based on the user's choice, it starts either `ExerciseGame` (for encoding) or `ListeningExerciseActivity` (for decoding sound).

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `rbNumbers` | `RadioButton` | Option for Numbers exercise. | Checked in `findGameType`. |
| `rbWords` | `RadioButton` | Option for Words exercise. | Checked in `findGameType`. |
| `rbLetters` | `RadioButton` | Option for Letters exercise. | Checked in `findGameType`. |
| `rbListening` | `RadioButton` | Option for Listening exercise. | Checked in `findGameType`. |
| `bNext` | `Button` | Button to proceed. | Click listener starts the exercise. |
| `user` (inherited) | `User` | Current user info. | Passed to the next activity. |

## 3. Classroom Methods

### Method name: `onCreate`
*   **Type:** `protected`
*   **Return value:** `void`
*   **Logic:**
    1. Sets the layout to `activity_exercise.xml`.
    2. Calls `initElements()` to initialize variables.
    3. Sets a listener for the menu to show the popup.
    4. Sets a listener for the `bNext` button. When clicked:
        *   Determines which exercise type is selected using `findGameType()`.
        *   If selected, creates an intent for the correct activity (`ExerciseGame` or `ListeningExerciseActivity`).
        *   Passes the `gameType` and `user` object to the next activity.
        *   If nothing is selected, shows a toast message.
*   **When called:** When the user clicks "Exercise" on the DashBoard.

### Method name: `initElements`
*   **Type:** `private`
*   **Return value:** `void`
*   **Logic:**
    1. Retrieves the `User` object from the Intent.
    2. Links Java variables to XML elements.
*   **When called:** Inside `onCreate`.

### Method name: `findGameType`
*   **Type:** `public`
*   **Return value:** `int` (Game type constant)
*   **Logic:** Checks which RadioButton is selected and returns the corresponding integer constant from `GameConstants`. Returns -1 if none are selected.
*   **When called:** When the "Next" button is clicked.

## 4. Lifecycle
*   **`onCreate()`**: Prepares the selection screen.

## 5. Interface Interaction (UI)
*   **RadioGroup/RadioButtons:** Used for mutually exclusive selection of exercise types.
*   **Button (`bNext`):** Triggers navigation to the practice screens.

## 6. Interaction with other components
*   **GameConstants:** Provides predefined integer values for each game type to ensure consistency across activities.
*   **Intents:** Used to send the `KEY_SENDER` (game type) and `user` data to the next screen.

## 7. General logic of the class
The `Exercise` class acts as a **Selector**. It's a simple menu where you pick a category. Once you decide (by clicking Next), it hands you off to the actual training ground, making sure to tell the training ground exactly what you want to practice.

## 8. Simplified explanation
Think of `Exercise` as a **Vending Machine**. The machine has four buttons (RadioButtons) representing different snacks (Numbers, Words, etc.). You press one button to choose, and then you pull the handle (Next button). The machine then delivers the specific snack you asked for.

---
**Bugs/Improvements:**
*   There is no default selection. It might be better to have "Letters" or "Numbers" selected by default so the user doesn't get an error toast if they just click "Next".
*   The `findGameType` method is `public` but is only used within this activity; it could be `private`.
