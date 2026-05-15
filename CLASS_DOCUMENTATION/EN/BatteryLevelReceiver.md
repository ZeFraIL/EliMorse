# Class: BatteryLevelReceiver

## 1. General information
*   **Class Name:** `BatteryLevelReceiver`
*   **Type:** BroadcastReceiver
*   **Purpose:** This class listens for system signals about the battery level. Its job is to warn the user if their battery is getting low (below 20%) using a custom toast, and provide voice warnings if it drops below 10%.
*   **Interactions:** Registered with the system to receive `BATTERY_CHANGED` broadcasts. It uses `My_Toast` for visual alerts and `TTS_Service` for voice alerts.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `MAX_REPEATS` | `int` | Limit for voice alerts (10). | Prevents annoying the user forever. |
| `level` | `int` | Current percentage of battery. | Read from the system intent. |
| `repeatCount` | `int` | Tracks how many voice warnings were sent. | Checked in `startVoiceNotifications`. |

## 3. Classroom Methods

### Method name: `onReceive`
*   **Type:** `public`
*   **Logic:**
    1. Extracts the "level" integer from the intent.
    2. If level < 20, shows a custom toast.
    3. If level < 10, shows a high-priority alert dialog and calls `startVoiceNotifications()`.
*   **When called:** Automatically by Android whenever the battery percentage changes.

### Method name: `startVoiceNotifications`
*   **Type:** `private`
*   **Logic:**
    1. Uses a `CountDownTimer` to repeat the warning every 60 seconds.
    2. In each tick, it checks if the phone is now charged (level > 20). If so, it cancels the timer.
    3. Otherwise, it sends an Intent to `TTS_Service` to say "Low battery level".
*   **When called:** When the battery falls below 10%.

## 4. Lifecycle
*   **N/A:** A BroadcastReceiver is a short-lived component. It only "lives" for the duration of the `onReceive` method. However, the `CountDownTimer` here might outlive the receiver (which is a slightly unusual practice).

## 5. Interaction with other components
*   **TTS_Service:** To speak the warning.
*   **My_Toast:** To show the visual warning.
*   **PowerConnectionReceiver:** Calls a static method in that class to show a dialog.

## 6. General logic of the class
`BatteryLevelReceiver` is a **Safety Lookout**. It watches the battery meter like a hawk. As soon as the needle hits the "red zone", it starts shouting (both visually and audibly) to make sure the user doesn't lose their exercise progress due to the phone dying.

## 7. Simplified explanation
Think of this class as a **Warning Siren**. When your phone is about to "faint" from hunger (low power), the siren goes off. It keeps beeping every minute until you give the phone some "food" (plug in the charger).
