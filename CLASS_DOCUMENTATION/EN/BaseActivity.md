# 📱 BaseActivity Documentation

## 1. General Information
*   **Name:** `BaseActivity`
*   **Type:** Abstract Class (Activity Parent)
*   **Purpose:** This is a "parent" or "template" class. It contains code that is used by many other screens (Activities) in the app. Instead of writing the same code for the menu or voice guidance in every screen, we write it here once.
*   **Interaction:** Other classes like `DashBoard`, `Translate`, and `ExerciseGame` inherit from this class.

## 2. Variables (Class Fields)
| Name | Type | Purpose | Usage |
| :--- | :--- | :--- | :--- |
| `user` | `User` | Stores information about the logged-in user. | Used to pass the user's name and ID between different screens. |

## 3. Methods
### Method: `showPopupMenu`
*   **Type:** `protected`
*   **Returns:** `void` (nothing)
*   **Parameters:**
    *   `anchorView` (`TextView`): The UI element (text) that was clicked to open the menu.
*   **What it does:** Creates and displays a popup menu. It handles button clicks like "Guide", "Credits", or "Exit".
*   **When called:** Usually when a user clicks the "Eli-Morse" logo or a menu button at the top.

### Method: `navigateTo`
*   **Type:** `protected`
*   **Returns:** `void`
*   **Parameters:**
    *   `activityClass` (`Class<?>`): The destination screen.
    *   `sectionName` (`String`): The name of the section (for the voice assistant).
*   **What it does:** Simplifies moving from one screen to another. It automatically includes the `user` object and announces where the user is going using TTS.

### Method: `sayWhat`
*   **Type:** `protected`
*   **Returns:** `void`
*   **Parameters:**
    *   `sayThis` (`String`): The text to be spoken.
*   **What it does:** Starts the `TTS_Service` to announce text out loud.

## 7. General Logic
The `BaseActivity` acts as a foundation. It ensures that every screen in the app has access to the same navigation menu and the same voice assistant, making the app consistent and easier to maintain.

## 8. Simple Explanation
Imagine you are building several different LEGO houses. Instead of building a new front door for every single house, you create one **Master Door**. `BaseActivity` is that "Master Door" for our app's screens. It provides the menu and the "voice" so you don't have to rebuild them every time.
