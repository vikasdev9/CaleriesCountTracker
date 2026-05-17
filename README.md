# NutriMind AI - Calories Count & AI Nutrition Tracking

NutriMind AI is a production-ready Android application designed for intelligent nutrition tracking and personalized health insights. Built with modern Android technologies and Clean Architecture, it integrates Gemini AI to provide a premium user experience.

## 📱 Screenshots

| Home Dashboard | Scanner | AI Analysis |
| :---: | :---: | :---: |
| ![Home](screenshots/home.png) | ![Scanner](screenshots/scanner.png) | ![Analysis](screenshots/analysis.png) |

| Chat with AI | Progress Charts | Health Goals |
| :---: | :---: | :---: |
| ![Chat](screenshots/chat.png) | ![Charts](screenshots/charts.png) | ![Goals](screenshots/goals.png) |

*(Note: Please place your screenshot images in the `/screenshots` directory with the names matching above to display them here.)*

## ✨ Features

- **AI-Powered Nutrition Tracking**: Analyze food and ingredients instantly using Gemini AI.
- **Smart Product Scanner**: Scan barcodes or nutrition labels (OCR) for immediate health ratings (GOOD, BAD, WORST).
- **Personalized Health Goals**: AI-calculated daily calorie, protein, carb, and fat targets based on your profile.
- **Home Dashboard**: Modern UI with progress rings, weekly analytics charts, and AI-generated suggestions.
- **Smart Reminders**: Automated water intake reminders via WorkManager and notifications.
- **Voice Food Logging**: Quickly add meals using voice input.
- **Secure Authentication**: Firebase Auth integration (Login, Signup, Forgot Password).
- **Offline First**: Robust local storage with Room and DataStore.

## 🛠 Tech Stack

- **UI**: Jetpack Compose, Material 3, Vico Charts.
- **Architecture**: MVVM + Clean Architecture + SOLID Principles.
- **DI**: Hilt.
- **Database**: Room, DataStore.
- **Networking**: Retrofit, OkHttp.
- **AI**: Google Gemini AI API.
- **Image/Camera**: CameraX, ML Kit (Barcode Scanning & Text Recognition).
- **Backend**: Firebase (Auth, Firestore, Analytics, Crashlytics).
- **Background Tasks**: WorkManager.

## 🚀 Getting Started

### 1. Prerequisites
- Android Studio Ladybug or later.
- A Gemini API Key from [Google AI Studio](https://aistudio.google.com/).
- A Firebase Project with `google-services.json`.

### 2. Setup
1. Clone the repository.
2. Add your `google-services.json` to the `app/` directory.
3. Open `local.properties` in the project root and add your **Gemini API Key**:
   ```properties
   gemini.api.key=YOUR_ACTUAL_API_KEY_HERE
   ```
4. Sync Gradle and run the app.

## 🏛 Project Structure

```
com.nutrimind.ai
│
├── data            # Local, Remote, and Repository Implementations
├── domain          # Models, Repository Interfaces, and UseCases
├── presentation    # UI Screens, ViewModels, and Components
├── di              # Hilt Modules
├── utils           # Helper classes (Barcode/Text Analyzers, DateUtils)
├── worker          # Background WorkManager workers
```

## 🧪 Testing
Run unit tests using:
`./gradlew test`

## 📝 License
This project is for demonstration purposes. Ensure you comply with API terms of use for Gemini and OpenFoodFacts.
