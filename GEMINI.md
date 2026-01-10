# Growfolio Android

## Project Overview
Growfolio is a native Android investment application designed for portfolio tracking, dollar-cost averaging (DCA), and AI-driven insights. It is an Android port of the Growfolio iOS app, built with modern Android development practices.

**Key Technologies:**
*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose (Material 3)
*   **Architecture:** MVVM + Clean Architecture
*   **Dependency Injection:** Hilt
*   **Networking:** Retrofit + OkHttp (REST + WebSockets)
*   **Serialization:** Kotlinx Serialization
*   **Asynchronous Processing:** Coroutines + Flow
*   **Image Loading:** Coil
*   **Local Storage:** DataStore (Preferences)

## Architecture
The project follows a Clean Architecture approach with a clear separation of concerns:

*   **Presentation Layer (`com.growfolio.app.presentation`):** Contains UI components (Compose screens) and ViewModels. Organized by feature (dashboard, stocks, ai_insights, funding).
*   **Data Layer (`com.growfolio.app.data`):** Handles data retrieval from network or local storage. Includes Repositories and Data Sources.
*   **Domain Layer:** (Implicit or explicit) Contains business logic and use cases.

## Build and Run
This project uses Gradle with Kotlin DSL.

*   **Build Debug APK:**
    ```bash
    export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
    ./gradlew assembleDebug
    ```
*   **Run Unit Tests:**
    ```bash
    ./gradlew test
    ```
*   **Run Instrumented Tests:**
    ```bash
    ./gradlew connectedAndroidTest
    ```
*   **Generate Coverage Report:**
    ```bash
    ./gradlew koverHtmlReportDebug
    ```

## Development Conventions

*   **Navigation:** Type-safe navigation using a `Screen` sealed class and `NavHost`.
*   **Networking:** `OkHttpClient` includes an `AuthInterceptor` for token management and supports WebSockets for real-time data.
*   **Testing:** High unit test coverage (~90% for core logic) is mandated. Use Mockk for mocking, Turbine for Flow testing, and Truth for assertions.
*   **Dependency Management:** Dependencies are managed in `gradle/libs.versions.toml` (Version Catalog).

## Current Status & Roadmap
(Refer to `PLAN.md` for the most up-to-date status)
*   **Implemented:** Navigation, Foundation, Basic Auth (Mocked), Stocks Watchlist, Funding UI, AI Chat Interface.
*   **In Progress/Planned:** Historical Charts, Bank Linking (Plaid), Real Authentication, Notifications.
