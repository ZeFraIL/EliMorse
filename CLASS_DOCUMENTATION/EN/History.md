# Class: History

## 1. General information
*   **Class Name:** `History`
*   **Type:** Activity
*   **Purpose:** This screen displays a list of all exercises completed by the logged-in user. It shows the date of the exercise, what type it was (Numbers, Letters, etc.), and how many mistakes were made.
*   **Interactions:** Inherits from `BaseActivity`. It reads data from the `HelperDB` (SQLite database) and displays it in a `ListView`.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `alAll` | `ArrayList<Exercises>` | Stores the raw exercise objects retrieved from the DB. | Populated in `BuildInfo`. |
| `alInfo` | `ArrayList<String>` | Stores formatted text lines for display in the list. | Populated in `BuildInfo` and passed to the adapter. |
| `helperDB` | `HelperDB` | Database assistant. | Used to open a readable database connection. |
| `lvInfo` | `ListView` | The UI component that displays the list. | Initialized in `initElements`. |
| `adapter` | `ArrayAdapter<String>` | Connects the data (`alInfo`) to the view (`lvInfo`). | Created and set in `BuildInfo`. |

## 3. Classroom Methods

### Method name: `onCreate`
*   **Type:** `protected`
*   **Logic:**
    1. Sets the layout to `activity_history.xml`.
    2. Calls `initElements()` to setup the list and load data.
    3. Sets a click listener for the menu.
*   **When called:** When the user clicks "History" on the DashBoard.

### Method name: `BuildInfo`
*   **Type:** `private`
*   **Logic:**
    1. Opens the database for reading.
    2. Queries the `Exercises` table for all records.
    3. Iterates through the results using a `Cursor`.
    4. For each record, it checks if the `exUser` matches the current logged-in user's name.
    5. If it matches, it creates an `Exercises` object and adds a formatted string to `alInfo`.
    6. Closes the cursor and database.
    7. Creates an `ArrayAdapter` and attaches it to the `ListView`.
*   **When called:** Inside `initElements`.
*   **What is important to understand:** The data is filtered manually in the code (`if(stUser.equals(user.getUserName()))`). A better way would be to use a SQL `WHERE` clause in the query.

### Method name: `initElements`
*   **Type:** `private`
*   **Logic:**
    1. Retrieves the current `User` object.
    2. Initializes lists and the DB helper.
    3. Sets the title text to show the user's name (e.g., "Elia's Exercise History").
    4. Calls `BuildInfo()` to fetch and show the data.
*   **When called:** During `onCreate`.

## 4. Lifecycle
*   **`onCreate()`**: Fetches and displays the historical data.

## 5. Interface Interaction (UI)
*   **ListView (`lvInfo`):** Shows the scrolling list of results.
*   **TextView (`tv`):** Shows the header title.

## 6. Interaction with other components
*   **HelperDB:** The source of all historical data.
*   **Exercises Class:** Used as a data model to store record details.

## 7. General logic of the class
The `History` activity is a **Report Viewer**. It goes to the app's "storage room" (the database), looks through all the files, picks out the ones belonging to the current user, and lists them neatly on the screen so the user can see their progress over time.

## 8. Simplified explanation
Think of `History` as a **Report Card**. Every time you finish a workout (exercise), the app writes a note about it. When you open the `History` screen, it's like opening your notebook to see all your past grades and dates.

---
**Bugs/Improvements:**
*   **Performance:** The code currently loads *all* exercises from the database and then checks the name in Java. If there are thousands of exercises from many users, this will be very slow. It is much better to ask the database only for the current user's records using `db.query(..., selection, selectionArgs, ...)`.
*   **Cursor Management:** In the current code, the cursor is only closed if there are records. If `cursor.getCount() == 0`, the cursor is not explicitly closed (though the DB is). It's best to use `try-with-resources` or a `finally` block to ensure the cursor always closes.
