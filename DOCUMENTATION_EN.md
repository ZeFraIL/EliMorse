# 📱 EliMorse - Android Project

## 📌 Description
EliMorse is an educational Android application designed to help users learn and practice Morse code. 
### Key Functions:
- **Translation:** Real-time conversion between text and Morse code.
- **Exercises:** Interactive games to practice numbers, letters, and words.
- **Visual & Audio signals:** Flashlight (torch) and sound (SoundPool) Morse playback.
- **User System:** Registration and login for progress tracking.
- **History:** Viewing previous exercise results from a local database.
- **Reminders:** Scheduled notifications to encourage regular practice.
- **Accessibility:** Integrated Text-to-Speech (TTS) for navigation.

## 🏗️ Architecture
The project follows the **MVC (Model-View-Controller)** pattern:
- **Model:** Data classes (`User`, `Question`) and Data Access (`UserRepository`, `HelperDB`).
- **View:** XML Layout files.
- **Controller:** Activities (`Translate`, `ExerciseGame`, `LogAndReg`) and Fragments.

## 📂 Project Structure
- `java/elia/shapira/elimorse/`: Main logic classes.
- `res/layout/`: UI design files.
- `res/values/strings.xml`: Centralized text resources.
- `res/raw/`: Audio files for Morse dots and dashes.
- `res/menu/`: Popup menu definitions.

## 🧩 Class Descriptions

### 🔹 MorseTranslator
**Purpose:** Core logic for bidirectional translation.
**Methods:**
- `toMorse(String text)`: Returns Morse string.
- `toText(String morse)`: Returns decoded text (uses '?' for invalid sequences).

### 🔹 UserRepository & HelperDB
**Purpose:** Local storage management using SQLite.
**Methods:**
- `registerUser(User user)`: Saves new user.
- `findUser(name, password)`: Authenticates user.

### 🔹 TTS_Service
**Purpose:** Background voice announcements.
**Methods:**
- `speak(String text)`: Uses `QUEUE_FLUSH` to announce UI transitions.

### 🔹 ExerciseGame
**Purpose:** Main training loop.
**Variables:** `failedAttempts` (int), `questions` (List<Question>).
**Methods:** `checkAnswer()`, `saveResults()`.

---

## 🔄 Data Flow
1. User interacts with UI (View).
2. Activity (Controller) processes input or requests data from `UserRepository`.
3. Results are saved to `HelperDB` (Model).
4. UI updates based on data retrieved from the database.

## ⚠️ Error Handling
- **Invalid Morse:** Replaced with `?`.
- **Empty Fields:** Validated in `LogAndReg` with Toast notifications.
- **Parsing Errors:** Try-catch blocks in `Reminder` for numeric inputs.
- **Hardware:** Camera availability checks for flashlight.

## 🚀 Possible Improvements
- Implementation of **Room Database** for more robust data handling.
- **MVVM Architecture** with LiveData for better lifecycle management.
- Addition of a **Morse Dictionary** with visual animations.
