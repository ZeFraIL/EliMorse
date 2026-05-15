# Class: AboutMe

## 1. General information
*   **Class Name:** `AboutMe`
*   **Type:** Activity
*   **Purpose:** This screen provides information about the app's developer. It features interactive buttons to contact the developer via SMS, Phone Call, or Email.
*   **Interactions:** Inherits from `BaseActivity`. It uses system intents to launch communication apps and monitors the device's connection status to enable or disable contact buttons.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `bSMS`, `bEmail`, `bCall` | `Button` | Contact action buttons. | Initialized in `onCreate` and updated in `updateButtonStates`. |
| `connectivityReceiver` | `BroadcastReceiver` | Listens for internet changes. | Registered in `onResume` to update UI dynamically. |

## 3. Classroom Methods

### Method name: `onCreate`
*   **Type:** `protected`
*   **Logic:**
    1. Sets the layout.
    2. Initializes the user data.
    3. Links buttons and sets click listeners.
    4. Adds **Haptic Feedback** (vibration) to button clicks.
    5. Initializes the `connectivityReceiver`.
*   **When called:** When the user opens the "About Me" screen.

### Method name: `updateButtonStates`
*   **Type:** `private`
*   **Logic:**
    1. Checks if the internet is available to enable the Email button.
    2. Checks if the device can make phone calls/send SMS to enable the Call and SMS buttons.
*   **When called:** During `onResume` and whenever the network status changes.

### Method name: `sendSms`, `makeCall`, `sendEmail`
*   **Type:** `private`
*   **Logic:** These methods create "Implicit Intents". For example, `sendEmail` creates an intent with the developer's address and subject, then asks the system to find an app (like Gmail) that can handle it.
*   **When called:** When the respective buttons are clicked.

### Method name: `onReceive` (inside `ConnectivityReceiver`)
*   **Type:** `public`
*   **Logic:** When the phone's internet connection toggles (on or off), this method is triggered by the system, which then calls `updateButtonStates()` to refresh the UI.

## 4. Lifecycle
*   **`onCreate()`**: Sets up the UI and basic logic.
*   **`onResume()`**: Registers the `connectivityReceiver`. This is done here to ensure the app listens for changes only while the screen is visible.
*   **`onPause()`**: Unregisters the receiver. This is crucial for battery saving and preventing memory leaks.

## 5. Interface Interaction (UI)
*   **Contact Buttons:** Use standard Android iconography and text.
*   **Haptic Feedback:** Provides a physical "click" sensation using the phone's vibration motor.

## 6. Interaction with other components
*   **ConnectivityManager:** Used to check if the user is online.
*   **TelephonyManager:** Used to check if the device has a working SIM card.
*   **External Apps:** Uses `Intent.ACTION_SENDTO` and `Intent.ACTION_DIAL` to talk to external messaging and phone apps.

## 7. General logic of the class
`AboutMe` is a **Dynamic Contact Card**. It doesn't just show buttons; it "smartly" checks if those buttons will actually work. If you are on a tablet with no SIM card, the "Call" button will be disabled. If you are offline, the "Email" button will be disabled.

## 8. Simplified explanation
Think of `AboutMe` as a **Smart Business Card**. If you meet the developer in person, the card "knows" if you have a pen or a phone. If you don't have a phone, the "Call" button on the card disappears or becomes unclickable. It's a way to ensure the user doesn't try to do something that the phone isn't capable of at that moment.

---
**Bugs/Improvements:**
*   **Connectivity Action:** `CONNECTIVITY_ACTION` is deprecated in newer Android versions. It's recommended to use `ConnectivityManager.NetworkCallback` for a more modern and efficient approach.
*   **Hardcoded strings:** While most strings come from `R.string`, ensure that the developer's email and phone are also defined in `strings.xml` for easier localization.
