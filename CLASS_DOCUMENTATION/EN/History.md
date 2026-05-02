# 📱 History Activity Documentation

## 1. General Information
*   **Name:** `History`
*   **Type:** Activity
*   **Purpose:** This screen displays a list of all exercises the user has completed. It shows the date, the type of exercise, and how many mistakes were made.
*   **Interaction:** It reads data from the `HelperDB` and displays it using a `ListView`.

## 2. Variables (Class Fields)
| Name | Type | Purpose | Usage |
| :--- | :--- | :--- | :--- |
| `alInfo` | `ArrayList<String>` | A list of text strings formatted for display. | Each string looks like: "Date - Kind - Mistakes". |
| `lvInfo` | `ListView` | The UI component that shows the scrollable list. | Connected to the `adapter`. |
| `adapter` | `ArrayAdapter` | The bridge between the data and the UI. | It takes the `alInfo` list and draws it into the `ListView`. |

## 3. Methods
### Method: `BuildInfo`
*   **Type:** `private`
*   **What it does:** 
    1. Opens the database for reading.
    2. Queries the `Exercises` table to get all rows.
    3. Loops through the results (using a `Cursor`).
    4. For each row, it checks if the result belongs to the current user.
    5. Formats the data into a readable string and adds it to the list.
    6. Updates the `ListView` to show the data.
*   **When called:** Automatically during `onCreate` when the screen opens.

## 5. UI Interaction
*   **ListView (`lvInfo`):** This is the main part of the screen. It allows the user to scroll through their past results.
*   **TextView (`tv`):** Displays a title like "Elia's Exercise History".

## 7. General Logic
When the screen opens, the app goes to the "filing cabinet" (the database), pulls out all the folders marked "Exercises", filters out the ones that don't belong to the logged-in user, and lists the rest on the screen.

## 8. Simple Explanation
Think of the `History` screen as your **Personal Report Card**. Whenever you finish a game, the app saves your "grade". This screen simply opens the gradebook and shows you all your past scores so you can see how much you have improved.
