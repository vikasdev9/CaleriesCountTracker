# Walkthrough - Premium Onboarding UI/UX

This document summarizes the premium redesign of the NutriMind AI onboarding flow.

## Design Architecture

### 1. Visual Language
- **Color Palette**: [Color.kt](file:///D:/GitHubRepository/CaleriesTrackerAi/app/src/main/java/com/nutrimind/ai/ui/theme/Color.kt)
    - `PremiumGreen` (#2D6A4F): A deep, professional forest green for primary actions.
    - `SoftGreen` (#D8F3DC): For subtle backgrounds and containers.
    - `GradientStart` & `GradientEnd`: A very soft vertical gradient from off-white to a hint of mint.
- **Typography**: Uses high-contrast weights. Titles use `Black` weight for a "premium magazine" look, while body text uses a legible `Gray` color.

### 2. Interaction Model: [OnboardingScreen.kt](file:///D:/GitHubRepository/CaleriesTrackerAi/app/src/main/java/com/nutrimind/ai/presentation/screens/OnboardingScreen.kt)
- **HorizontalPager**: Provides a standard "swipeable" interface.
- **Expanding Indicators**: The dots at the bottom expand into "pills" as you scroll, giving clear visual progress feedback.
- **Dynamic Quotes**: A unique "living" component that rotates health quotes every few seconds with a vertical slide-and-fade animation.

## Technical Highlights

- **Animations**:
    - `AnimatedContent`: Handles the seamless transition between motivational quotes.
    - `animateDpAsState`: Animates the width of the page indicators.
- **Responsiveness**: The use of a weighted Pager ensures that on taller phones, the white space expands elegantly, while on shorter phones, the content remains accessible.
- **Navigation Integration**: Seamlessly connects to the [NavGraph.kt](file:///D:/GitHubRepository/CaleriesTrackerAi/app/src/main/java/com/nutrimind/ai/presentation/navigation/NavGraph.kt) with backstack clearing logic.

## Resource Recommendations

- **Illustrations**: For the final product, I recommend using resources from:
    - [unDraw](https://undraw.co/): Clean, customizable SVG illustrations.
    - [Humaaans](https://www.humaaans.com/): Mix-and-match illustrations of people.
    - [LottieFiles](https://lottiefiles.com/): For high-quality vector animations if moving beyond static icons.

## Next Steps
- **Profile Setup**: After "Get Started", users will navigate to the multi-step profile questionnaire to calculate their AI-powered health goals.
- **Image Assets**: Replace the current `Icon` placeholders with the recommended SVG/Lottie resources to complete the high-end look.
