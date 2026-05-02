# 📱 LogAndReg Activity Documentation

## 1. General Information
*   **Name:** `LogAndReg`
*   **Type:** Activity
*   **Purpose:** This activity is responsible for managing the user's entry into the app. It provides two options: logging in as an existing user or registering as a new one. It manages the switching between login and registration screens (Fragments).
*   **Interaction:** It interacts with `LoginFragment` and `RegistryFragment` to get user input, and uses `UserRepository` to save or check data in the database.

## 2. Variables (Class Fields)
| Name | Type | Purpose | Usage |
| :--- | :--- | :--- | :--- |
| `loginFragment` | `LoginFragment` | The login screen component. | Shown when the user selects "Login". |
| `registryFragment` | `RegistryFragment` | The registration screen component. | Shown when the user selects "Register". |
| `userRepository` | `UserRepository` | Database access tool. | Used to find existing users or register new ones. |
| `executorService` | `ExecutorService` | Background thread manager. | Used to run database tasks without freezing the screen. |
| `mainThreadHandler` | `Handler` | UI thread manager. | Used to update the screen after background tasks finish. |

## 3. Methods
### Method: `showFragment`
*   **Type:** `private`
*   **Returns:** `void`
*   **Parameters:** `fragment` (`Fragment`) - The screen to show.
*   **What it does:** Replaces the current content in the `FrameLayout` with the selected fragment (either login or registry).
*   **When called:** When the activity starts or when the user clicks a RadioButton.

### Method: `handleRegistration`
*   **Type:** `private`
*   **What it does:** Gets data from the `RegistryFragment`, validates it, and then checks in a background thread if the user already exists. If not, it creates a new user in the database.

### Method: `handleLogin`
*   **Type:** `private`
*   **What it does:** Gets the name and password from `LoginFragment`, then searches the database in a background thread. If found, it proceeds to the `DashBoard`.

## 4. Lifecycle
*   **`onCreate()`**: Called when the screen is first created. It sets up the UI elements, initializes the fragments, and shows the login screen by default.

## 7. General Logic
The activity acts as a coordinator. It switches between two "sub-screens" (fragments). When the user clicks "Confirm", it gathers the data from the active sub-screen, checks the database using a background thread, and either allows entry or shows an error.

## 8. Simple Explanation
Imagine a **Reception Desk** at a club. The receptionist asks: "Are you a member or do you want to join?". Depending on your answer, they give you a different form (Fragment) to fill out. When you finish, they check their computer (Database) to see if you are allowed in.
