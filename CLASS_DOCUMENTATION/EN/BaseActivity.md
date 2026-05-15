# Class: BaseActivity

## 1. General information
*   **Class Name:** `BaseActivity`
*   **Type:** Activity (Abstract Base Class)
*   **Purpose:** This class serves as a "template" or "parent" for other activities in the application. It contains common logic that many screens need, such as the navigation menu and Text-to-Speech (TTS) announcements. Instead of rewriting the same menu code in every activity, other classes (like `DashBoard` or `Exercise`) "inherit" from this class.
*   **Interactions:** It is inherited by most other activities in the app. It uses `TTS_Service` to announce section names and interacts with various Activity classes during navigation.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `user` | `User` | Stores the currently logged-in user object. | Used to keep track of who is using the app across different screens. |

## 3. Classroom Methods

### Method name: `showPopupMenu`
*   **Type:** `protected`
*   **Return value:** `void` (None)
*   **Parameters:**
    | Name | Type | Description |
    | :--- | :--- | :--- |
    | `anchorView` | `TextView` | The UI element (like a "Menu" text) that the popup will attach to. |
*   **Logic:**
    1. Creates a `PopupMenu` object.
    2. Loads the menu items from the `total_menu.xml` resource file.
    3. Sets a listener to handle clicks on menu items (Guide, Credits, Reminder, Back, Exit).
    4. Navigates to the corresponding screen based on the clicked item.
*   **When called:** When a user clicks on a menu trigger (usually a TextView) in any activity that extends `BaseActivity`.
*   **What is important to understand:** This centralizes navigation logic. If you want to add a new item to the menu for all screens, you only need to change it here and in the XML.

### Method name: `navigateTo`
*   **Type:** `protected`
*   **Return value:** `void`
*   **Parameters:**
    | Name | Type | Description |
    | :--- | :--- | :--- |
    | `activityClass` | `Class<?>` | The class of the activity to open. |
    | `sectionName` | `String` | The name of the section (for the voice announcement). |
*   **Logic:**
    1. Creates an `Intent` to move to the specified activity.
    2. Attaches the current `user` object to the intent so the next screen knows who is logged in.
    3. Calls `sayWhat()` to announce where the user is going.
    4. Starts the new activity.
*   **When called:** Manually by child activities when they want to switch screens.
*   **What is important to understand:** It automates the process of passing user data and providing voice feedback.

### Method name: `sayWhat`
*   **Type:** `protected`
*   **Return value:** `void`
*   **Parameters:**
    | Name | Type | Description |
    | :--- | :--- | :--- |
    | `sayThis` | `String` | The name of the section or message to speak. |
*   **Logic:**
    1. Prepares a message like "You are going to [section name]".
    2. Starts the `TTS_Service` and sends this message to it.
*   **When called:** Inside `navigateTo()` or manually when a voice prompt is needed.
*   **What is important to understand:** This relies on `TTS_Service` being correctly registered in the `AndroidManifest.xml`.

## 4. Lifecycle
*   **`onCreate()`**: Not explicitly implemented in `BaseActivity` (it relies on child activities calling `super.onCreate()`).
*   **`onStart()`, `onResume()`, etc.** : Standard Activity lifecycle applies, but no specific custom logic is added in this base class.

## 5. Interface Interaction (UI)
*   **Elements:** It doesn't have its own layout, but it interacts with `TextView` elements passed to `showPopupMenu`.
*   **Handling:** It handles menu item clicks via `PopupMenu.OnMenuItemClickListener`.

## 6. Interaction with other components
*   **Intents:** Uses `Intent` to start new activities and pass the `User` object.
*   **Services:** Starts `TTS_Service` to provide audio feedback.

## 7. General logic of the class
`BaseActivity` is like a Swiss Army knife for other activities. Instead of every activity knowing how to show the menu or how to speak to the user, they just "ask" their parent (`BaseActivity`) to do it.
**Example use case:**
1. A user is on the `DashBoard` and clicks "Exercise".
2. `DashBoard` calls `navigateTo(Exercise.class, "Exercise")`.
3. `BaseActivity` tells the TTS service to say "You are going to Exercise section".
4. `BaseActivity` starts the `Exercise` activity and passes the user's data.

## 8. Simplified explanation
Imagine `BaseActivity` as a **General Rulebook** or a **Master Template**. If you are building different rooms in a house (Activities), `BaseActivity` provides the standard doors and the intercom system. Every room you build based on this template automatically gets a door to the hallway (the menu) and the ability to use the speakers (TTS).

---
**Bugs/Improvements:**
*   The `theme` logic in the menu is currently empty with a comment saying it's handled in `DashBoard`. It would be better to move the theme dialog logic here so all activities can change the theme easily.
*   The `user` object is `protected`, which is fine, but ensure all child activities call `super.onCreate` and initialize it properly to avoid `NullPointerException`.
