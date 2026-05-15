# Class: GuideHow

## 1. General information
*   **Class Name:** `GuideHow`
*   **Type:** Activity
*   **Purpose:** This screen provides a visual tutorial on how to use Morse code or the application. It embeds a YouTube video using a `WebView`.
*   **Interactions:** Inherits from `BaseActivity`.

## 2. Variables (class fields)
| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `videoID` | `String` | The unique ID for the YouTube video. | Used to build the HTML embed code. |
| `webView` | `WebView` | The component that displays web content. | Used to load the video player. |

## 3. Classroom Methods

### Method name: `onCreate`
*   **Type:** `protected`
*   **Logic:**
    1. Sets the layout to `activity_guide_how.xml`.
    2. Initializes variables.
    3. Sets a click listener for the "Back" button to close the screen.
    4. Retrieves the `videoID` from string resources.
    5. Configures the `WebView` to enable JavaScript (required for YouTube).
    6. Creates an HTML string containing an `<iframe>` with the video URL.
    7. Loads the HTML into the `WebView`.
*   **When called:** When the user clicks "How to use" in the Guide menu.

## 4. Lifecycle
*   **`onCreate()`**: Loads the video player.
*   **`onPause()` / `onDestroy()`**: It's important to stop the WebView when leaving. (Note: This is missing in the current code).

## 5. Interface Interaction (UI)
*   **WebView (`webView`):** Acts as a container for the YouTube video player.
*   **Button (`bBack`):** Closes the activity.

## 6. Interaction with other components
*   **WebBrowser/Internet:** The `WebView` requires an internet connection to load the YouTube video.

## 7. General logic of the class
The `GuideHow` activity is a **Video Player**. It uses a tiny "web browser" inside the app to show a specific video from the internet. It wraps the video in HTML so it fits perfectly on the mobile screen.

## 8. Simplified explanation
Think of `GuideHow` as a **Mini TV Screen**. Instead of reading about Morse code, you can watch a video about it. The app opens a small window that connects to YouTube and plays a lesson for you.

---
**Bugs/Improvements:**
*   **Video continues in background:** In the current code, if the user presses the home button, the video might continue playing in the background. It is better to call `webView.onPause()` in the activity's `onPause()` method.
*   **Missing internet check:** If there is no internet, the WebView will show an error. Adding a check for connectivity would improve user experience.
