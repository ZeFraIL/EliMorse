# Class: HelperDB

## 1. General information
*   **Class Name:** `HelperDB`
*   **Type:** Normal Class (SQLiteOpenHelper)
*   **Purpose:** This class defines the "skeleton" of the app's database. It specifies exactly what tables exist (Users and Exercises), what their columns are named, and what types of data they hold. It also handles creating the database for the first time and updating it if the structure changes.
*   **Interactions:** Used by `UserRepository` and activities that save results (like `ExerciseGame`). It is the foundation for all permanent data in the app.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `DATABASE_NAME` | `String` | The filename ("Info.db"). | Used by the system to find the file. |
| `TABLE_USER` | `String` | Name of the users table. | Used in SQL queries. |
| `COLUMN_USER_NAME`| `String` | Column for the username. | Used in SQL queries. |
| `TABLE_EXERCISE`| `String` | Name of the exercises table. | Used in SQL queries. |
| `COLUMN_EXERCISE_MISTAKES`| `String`| Column for the error count. | Used in SQL queries. |

## 3. Classroom Methods

### Method name: `onCreate`
*   **Type:** `public`
*   **Parameters:** `db (SQLiteDatabase)`
*   **Logic:**
    1. Builds two large "CREATE TABLE" strings using the variable names defined earlier.
    2. Executes these SQL commands to physically create the tables on the phone's memory.
*   **When called:** Automatically by Android the very first time the app tries to access the database.

### Method name: `onUpgrade`
*   **Type:** `public`
*   **Logic:**
    1. Deletes the old tables.
    2. Calls `onCreate()` to make new ones.
*   **When called:** When the `DATABASE_VERSION` is increased (e.g., from 1 to 2).
*   **What is important to understand:** This method currently deletes all data during an upgrade. In a real app, you would use code to move the old data to the new structure instead of deleting it.

## 4. Lifecycle
*   **N/A:** Managed by the Android system.

## 5. Interaction with other components
*   **SQLite Engine:** The class talks directly to the phone's built-in database engine.
*   **ContentValues:** Used in conjunction with this class to insert data cleanly.

## 6. General logic of the class
`HelperDB` is the **Architect**. It draws the floor plan for the house (the database). It decides where the kitchen (Users) and the living room (Exercises) are. It doesn't live in the house, but it makes sure the house is built correctly.

## 7. Simplified explanation
Think of `HelperDB` as an **Organizer**. It sets up a big cabinet with drawers. It labels the drawers "Users" and "Exercises". It also makes sure that if you ever need a bigger cabinet, it throws away the old one and sets up a new, better one.

---
**Bugs/Improvements:**
*   **Upgrade Strategy:** As mentioned, `DROP TABLE` is destructive. Using `ALTER TABLE` is better for production apps.
*   **Data Consistency:** The mistakes column was corrected in the comments but ensure the SQL string reflects the correct type (`INTEGER`).
 Schloss
