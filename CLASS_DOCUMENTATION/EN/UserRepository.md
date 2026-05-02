# 🧩 UserRepository Documentation

## 1. General Information
*   **Name:** `UserRepository`
*   **Type:** Normal Java Class (Repository/Data Access Object)
*   **Purpose:** This class acts as a bridge between the application logic and the database for everything related to users. It handles tasks like finding a user by name and password or saving a new user.
*   **Interaction:** Used by `LogAndReg` and `DashBoard`. It communicates directly with `HelperDB`.

## 2. Variables (Class Fields)
| Name | Type | Purpose |
| :--- | :--- | :--- |
| `helperDB` | `HelperDB` | The database helper that manages the connection to the SQLite file. |

## 3. Methods
### Method: `findUser`
*   **Type:** `public`
*   **Returns:** `User` (The found user object or `null` if not found)
*   **Parameters:** `name` (String), `password` (String)
*   **What it does:** 
    1. Opens the database for reading.
    2. Runs a query searching for a row where name AND password match.
    3. If a match is found, it converts the database row into a `User` object.
    4. Closes the connection and returns the object.

### Method: `registerUser`
*   **Type:** `public`
*   **Returns:** `long` (The ID of the newly inserted row)
*   **Parameters:** `user` (`User` object)
*   **What it does:** 
    1. Opens the database for writing.
    2. Prepares a `ContentValues` object containing the user's name, password, email, and phone.
    3. Inserts the data into the `Users` table.
    4. Returns the new user's unique ID.

## 7. General Logic
This class centralizes all "questions" we might have about users for the database. Instead of writing SQL code everywhere in the app, we write it here once. This makes the code cleaner and easier to fix.

## 8. Simple Explanation
Think of `UserRepository` as a **Librarian**. When you want a specific book (User), you don't go wandering through the shelves yourself. You ask the librarian: "Can you find the user named Elia with this password?". The librarian goes to the back (Database), finds the info, and brings it back to you.
