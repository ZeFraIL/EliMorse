# Class: LogAndReg

## 1. General information
*   **Class Name:** `LogAndReg`
*   **Type:** Activity
*   **Purpose:** This activity manages both user login and registration. It acts as a container for two fragments (`LoginFragment` and `RegistryFragment`) and handles the logic for switching between them and communicating with the database.
*   **Interactions:** It interacts with `LoginFragment` and `RegistryFragment` to get user input, uses `UserRepository` to check or save data in the database, and navigates to the `DashBoard` upon success.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `loginFragment` | `LoginFragment` | Object for the login screen. | Switched in via `showFragment`. |
| `registryFragment` | `RegistryFragment` | Object for the registration screen. | Switched in via `showFragment`. |
| `rbLogin` | `RadioButton` | Toggle for login mode. | Click listener to show `loginFragment`. |
| `rbRegister` | `RadioButton` | Toggle for registration mode. | Click listener to show `registryFragment`. |
| `bConfirm` | `Button` | The action button (Log in / Register). | Click listener to trigger auth logic. |
| `userRepository` | `UserRepository` | Handles database queries. | Used in `handleLogin` and `handleRegistration`. |
| `executorService` | `ExecutorService` | Background thread manager. | Used to run database queries off the UI thread. |
| `mainThreadHandler`| `Handler` | UI thread manager. | Used to post results back to the UI after background work. |

## 3. Classroom Methods

### Method name: `onCreate`
*   **Type:** `protected`
*   **Return value:** `void`
*   **Logic:**
    1. Sets the UI layout.
    2. Calls `initElements()` to setup components.
    3. Sets listeners on RadioButtons to switch fragments.
    4. Sets a listener on the Confirm button to start the login or registration process.
*   **When called:** When the activity starts.

### Method name: `showFragment`
*   **Type:** `private`
*   **Return value:** `void`
*   **Parameters:** `fragment (Fragment)` — The fragment to display.
*   **Logic:** Uses a `FragmentTransaction` to replace the current fragment in the container (`R.id.FL`) with the new one.
*   **When called:** When a RadioButton is clicked or during initialization.

### Method name: `handleRegistration`
*   **Type:** `private`
*   **Return value:** `void`
*   **Logic:**
    1. Collects data (name, password, mail, phone) from `registryFragment`.
    2. Validates fields using `validateFields()`.
    3. Starts a background thread via `executorService`.
    4. Checks if the user already exists.
    5. If not, saves the new user and proceeds to `DashBoard` on the main thread.
*   **When called:** When `bConfirm` is clicked and "Register" is selected.

### Method name: `handleLogin`
*   **Type:** `private`
*   **Return value:** `void`
*   **Logic:**
    1. Collects name and password from `loginFragment`.
    2. Validates fields.
    3. Starts a background thread to find the user in the database.
    4. If found, proceeds to `DashBoard`. If not, shows an error toast.
*   **When called:** When `bConfirm` is clicked and "Login" is selected.

### Method name: `validateFields`
*   **Type:** `private`
*   **Return value:** `boolean` (true if valid, false otherwise)
*   **Parameters:** `fields (String...)` — Variable number of strings to check.
*   **Logic:** Checks if any of the provided strings are null or empty.
*   **When called:** Before starting database operations.

## 4. Lifecycle
*   **`onCreate()`**: Initializes the app's first functional interaction. Sets the default fragment to `loginFragment`.

## 5. Interface Interaction (UI)
*   **RadioButtons:** Act as tabs to switch between Login and Register.
*   **FragmentContainer (`R.id.FL`):** A placeholder in the layout where fragments are swapped.
*   **Button (`bConfirm`):** Executes the main action.

## 6. Interaction with other components
*   **Fragments:** Uses getter methods in fragments to retrieve user input.
*   **UserRepository:** Abstracts SQLite operations.
*   **DashBoard Activity:** Started after successful authentication.
*   **ExecutorService:** Essential for "Async" operations. Android does not allow database work on the Main Thread because it might freeze the UI.

## 7. General logic of the class
`LogAndReg` is the gateway. It provides a choice: "Who are you?" or "Join us!". It collects info through small "mini-screens" (fragments). Once you click confirm, it goes to the "filing cabinet" (database) in the background to check the info. If everything is okay, it lets you into the `DashBoard`.

## 8. Simplified explanation
Think of `LogAndReg` as a **Receptionist** at a building. The receptionist has two forms: a "Sign In" form and a "Register" form. You pick the form you want (RadioButtons), fill it out (Fragments), and hand it to the receptionist (Confirm button). The receptionist then checks their records (Database) in the back office (Background Thread). If your name is on the list, they give you a badge (User object) and let you into the main hall (DashBoard).

---
**Bugs/Improvements:**
*   Password security: Passwords are currently stored and passed as plain text. In a real-world app, they should be hashed.
*   The `executorService` is never shut down. It's better to shut it down in `onDestroy` to prevent memory leaks.
