# Class: Welcome

## 1. General information
*   **Class Name:** `Welcome`
*   **Type:** Activity
*   **Purpose:** This is the first screen the user sees (the Splash Screen). Its main job is to welcome the user, show the application logo with a nice animation, and announce a welcome message using voice (TTS). It then automatically moves the user to the login and registration screen after a few seconds.
*   **Interactions:** It starts the `TTS_Service` to play the welcome message and launches the `LogAndReg` activity.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `ivLogo` | `ImageView` | Displays the application's logo. | Used in `initElements` to start rotation and zoom animations. |
| `bStart` | `Button` | A button that says "Start". | Used in `onCreate` to allow the user to skip the timer and go to the next screen immediately. |
| `context` | `Context` | Stores the activity context. | Used for loading animations and starting the service. |
| `cdt` | `CountDownTimer` | A timer that waits for 7 seconds. | Used to automatically transition to the next screen if the user doesn't click anything. |

## 3. Classroom Methods

### Method name: `onCreate`
*   **Type:** `protected`
*   **Return value:** `void`
*   **Parameters:** `savedInstanceState (Bundle)`
*   **Logic:**
    1. Calls `applyTheme()` to set the correct visual style.
    2. Sets the layout to `activity_welcome.xml`.
    3. Initializes components via `initElements()`.
    4. Sets up and starts a 7-second `CountDownTimer`.
    5. Sets a click listener for the "Start" button to cancel the timer and move forward.
*   **When called:** Automatically by the Android system when the app starts.

### Method name: `startMainScreen`
*   **Type:** `private`
*   **Return value:** `void`
*   **Logic:**
    1. Creates an `Intent` to open `LogAndReg`.
    2. Starts the activity.
    3. Calls `finish()` so the user cannot go back to the splash screen by pressing the back button.
*   **When called:** When the timer finishes or the "Start" button is clicked.

### Method name: `applyTheme`
*   **Type:** `private`
*   **Return value:** `void`
*   **Logic:**
    1. Opens `SharedPreferences` named "theme_prefs".
    2. Reads the saved theme mode (Light/Dark/System).
    3. Tells the system to apply this mode.
*   **When called:** At the very beginning of `onCreate`.

### Method name: `initElements`
*   **Type:** `private`
*   **Return value:** `void`
*   **Logic:**
    1. Links Java variables to XML elements (`ivLogo`, `bStart`).
    2. Loads a "zoom in" animation and starts it on the logo.
    3. Starts a rotation animation (360 degrees) on the logo.
    4. Starts `TTS_Service` with a welcome message from the string resources.
*   **When called:** During `onCreate`.

## 4. Lifecycle
*   **`onCreate()`**: Called when the activity is created. Here, the timer starts and animations begin.
*   **`onPause()`, `onStop()`**: Standard behavior. Note: The timer continues unless explicitly cancelled (which happens if the user clicks Start).

## 5. Interface Interaction (UI)
*   **ImageView (`ivLogo`):** Shows the logo. Animated using `AnimationUtils`.
*   **Button (`bStart`):** Allows immediate entry to the app. Handled via `setOnClickListener`.

## 6. Interaction with other components
*   **LogAndReg Activity:** Opened via `Intent` after the splash screen.
*   **TTS_Service:** Used to say "Welcome to Eli Morse" (or similar message).
*   **SharedPreferences:** Used to load the user's preferred theme.

## 7. General logic of the class
The `Welcome` activity is a "waiting room" with a show. It plays animations and a voice message while waiting for 7 seconds. If the user is impatient, they click "Start". In either case, it leads to the login screen and closes itself.

## 8. Simplified explanation
Think of the `Welcome` screen as the **Opening Credits** of a movie. It shows the title (Logo), plays some music (TTS voice), and sets the mood. After a few seconds, the movie (the actual app) starts automatically, or you can press "Skip" (the Start button) to jump straight into the action.

---
**Bugs/Improvements:**
*   The `cdt` is started but only cancelled if `bStart` is clicked. If the activity is destroyed (e.g., screen rotation) before the timer ends, it might cause issues or multiple launches of the next screen. It's better to cancel the timer in `onDestroy`.
*   The `context` variable is assigned `this` but is mostly redundant as `this` can be used directly.
