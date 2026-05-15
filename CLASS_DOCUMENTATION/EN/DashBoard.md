# Class: DashBoard

## 1. General information
*   **Class Name:** `DashBoard`
*   **Type:** Activity
*   **Purpose:** This is the main screen of the application after logging in. It acts as a "hub" or "control center" where users can choose what they want to do: practice Morse code, translate text, read a guide, see their history, or set a reminder.
*   **Interactions:** It connects to almost every other feature-related activity in the app. It inherits from `BaseActivity` to use the shared menu and TTS features.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `sharedPreferences`| `SharedPreferences` | Stores app settings locally. | Used to save and load the user's preferred theme (Light/Dark). |
| `user` (inherited) | `User` | Current user info. | Passed to other activities during navigation. |

## 3. Classroom Methods

### Method name: `onCreate`
*   **Type:** `protected`
*   **Return value:** `void`
*   **Logic:**
    1. Sets the layout to `activity_dash_board.xml`.
    2. Calls `initElements()` to setup preferences and user data.
    3. Sets up click listeners for all six main navigation buttons (Exercise, Translate, Guide, About, History, Reminder).
    4. Sets a listener for the menu icon to show the popup menu.
*   **When called:** When the user successfully logs in and the dashboard opens.

### Method name: `showPopupMenu`
*   **Type:** `protected` (Overridden)
*   **Return value:** `void`
*   **Logic:** Similar to `BaseActivity`, but adds a specific option for "Theme" to change the app's appearance.
*   **When called:** When the user clicks the menu text at the top.

### Method name: `showThemeDialog`
*   **Type:** `private`
*   **Return value:** `void`
*   **Logic:**
    1. Shows a dialog with three choices: Light, Dark, System Default.
    2. When a choice is made, it changes the app's theme immediately using `AppCompatDelegate`.
    3. Saves the choice into `SharedPreferences` so the app remembers it next time it starts.
*   **When called:** When "Theme" is selected from the popup menu.

### Method name: `initElements`
*   **Type:** `private`
*   **Return value:** `void`
*   **Logic:**
    1. Initializes the `SharedPreferences` object.
    2. Retrieves the `User` object passed from the `LogAndReg` activity.
*   **When called:** Inside `onCreate`.

## 4. Lifecycle
*   **`onCreate()`**: Prepares the main hub. This is where the user spends most of their time navigating the app.

## 5. Interface Interaction (UI)
*   **Buttons:** `bExercise`, `bTranslate`, `bGuide`, `bAbout`, `bHistory`, `bReminder`. Each triggers a transition to a new screen.
*   **TextView (`tvMenu`):** Acts as the anchor for the navigation menu.

## 6. Interaction with other components
*   **Navigation:** Uses the `navigateTo()` method from `BaseActivity` to switch to other activities while passing the `User` object.
*   **Theme Management:** Interacts with the Android System's `AppCompatDelegate` to toggle night mode.
*   **Persistence:** Uses `SharedPreferences` to ensure settings persist after the app is closed.

## 7. General logic of the class
The `DashBoard` is like a **Main Menu** in a video game. It doesn't perform complex calculations itself; instead, it provides clear paths to the various "worlds" (features) of the app. It ensures that the user's identity is carried along to every screen they visit.

## 8. Simplified explanation
Think of the `DashBoard` as a **Grand Lobby** in a hotel. From this lobby, you have several doors leading to the gym (Exercises), the library (Guide), the front desk (About), etc. The lobby also has a switch to turn the lights on or off (Theme). When you leave the lobby to go to a room, the receptionist (the code) makes sure to remember your name (the User object).

---
**Bugs/Improvements:**
*   The `user` initialization in `initElements` should handle the case where the intent doesn't contain a user (though this shouldn't happen in normal flow).
*   Clicking "Back" in the menu calls `finish()`, which might take the user back to the login screen. It might be better to show a confirmation dialog or handle the back stack differently.
