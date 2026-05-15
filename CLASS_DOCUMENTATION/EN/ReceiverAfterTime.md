# Class: ReceiverAfterTime

## 1. General information
*   **Class Name:** `ReceiverAfterTime`
*   **Type:** BroadcastReceiver
*   **Purpose:** This class is the "Alarm Clock" listener. When the time set in the `Reminder` screen arrives, the Android system sends a signal. This class catches that signal and pops up a notification on the user's screen.
*   **Interactions:** Triggered by `AlarmManager`. It uses `NotificationManager` to show the alert and opens the `Welcome` activity if the user clicks the notification.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `nmc` | `NotificationManagerCompat`| Utility for notifications. | (Defined but unused in current logic). |

## 3. Classroom Methods

### Method name: `onReceive`
*   **Type:** `public`
*   **Logic:**
    1. Shows a small "Ok" toast for debugging.
    2. Calls `build_and_view_Notification()` to show the actual alert.
*   **When called:** Automatically by Android when the reminder alarm goes off.

### Method name: `build_and_view_Notification`
*   **Type:** `private`
*   **Logic:**
    1. Creates an `Intent` that points to the `Welcome` screen.
    2. Wraps it in a `PendingIntent` (so the system can open the app even if it's closed).
    3. Builds the notification with a title ("Important!"), a message, an icon, and a color.
    4. Checks for notification permissions.
    5. Sends the notification to the `NotificationManager` to display it.
*   **When called:** Inside `onReceive`.

## 4. Lifecycle
*   **N/A:** Runs for a fraction of a second to show the notification, then stops.

## 5. Interface Interaction (UI)
*   **System Notification:** This is the only "UI" this class generates.

## 6. Interaction with other components
*   **NotificationChannel:** Uses `CHANNEL_1_ID` to know where to send the alert.
*   **Welcome Activity:** The destination when the notification is tapped.

## 7. General logic of the class
`ReceiverAfterTime` is the **Messenger**. It sits quietly until the alarm rings. Then it jumps up, writes a "practice your Morse code!" message, and pins it to the top of the user's screen.

## 8. Simplified explanation
Think of this class as a **Secret Agent**. The Agent is waiting in a car (the background). At exactly 4 PM, he gets a radio signal. He immediately runs to your front door and leaves a note (the notification) saying "Time to practice!".
