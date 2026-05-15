# Class: Reminder

## 1. General information
*   **Class Name:** `Reminder`
*   **Type:** Activity
*   **Purpose:** This screen allows users to set reminders to practice Morse code. Users can schedule a notification for a specific date and time or set a timer for a specific number of seconds.
*   **Interactions:** It interacts with the Android `AlarmManager` to schedule events and uses `ReceiverAfterTime` to handle the alarm when it goes off.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `calendar` | `Calendar` | Holds the target date and time for the alarm. | Set via pickers and used by `AlarmManager`. |
| `notiYear`, etc. | `int` | Individual components of the target time. | Stored from pickers. |
| `cyear`, etc. | `int` | Components of the *current* time. | Used to initialize the pickers. |
| `stWhere` | `String` | Formatted string of the chosen time. | Displayed in `tvWhere`. |
| `etTimeAfter` | `EditText` | Input for the "X seconds" timer. | Read when `bAddTimeAlarm` is clicked. |

## 3. Classroom Methods

### Method name: `onCreate`
*   **Type:** `protected`
*   **Logic:**
    1. Requests permission to post notifications (Android 13+).
    2. Initializes UI.
    3. **bAddTimeAlarm listener:** Reads seconds from `etTimeAfter`, calculates future time, and sets a one-time alarm.
    4. **bWhere listener:** Opens the date picker.
    5. **bSetAlarm listener:** Schedules an exact alarm based on the chosen `calendar` time.
*   **When called:** When the user clicks "Reminder" on the DashBoard.

### Method name: `choiceDate`
*   **Type:** `private`
*   **Logic:** Shows a `DatePickerDialog`. Once the user picks a date, it updates the `calendar` object and automatically calls `choiceTime()`.
*   **When called:** When the user clicks the "Choose Date/Time" button.

### Method name: `choiceTime`
*   **Type:** `private`
*   **Logic:** Shows a `TimePickerDialog`. Updates the `calendar` with the chosen hour and minute, and displays the full selection in `tvWhere`.
*   **When called:** Automatically after `choiceDate()`.

### Method name: `initElements`
*   **Type:** `private`
*   **Logic:** Links UI elements and initializes the context.
*   **When called:** During `onCreate`.

## 4. Lifecycle
*   **`onCreate()`**: Sets up the scheduling interface.

## 5. Interface Interaction (UI)
*   **DatePickerDialog / TimePickerDialog:** Standard Android popups for choosing dates and times.
*   **Button (`bSetAlarm`):** Finalizes the scheduling.
*   **EditText (`etTimeAfter`):** Quick-set timer input.

## 6. Interaction with other components
*   **AlarmManager:** A system service that can wake up the app (or a receiver) at a specific time, even if the app is closed.
*   **PendingIntent:** A token handed to the system, allowing it to start `ReceiverAfterTime` on our behalf.
*   **ReceiverAfterTime:** The class that actually builds and shows the notification.

## 7. General logic of the class
`Reminder` is a **Scheduler**. It collects a future timestamp from the user and "files" it with the Android System. The system then waits until that exact moment and sends a signal (Broadcast) back to the app's receiver.

## 8. Simplified explanation
Think of `Reminder` as a **Digital Alarm Clock**. You tell it "wake me up at 4 PM tomorrow" or "set a timer for 10 minutes". The app then hands this request to the phone's "Secretary" (AlarmManager). The secretary makes sure to tap you on the shoulder (Notification) when the time comes.

---
**Bugs/Improvements:**
*   **Variable naming:** `UserPassword` is assigned from the Intent but never used. It also shouldn't be passed around unnecessarily.
*   **Calendar initialization:** `calendar` should be initialized at the start of `onCreate` to avoid potential `NullPointerException` if the user clicks "Set Alarm" before picking a date.
*   **Notification permission:** The code requests permission but doesn't check if it's actually granted before proceeding.
