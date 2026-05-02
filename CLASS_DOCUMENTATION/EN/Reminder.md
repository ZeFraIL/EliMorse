# 📱 Reminder Activity Documentation

## 1. General Information
*   **Name:** `Reminder`
*   **Type:** Activity
*   **Purpose:** This screen allows users to set reminders for practicing Morse code. Users can choose a specific date and time, or set a timer (e.g., "remind me in 60 seconds").
*   **Interaction:** Uses `AlarmManager` to schedule tasks and `ReceiverAfterTime` (a BroadcastReceiver) to show notifications when the time comes.

## 2. Variables (Class Fields)
| Name | Type | Purpose | Usage |
| :--- | :--- | :--- | :--- |
| `calendar` | `Calendar` | Stores the specific date and time the user chose. | Used to tell the `AlarmManager` exactly when to trigger. |
| `etTimeAfter` | `EditText` | Field for inputting seconds (for a quick timer). | The value is converted to a long and added to current time. |
| `bSetAlarm` | `Button` | The button that confirms the scheduled reminder. | Sets the exact alarm when clicked. |

## 3. Methods
### Method: `choiceDate` & `choiceTime`
*   **What they do:** These methods open the Android **DatePickerDialog** and **TimePickerDialog**. These are standard pop-up windows that let the user pick a date and time easily without typing.

### Method: `bAddTimeAlarm` (Click Listener)
*   **What it does:** 
    1. Reads the number of seconds from the screen.
    2. Calculates the future time (`System.currentTimeMillis() + seconds`).
    3. Creates a `PendingIntent` that points to `ReceiverAfterTime`.
    4. Registers this with the `AlarmManager`.
*   **Important:** This uses `PendingIntent.FLAG_IMMUTABLE` for security as required by modern Android versions.

## 6. Interaction with other components
*   **AlarmManager:** This is a system service. Even if the user closes the app, the `AlarmManager` will remember the schedule and "wake up" the app's code when the time is right.
*   **ReceiverAfterTime:** When the alarm goes off, this class receives the signal and builds a notification that appears in the phone's status bar.

## 7. General Logic
The user sets a time. The app registers this time with the Android system's alarm clock. When that time arrives, the system sends a "ping" (Broadcast) to the app. The app catches this "ping" and shows a notification to the user.

## 8. Simple Explanation
Think of the `Reminder` screen as your **Personal Alarm Clock**. You tell it when you want to study. The app then sets a "sticky note" on the Android system's calendar. When the time comes, Android "pokes" the app, and the app shouts: "Hey! Time to learn Morse code!"
