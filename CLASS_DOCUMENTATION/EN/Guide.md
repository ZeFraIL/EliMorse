# Class: Guide

## 1. General information
*   **Class Name:** `Guide`
*   **Type:** Activity
*   **Purpose:** This activity serves as a sub-menu for the learning section of the app. It provides buttons for the user to navigate to different educational resources: "What is Morse code?", "How to use it?" (video), and a "Morse Dictionary".
*   **Interactions:** Inherits from `BaseActivity`. It opens `GuideWhat`, `GuideHow`, or `GuideList` activities.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `user` (inherited) | `User` | Current user info. | Passed to sub-guide activities. |

## 3. Classroom Methods

### Method name: `onCreate`
*   **Type:** `protected`
*   **Logic:**
    1. Sets the layout to `activity_guide.xml`.
    2. Initializes variables via `initElements()`.
    3. Sets click listeners for three buttons: `bWhat`, `bHow`, and `bList`. Each button starts its respective activity.
    4. Sets up the popup menu on `tvMenuGuide`.
*   **When called:** When the user clicks "Guide" from the DashBoard.

### Method name: `initElements`
*   **Type:** `private`
*   **Logic:** Retrieves the `User` object from the Intent.
*   **When called:** During `onCreate`.

## 4. Lifecycle
*   **`onCreate()`**: Prepares the guide navigation screen.

## 5. Interface Interaction (UI)
*   **Buttons:** `bWhat`, `bHow`, `bList`. Used for navigation.
*   **TextView (`tvMenuGuide`):** Used as a menu trigger.

## 6. Interaction with other components
*   **Intents:** Used to launch specialized guide activities.

## 7. General logic of the class
`Guide` is a **Directory**. It doesn't contain the actual information itself; instead, it points the user to the specific type of information they are looking for (reading, watching, or looking up symbols).

## 8. Simplified explanation
Think of `Guide` as a **Library Lobby**. When you walk into the library, you see signs for the Reading Room (GuideWhat), the Media Room (GuideHow), and the Reference Desk (GuideList). You click a button to go to the room you want.

---
**Bugs/Improvements:**
*   The class is very simple. It follows good practice by separating navigation from the actual content screens.
