# Class: User

## 1. General information
*   **Class Name:** `User`
*   **Type:** Normal Class (POJO - Plain Old Java Object)
*   **Purpose:** This class is a "data container" that represents a person using the application. It holds all the relevant information about a user, such as their name, password, and contact info.
*   **Interactions:** It is used by almost every part of the app. It's stored in the database, passed between activities via Intents, and used to personalize the experience.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `id` | `int` | Unique ID from the database. | Used to distinguish users in the DB. |
| `userName` | `String` | The user's name. | Displayed on the History and Dashboard screens. |
| `userPassword`| `String` | The secret password. | Used for authentication. |
| `userPhone` | `String` | Contact phone number. | Part of the user profile. |
| `userMail` | `String` | Contact email address. | Part of the user profile. |

## 3. Classroom Methods

### Method name: `User` (Constructor)
*   **Type:** `public`
*   **Logic:** There are two versions. One takes all fields including ID (used when reading from the DB). The other takes everything *except* ID (used when creating a new user who isn't saved yet).
*   **When called:** When a user logs in, registers, or is loaded from history.

### Method name: `get...` / `set...` (Getters & Setters)
*   **Type:** `public`
*   **Logic:** Standard methods to read or update the private variables.
*   **When called:** Whenever an activity needs to show the user's name or update their phone number.

### Method name: `toString`
*   **Type:** `public`
*   **Return value:** `String`
*   **Logic:** Creates a readable summary of the user object for debugging.
*   **When called:** Usually for logging purposes.

## 4. Lifecycle
*   **N/A:** As this is a normal class, it does not have an Android lifecycle. It exists as long as there is a reference to it in memory.

## 5. Interface Interaction (UI)
*   **None directly:** This class has no UI, but its data is frequently displayed in `TextViews` across the app.

## 6. Interaction with other components
*   **Serializable Interface:** This class implements `Serializable`. This is very important in Android as it allows the entire `User` object to be "packed" into an Intent and sent to another screen.
*   **UserRepository:** The repository converts database rows into `User` objects.

## 7. General logic of the class
`User` is a **Digital ID Card**. Every time you move to a new screen, you carry this card with you so the app knows who you are.

## 8. Simplified explanation
Think of the `User` class as a **Member's Folder**. Inside the folder, there are sheets of paper with your name, phone number, and password. When the app needs to know who is practicing, it pulls out this folder and reads the name on the front.

---
**Bugs/Improvements:**
*   The `toString` method currently includes the password. For security reasons, it's better to exclude sensitive data from string representations to avoid accidental logging.
*   The field names are a bit inconsistent (some use `userName`, some `userMail`). Standardizing naming conventions makes the code easier to read.
