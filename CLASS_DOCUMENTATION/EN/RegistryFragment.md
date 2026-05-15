# Class: RegistryFragment

## 1. General information
*   **Class Name:** `RegistryFragment`
*   **Type:** Fragment
*   **Purpose:** This class handles the UI for new user registration. It provides a form with four fields: Name, Password, Email, and Phone.
*   **Interactions:** It is hosted by the `LogAndReg` activity. The activity retrieves the registration data using the fragment's public methods.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `etName` | `EditText` | Name input. | Initialized in `onCreateView`, read in `getName`. |
| `etPassword` | `EditText` | Password input. | Initialized in `onCreateView`, read in `getPassword`. |
| `etMail` | `EditText` | Email input. | Initialized in `onCreateView`, read in `getMail`. |
| `etPhone` | `EditText` | Phone input. | Initialized in `onCreateView`, read in `getPhone`. |

## 3. Classroom Methods

### Method name: `onCreateView`
*   **Type:** `public`
*   **Return value:** `View`
*   **Logic:**
    1. Loads the `fragment_registry.xml` layout.
    2. Hooks up the four `EditText` fields to the Java variables.
    3. Returns the completed view.
*   **When called:** When the fragment is created.

### Method name: `getName` / `getPassword` / `getMail` / `getPhone`
*   **Type:** `public`
*   **Return value:** `String`
*   **Logic:** Returns the text currently typed in the respective field.
*   **When called:** By `LogAndReg` when the user clicks "Confirm" while in Register mode.

## 4. Lifecycle
*   **`onCreateView()`**: Standard fragment initialization.

## 5. Interface Interaction (UI)
*   **EditTexts:** Four fields for comprehensive user profiling.

## 6. Interaction with other components
*   **LogAndReg (Activity):** The controller that orchestrates the switching and data usage.

## 7. General logic of the class
`RegistryFragment` is a **Detailed Form**. It's slightly more complex than the login fragment because it asks for more information (email and phone). Like a real-world form, it just sits there holding the information until someone (the activity) comes to collect it.

## 8. Simplified explanation
Think of `RegistryFragment` as a **Membership Application**. If you are a new guest, you fill out this longer form with your contact details. The "Receptionist" (Activity) then takes this application and files it away.

---
**Bugs/Improvements:**
*   **Validation:** The fragment doesn't check if the email format is correct (e.g., contains @) or if the phone number contains only digits. This validation is currently partially handled in the Activity, but some basic UI-level checks here would be good.
