# Class: NotificationChannel

## 1. General information
*   **Class Name:** `NotificationChannel`
*   **Type:** Application Class
*   **Purpose:** This class runs *before* any activity when the app starts. Its job is to create a "communication channel" between the app and the Android notification system. Since Android 8.0, all notifications must belong to a channel so users can control them.
*   **Interactions:** Configures the system `NotificationManager`. Its `CHANNEL_1_ID` is used by `ReceiverAfterTime`.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `CHANNEL_1_ID` | `String` | Unique ID for the reminder channel. | Used by `NotificationCompat.Builder`. |

## 3. Classroom Methods

### Method name: `onCreate`
*   **Type:** `public`
*   **Logic:**
    1. Calls `super.onCreate()`.
    2. Calls `createNotificationChannels()` to register the channel with the phone.
*   **When called:** Automatically by Android once, as soon as the app process starts.

### Method name: `createNotificationChannels`
*   **Type:** `private`
*   **Logic:**
    1. Defines a channel with high importance (meaning it can pop up and make sound).
    2. Sets a description (visible in phone settings).
    3. Hands the channel object to the system's `NotificationManager`.
*   **When called:** Inside `onCreate`.

## 4. Lifecycle
*   **`onCreate()`**: The global entry point for the entire application logic.

## 5. Interaction with other components
*   **NotificationManager:** The system service that registers channels.
*   **AndroidManifest.xml:** This class must be declared in the `<application android:name=".NotificationChannel" ...>` tag to work.

## 6. General logic of the class
`NotificationChannel` is the **Registry**. It goes to the government office (the Android system) and registers the app's phone line. Once the line is registered, the app is allowed to send "calls" (notifications) to the user.

## 7. Simplified explanation
Think of this class as **Setting up the Mailbox**. Before you can receive any letters (notifications), you first need to go to the post office and register a mailbox address. This class does exactly that as soon as you "move in" (start the app).
