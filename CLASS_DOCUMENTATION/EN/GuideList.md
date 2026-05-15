# Class: GuideList

## 1. General information
*   **Class Name:** `GuideList`
*   **Type:** Activity
*   **Purpose:** This screen acts as a comprehensive Morse code dictionary. it reads a list of all Morse code symbols and their corresponding letters/numbers from a raw resource file and displays them in a scrollable list.
*   **Interactions:** Inherits from `BaseActivity`. It uses the `MorseCode` data model and reads from `R.raw.all_morse`.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `info` | `ArrayList<String>`| Stores the strings to be displayed in the list. | Populated during file reading and passed to the adapter. |
| `lv` | `ListView` | The UI component for the scrollable list. | Displays the dictionary items. |
| `user` (inherited) | `User` | Current user info. | Used for menu consistency. |

## 3. Classroom Methods

### Method name: `onCreate`
*   **Type:** `protected`
*   **Logic:**
    1. Sets the layout to `activity_guide_list.xml`.
    2. Calls `initElements()` to load data and setup UI.
    3. Sets up the popup menu.
*   **When called:** When the user clicks "Morse Dictionary" in the Guide menu.

### Method name: `initElements`
*   **Type:** `private`
*   **Logic:**
    1. Retrieves the user object.
    2. Opens the `all_morse` file from the `res/raw` folder.
    3. Uses a `BufferedReader` to read the file line by line.
    4. Since the file is structured with two lines per entry (Code then Letter), it reads two lines at a time.
    5. Creates a `MorseCode` object for each pair and adds its string representation to the `info` list.
    6. Handles potential errors (IO exceptions) with a log and toast.
    7. Creates an `ArrayAdapter` and attaches it to the `ListView`.
*   **When called:** During `onCreate`.

## 4. Lifecycle
*   **`onCreate()`**: Triggers the data loading and display.

## 5. Interface Interaction (UI)
*   **ListView (`lv`):** The primary way users interact with the dictionary by scrolling.

## 6. Interaction with other components
*   **MorseCode Class:** Used to format the data into a readable "Code -> Letter" format.
*   **Raw Resources:** Reads from a local text file packaged with the app.

## 7. General logic of the class
`GuideList` is a **Data Loader**. It transforms a static text file into a dynamic, scrollable list on the phone screen. It ensures that the information is presented in a clean, consistent format.

## 8. Simplified explanation
Think of `GuideList` as a **Digital Cheat Sheet**. The app has a "hidden" list of all the dots and dashes in its memory. When you open this screen, the app reads that list and prints it out on your screen so you can look up any symbol you don't know.

---
**Bugs/Improvements:**
*   **Formatting:** The current `MorseCode.toString()` uses spaces for alignment. On some screens, the columns might not line up perfectly. Using a custom layout for the list items would look more professional.
*   **File Reading:** The `try-with-resources` block is a good practice as it ensures the file is closed automatically.
