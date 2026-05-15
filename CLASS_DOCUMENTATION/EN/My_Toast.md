# Class: My_Toast

## 1. General information
*   **Class Name:** `My_Toast`
*   **Type:** Normal Class (Utility)
*   **Purpose:** This class is used to show custom, styled "Toast" messages (the little popups at the bottom of the screen). Instead of the plain Android text, it creates a toast with an icon and centered text.
*   **Interactions:** Can be called from any Activity in the application.

## 2. Variables (class fields)
*   **None:** All methods are static.

## 3. Classroom Methods

### Method name: `showToast`
*   **Type:** `public static`
*   **Return value:** `void`
*   **Parameters:**
    | Name | Type | Description |
    | :--- | :--- | :--- |
    | `context` | `Context` | Where the toast is shown. |
    | `message` | `String` | The text to display. |
    | `iconResId`| `int` | (Optional) The image icon to show. |
*   **Logic:**
    1. Uses a `LayoutInflater` to load the custom layout `my_toast.xml`.
    2. Finds the `ImageView` and `TextView` in that layout.
    3. Sets the message and the icon (if provided).
    4. Creates a new `Toast` object, centers it on the screen, and shows it.
*   **When called:** Whenever the app needs to give the user quick feedback (e.g., "User not found" or "Battery low").

## 4. Lifecycle
*   **N/A:** Simple static helper.

## 5. Interface Interaction (UI)
*   **Layout (`my_toast.xml`):** Defines the rounded background, icon position, and font style for the messages.

## 6. Interaction with other components
*   **LayoutInflater:** Used to convert XML design into a Java object at runtime.

## 7. General logic of the class
`My_Toast` is a **Sign Maker**. Instead of using the generic signs provided by the city (Android), the app uses this class to make signs that match the Eli Morse "brand".

## 8. Simplified explanation
Think of `My_Toast` as a **Custom Post-it Note**. Normal Android toasts are like plain white stickers. `My_Toast` lets you pick a colored sticker with a little drawing (the icon) on it to make the message look more friendly.
