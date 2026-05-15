# Class: GuideWhat

## 1. General information
*   **Class Name:** `GuideWhat`
*   **Type:** Activity
*   **Purpose:** This screen explains the history and concept of Morse code. It is a text-based informational screen.
*   **Interactions:** Inherits from `BaseActivity`.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `user` (inherited) | `User` | Current user info. | Used for menu consistency. |

## 3. Classroom Methods

### Method name: `onCreate`
*   **Type:** `protected`
*   **Logic:**
    1. Sets the layout to `activity_guide_what.xml`.
    2. Initializes elements.
    3. Sets up the popup menu.
*   **When called:** When the user clicks "What is Morse code?" in the Guide menu.

### Method name: `initElements`
*   **Type:** `private`
*   **Logic:** Retrieves the user object from the Intent.
*   **When called:** During `onCreate`.

## 4. Lifecycle
*   **`onCreate()`**: Displays the information.

## 5. Interface Interaction (UI)
*   **TextViews:** Used in the XML layout to display the historical text and definitions.

## 6. Interaction with other components
*   **None:** This is a static information screen.

## 7. General logic of the class
`GuideWhat` is a **Textbook Page**. Its only job is to stay still and let the user read the interesting facts about how Samuel Morse invented his code.

## 8. Simplified explanation
Think of `GuideWhat` as an **Encyclopedia Page**. You open it to read about the "Who, When, and Why" of Morse code.

---
**Bugs/Improvements:**
*   The class is extremely simple and perfectly functional for showing static text.
*   Consider adding a scroll view if the text is very long.
