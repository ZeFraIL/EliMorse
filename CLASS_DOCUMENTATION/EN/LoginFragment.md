# Class: LoginFragment

## 1. General information
*   **Class Name:** `LoginFragment`
*   **Type:** Fragment
*   **Purpose:** This class represents a sub-section of the `LogAndReg` screen. It specifically handles the UI for logging in, providing fields for the username and password.
*   **Interactions:** It is hosted by `LogAndReg`. The activity calls this fragment's methods to get the text entered by the user.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `etNameL` | `EditText` | Input field for the username. | Initialized in `onCreateView` and read in `getName`. |
| `etPasswordL`| `EditText` | Input field for the password. | Initialized in `onCreateView` and read in `getPassword`. |

## 3. Classroom Methods

### Method name: `onCreateView`
*   **Type:** `public`
*   **Return value:** `View` (The inflated UI)
*   **Logic:**
    1. Loads the `fragment_login.xml` layout.
    2. Finds the `EditText` elements within that layout.
    3. Returns the view to the system to be displayed.
*   **When called:** When the fragment is being created and added to the screen.

### Method name: `getName` / `getPassword`
*   **Type:** `public`
*   **Return value:** `String`
*   **Logic:** Simply returns the current text inside the respective `EditText` fields.
*   **When called:** By the parent Activity (`LogAndReg`) when the user clicks the "Confirm" button.

## 4. Lifecycle
*   **`onCreateView()`**: The most important lifecycle method for fragments. This is where the interface is built and linked to Java code.

## 5. Interface Interaction (UI)
*   **EditTexts:** Used for secure and non-secure text entry.

## 6. Interaction with other components
*   **LogAndReg (Activity):** Acts as the parent. The fragment doesn't perform any logic (like checking the database) itself; it just holds the data for the parent.

## 7. General logic of the class
`LoginFragment` is a **Data Collector**. It provides the text boxes and waits for the user to type. It doesn't know *why* it's collecting the data or *where* it goes; it just provides the name and password whenever the activity asks for them.

## 8. Simplified explanation
Think of `LoginFragment` as a **Small Paper Form** inside a larger folder. The folder (LogAndReg) shows the form to the user. You fill out your name and password on the form. When you're done, the folder "reads" what you wrote on the paper form.

---
**Bugs/Improvements:**
*   The class is clean and follows the Fragment design pattern well by exposing public methods to access UI data.
