# Growfolio Android

Growfolio is a native Android investment application for portfolio tracking, dollar-cost averaging (DCA), and AI-driven insights. This project is the Android port of the original Growfolio iOS app.

## Project Overview

Built with modern Android development practices, this app mirrors the Clean Architecture and feature set of its iOS counterpart.

- **UI Framework:** Jetpack Compose
- **Language:** Kotlin
- **Dependency Injection:** Hilt
- **Networking:** Retrofit + OkHttp
- **Serialization:** Kotlinx Serialization
- **Architecture:** MVVM + Clean Architecture

## Installation

1. **Prerequisites:**
   - Android Studio Iguana (2023.2.1) or newer.
   - JDK 17+.
   - Android SDK 34 (Target).

2. **Clone the repository:**
   ```bash
   git clone https://github.com/rkemish/growfolio-android.git
   ```

3. **Open in Android Studio:**
   - File > Open > Select the `android` directory.
   - Allow Gradle to sync and download dependencies.

## Usage

1. **Build and Run:**
   - Select the `app` configuration.
   - Choose an emulator or physical device (API 26+).
   - Click the **Run** icon in Android Studio.

2. **Command Line:**
   - Build APK: `./gradlew assembleDebug`
   - Run Unit Tests: `./gradlew test`
   - Run Instrumented Tests: `./gradlew connectedAndroidTest`

## Remaining Tasks

The project is currently in its initial phase with the following work remaining (refer to `PLAN.md` for details):

- [ ] **Navigation:** Implement full NavHost and Bottom Navigation.
- [ ] **Authentication:** Implement Login and Token management.
- [ ] **Stocks:** Complete Watchlist and Stock Detail screens with charts.
- [ ] **WebSockets:** Implement real-time price streaming.
- [ ] **Funding:** Port bank linking and transfer flows.
- [ ] **AI Insights:** Integrate AI chat and automated insights.
- [ ] **Settings:** Support theme switching and notifications.
