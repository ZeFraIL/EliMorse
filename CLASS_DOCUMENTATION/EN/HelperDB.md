# 🧩 HelperDB Documentation

## 1. General Information
*   **Name:** `HelperDB`
*   **Type:** Normal Java Class (Extends `SQLiteOpenHelper`)
*   **Purpose:** This class is responsible for creating and managing the local database file named `Info.db`. It defines the structure (tables and columns) of where all user data and exercise scores are stored.
*   **Interaction:** Used by `UserRepository` and other activities that need to save results.

## 2. Variables (Constants)
| Name | Type | Purpose |
| :--- | :--- | :--- |
| `DATABASE_NAME` | `String` | The name of the file on the phone: "Info.db". |
| `TABLE_USER` | `String` | The name of the user table: "Users". |
| `COLUMN_USER_NAME` | `String` | The name of the column that stores the user's name. |
| `TABLE_EXERCISE` | `String` | The name of the exercise results table: "Exercises". |

## 3. Methods
### Method: `onCreate`
*   **Type:** `public`
*   **Returns:** `void`
*   **Parameters:** `db` (`SQLiteDatabase`)
*   **What it does:** This method runs **only once** when the app is installed and the database is created for the first time. It executes SQL commands to create the `Users` and `Exercises` tables.

### Method: `onUpgrade`
*   **Type:** `public`
*   **What it does:** Runs if you change the `DATABASE_VERSION`. In this app, it deletes the old tables and creates new ones. *Note: In a professional app, you would use this to add new columns without deleting user data.*

## 5. Database Structure (Tables)
1.  **Users Table**: Stores `_id`, `User_Name`, `User_Password`, `User_Email`, `User_Phone`.
2.  **Exercises Table**: Stores `_id`, `Exercise_Kind`, `Exercise_Mistakes`, `Exercise_Date`, `Exercise_User` (linking to the user).

## 7. General Logic
This class acts as the "Architect" of the database. It doesn't usually search for data; it just makes sure the "building" (the database structure) is ready and has the right "rooms" (tables) for the data.

## 8. Simple Explanation
Think of `HelperDB` as the **Building Plan** for a filing cabinet. It decides how many drawers the cabinet has (Tables) and what labels are on each folder (Columns). It makes sure that when the app starts for the first time, the cabinet is built correctly so that information can be stored inside.
