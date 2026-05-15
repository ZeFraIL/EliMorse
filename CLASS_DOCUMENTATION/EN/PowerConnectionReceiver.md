# Class: PowerConnectionReceiver

## 1. General information
*   **Class Name:** `PowerConnectionReceiver`
*   **Type:** BroadcastReceiver
*   **Purpose:** This class monitors whether the phone is plugged into a charger or not. Its main job is to stop the battery warnings once the user connects their phone to power.
*   **Interactions:** Listens for power connection signals from Android. It can stop the `BatteryLevelReceiver` to save resources.

## 2. Variables (class fields)
*   **None:** The class logic is method-based.

## 3. Classroom Methods

### Method name: `onReceive`
*   **Type:** `public`
*   **Logic:**
    1. Checks the system intent for the current charging status.
    2. If the phone is now charging or full, it calls `unregisterBatteryReceiver()` to silence any ongoing battery alerts.
*   **When called:** When the user plugs in or unplugs the charger.

### Method name: `showLowBatteryAlert`
*   **Type:** `public static`
*   **Return value:** `void`
*   **Parameters:** `context (Context)`
*   **Logic:** Builds and shows a standard Android `AlertDialog` with the message "charge your phone". This dialog is modal, meaning the user must click "OK" to dismiss it.
*   **When called:** Usually called by the `BatteryLevelReceiver` when the power is extremely low.

## 4. Lifecycle
*   **N/A:** Short-lived component triggered by system broadcasts.

## 5. Interface Interaction (UI)
*   **AlertDialog:** A simple system-styled popup.

## 6. Interaction with other components
*   **BatteryLevelReceiver:** This class manages the lifecycle of the battery monitor by unregistering it when power is restored.

## 7. General logic of the class
`PowerConnectionReceiver` is the **Turn-off Switch**. While the `BatteryLevelReceiver` is the alarm, this class is the person who turns the alarm off once the "emergency" (empty battery) is over because the charger is connected.

## 8. Simplified explanation
Think of this class as a **Sensor** on your charger. When you plug the cable in, the sensor sends a message saying: "The phone is safe now, you can stop the warning siren!".
