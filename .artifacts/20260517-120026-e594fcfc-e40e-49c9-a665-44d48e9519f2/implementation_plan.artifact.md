# Implementation Plan - NutriMind AI

Build a production-ready Android Calories Count & AI Nutrition Tracking App with Clean Architecture, MVVM, and Gemini AI integration.

## User Review Required

> [!IMPORTANT]
> - **Firebase Setup**: The code will include Firebase integration (Auth, Firestore, Analytics, Crashlytics). You will need to add your own `google-services.json` to the `app/` directory and uncomment the Firebase plugins in `app/build.gradle.kts`.
> - **Gemini API Key**: You will need to provide a Gemini API key in a secure way (e.g., `local.properties` or a secrets file) for the AI features to work.
> - **Android API 36**: The project is currently configured with `compileSdk = 36` to satisfy a dependency requirement (`navigationevent`). This means you'll need the latest Android SDK (Android 16/Baklava) installed in Android Studio.

## Proposed Changes

### 1. Build Configuration & Dependencies
Update gradle files to support Firebase and latest libraries.

#### [app/build.gradle.kts](file:///D:/GitHubRepository/CaleriesTrackerAi/app/build.gradle.kts)
- Uncomment Firebase plugins.
- Ensure all dependencies from `libs.versions.toml` are correctly implemented.

#### [libs.versions.toml](file:///D:/GitHubRepository/CaleriesTrackerAi/gradle/libs.versions.toml)
- (Verify and keep current versions as they seem to work with API 36).

---

### 2. Authentication Layer
Implement Login, Signup, and Forgot Password using Firebase Auth.

#### [NEW] [LoginScreen.kt](file:///D:/GitHubRepository/CaleriesTrackerAi/app/src/main/java/com/nutrimind/ai/presentation/screens/auth/LoginScreen.kt)
#### [NEW] [SignupScreen.kt](file:///D:/GitHubRepository/CaleriesTrackerAi/app/src/main/java/com/nutrimind/ai/presentation/screens/auth/SignupScreen.kt)
#### [NEW] [ForgotPasswordScreen.kt](file:///D:/GitHubRepository/CaleriesTrackerAi/app/src/main/java/com/nutrimind/ai/presentation/screens/auth/ForgotPasswordScreen.kt)
#### [AuthRepositoryImpl.kt](file:///D:/GitHubRepository/CaleriesTrackerAi/app/src/main/java/com/nutrimind/ai/data/repository/AuthRepositoryImpl.kt)
- Implement Firebase Auth calls.

---

### 3. User Profile & Health Setup
Collect user data and use AI to calculate goals.

#### [NEW] [ProfileSetupScreen.kt](file:///D:/GitHubRepository/CaleriesTrackerAi/app/src/main/java/com/nutrimind/ai/presentation/screens/profile/ProfileSetupScreen.kt)
- Multi-step form for Age, Height, Weight, Activity Level, etc.

#### [AiRepositoryImpl.kt](file:///D:/GitHubRepository/CaleriesTrackerAi/app/src/main/java/com/nutrimind/ai/data/repository/AiRepositoryImpl.kt)
- Implement Gemini AI prompts for health calculation.

---

### 4. AI Nutrition Assistant
Gemini-powered chat for nutrition advice.

#### [ChatScreen.kt](file:///D:/GitHubRepository/CaleriesTrackerAi/app/src/main/java/com/nutrimind/ai/presentation/screens/ChatScreen.kt)
- Improve UI with chat bubbles and AI typing indicators.

#### [ChatViewModel.kt](file:///D:/GitHubRepository/CaleriesTrackerAi/app/src/main/java/com/nutrimind/ai/presentation/viewmodel/ChatViewModel.kt)
- Integrate with `AiRepository`.

---

### 5. Smart Product Scanner
CameraX + ML Kit + OpenFoodFacts + AI Analysis.

#### [ScannerScreen.kt](file:///D:/GitHubRepository/CaleriesTrackerAi/app/src/main/java/com/nutrimind/ai/presentation/screens/ScannerScreen.kt)
- Add overlay for barcode/label scanning.
- Navigate to Result screen after scan.

#### [NEW] [ScanResultScreen.kt](file:///D:/GitHubRepository/CaleriesTrackerAi/app/src/main/java/com/nutrimind/ai/presentation/screens/scanner/ScanResultScreen.kt)
- Show Health Score (GOOD/BAD/WORST).
- Show AI explanation of ingredients.

---

### 6. Home Dashboard & Analytics
Modern dashboard with charts and progress rings.

#### [HomeScreen.kt](file:///D:/GitHubRepository/CaleriesTrackerAi/app/src/main/java/com/nutrimind/ai/presentation/screens/HomeScreen.kt)
- Add Vico Weekly Calories Graph.
- Add "AI Suggestions" card.

#### [NEW] [AnalyticsScreen.kt](file:///D:/GitHubRepository/CaleriesTrackerAi/app/src/main/java/com/nutrimind/ai/presentation/screens/analytics/AnalyticsScreen.kt)
- Detailed charts for Macros, Calories, and Weight progress.

---

### 7. Data Layer & WorkManager
Offline-first storage and background notifications.

#### [AppDatabase.kt](file:///D:/GitHubRepository/CaleriesTrackerAi/app/src/main/java/com/nutrimind/ai/data/local/AppDatabase.kt)
- Ensure entities for Calories, Water, and UserProfile are present.

#### [WaterReminderWorker.kt](file:///D:/GitHubRepository/CaleriesTrackerAi/app/src/main/java/com/nutrimind/ai/worker/WaterReminderWorker.kt)
- Setup periodic work for hydration reminders.

---

## Verification Plan

### Automated Tests
- Run unit tests for Repositories and UseCases:
  `./gradlew test`
- Run UI tests for Navigation:
  `./gradlew connectedAndroidTest`

### Manual Verification
1.  **Auth Flow**: Verify Login -> Signup -> Forgot Password works (with Firebase console).
2.  **Onboarding**: Ensure smooth transitions between cards.
3.  **AI Calculation**: Verify that the calculated calories make sense based on input profile.
4.  **Scanner**: Test with a real barcode (e.g., a snack) and verify AI analysis.
5.  **Dashboard**: Check if the Vico graph updates correctly after adding food.
6.  **Notifications**: Trigger a test notification for water reminder.
