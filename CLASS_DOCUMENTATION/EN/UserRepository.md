# Class: UserRepository

## 1. General information
*   **Class Name:** `UserRepository`
*   **Type:** Normal Class (Repository / Data Access Object)
*   **Purpose:** This class is responsible for all database operations related to users. It acts as an intermediary between the activities and the SQLite database, providing clean methods like `registerUser` instead of raw SQL queries.
*   **Interactions:** Used by the `LogAndReg` activity to check credentials and save new users. It uses `HelperDB` to get database access.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `helperDB` | `HelperDB` | The manager of the SQLite file. | Used in every method to open the database. |

## 3. Classroom Methods

### Method name: `findUser`
*   **Type:** `public`
*   **Return value:** `User` (The found user object or `null`)
*   **Parameters:** `name (String)`, `password (String)`
*   **Logic:**
    1. Opens the database for reading.
    2. Runs a query to find a row where the name and password match.
    3. If a row is found, it uses `mapCursorToUser` to turn that row into a Java object.
    4. Closes the connection and returns the user.
*   **When called:** When a user tries to log in.

### Method name: `registerUser`
*   **Type:** `public`
*   **Return value:** `long` (The new ID of the user)
*   **Parameters:** `user (User)`
*   **Logic:**
    1. Opens the database for writing.
    2. Packages the user's data into a `ContentValues` object.
    3. Tells the database to insert the new row.
    4. Returns the generated ID.
*   **When called:** When a new user signs up.

### Method name: `mapCursorToUser`
*   **Type:** `private`
*   **Logic:** Reads data from a database `Cursor` (which is like a pointer to a result row) and builds a `User` object from it.
*   **What is important to understand:** This is a helper method to keep the code clean and avoid repetition.

## 4. Lifecycle
*   **N/A:** Exists as long as the activity using it exists.

## 5. Interaction with other components
*   **SQLite Database:** The target for all data storage.
*   **LogAndReg Activity:** The main user of this repository.

## 6. General logic of the class
`UserRepository` is a **Filing Clerk**. When an activity wants to find someone, it asks the Clerk. The Clerk then goes to the storage room (the database), looks for the right folder, and brings it back as a `User` object.

## 7. Simplified explanation
Think of `UserRepository` as a **Digital Security Guard**. The activities don't have the key to the vault (the database). They ask the Guard: "Is there a user named Bob?". The Guard checks the vault and gives the activity a thumbs up (User object) or thumbs down (null).

---
**Bugs/Improvements:**
*   **Password Storage:** As noted in other classes, storing passwords in plain text is a security risk. The `registerUser` method should ideally hash the password before saving.
*   **Duplicate User Check:** Currently, `registerUser` doesn't check if the username is already taken. This check is performed in the Activity, but it would be more robust to handle it here or set a `UNIQUE` constraint in the database.
