# EliMorse - Morse Code Learning and Translation App

## Overview

EliMorse is a comprehensive Android application designed for learning, practicing, and utilizing Morse code. The app provides a suite of tools for both beginners and experienced users, combining educational exercises with practical translation utilities.

## Core Features

- **Translation:**
  - **Text to Morse:** Translates standard English text into its Morse code representation.
  - **Morse to Text:** Translates Morse code input back into English text.

- **Signal Transmission:**
  - **Audio Playback:** Plays any Morse code sequence as a series of audible dots and dashes, adhering to standard timing rules.
  - **Flashlight (Torch) Transmission:** Transmits Morse code visually using the device's flashlight, with correct timing for light pulses and pauses.

- **Training and Exercises:**
  - **Encoding Practice:** Users can practice translating letters, numbers, and words into Morse code.
  - **Listening Practice:** A dedicated "listening" mode where the app plays a Morse code signal, and the user must enter the corresponding text.
  - **Mistake Tracking:** The app tracks mistakes during exercises to help users identify areas for improvement.

- **User Management:**
  - **Login & Registration:** A simple user authentication system to manage user data.
  - **Exercise History:** Saves the results of completed exercises to the user's profile.

- **User Interface & Experience:**
  - **Dynamic Theming:** Supports Light, Dark, and System Default themes.
  - **Haptic Feedback:** Provides tactile feedback on button presses for a more responsive feel.
  - **Smart UI:** Buttons for network-dependent actions (call, SMS, email) are automatically disabled if the required service is unavailable.
  - **Resource-Efficient:** Core components like `TextToSpeech` and `BroadcastReceiver` are implemented following best practices to prevent memory leaks and ensure efficient resource management.

- **Guides & Information:**
  - **Morse Code Chart:** An in-app list of all letters and numbers with their Morse code equivalents.
  - **About Section:** Provides contact information and ways to get in touch with the developer.

## Architecture & Technology

- **Language:** Java
- **Platform:** Android
- **UI:** Android XML Layouts with Material Design components.
- **Database:** Local data persistence using `SQLite`.
- **Core Components:** Activities, Services, BroadcastReceivers, and custom UI components.
- **Modularity:** Key functionalities like Morse code playback (`MorsePlayer`), question generation (`QuestionRepository`), and database operations (`HelperDB`) are encapsulated in separate helper classes for better organization and reusability.
