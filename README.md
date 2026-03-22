# Weather Forecast App

A modern, clean Android weather forecast application built with **Jetpack Compose**, following **MVVM**, **Clean Architecture** and **SOLID principles**. The app displays a 3-day weather forecast for any city with full offline support.

![Kotlin](https://img.shields.io/badge/Kotlin-2.3.20-blue.svg)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-2026.03.00-green.svg)
![Hilt](https://img.shields.io/badge/Dagger%20Hilt-2.59.2-red.svg)

## ✨ Features

- Search weather by city name with **2-second debounce**
- Displays **3-day forecast** (Date, Min/Max Temperature, Condition + Icon)
- **Offline support** – Shows last saved forecast when internet is unavailable
- Clean Architecture with **MVVM + Use Cases**
- Fully testable (Unit Tests + UI Tests ready)
- Material 3 Design with dark mode support
- Proper logging for API calls

## 🛠 Tech Stack

- **Language**: Kotlin 2.3.20
- **UI**: Jetpack Compose + Material 3
- **Architecture**: Clean Architecture (Presentation → Domain → Data)
- **Dependency Injection**: Dagger Hilt
- **Networking**: Retrofit 2 + OkHttp + Gson
- **Local Database**: Room + Type Converters
- **Async Image Loading**: Coil
- **Testing**: JUnit 4, MockK, Turbine, Compose UI Testing, Hilt Testing
- **Build System**: Gradle with Version Catalog (`libs.versions.toml`)

## 🛠 Structure
    Root
    ├── app/
    │   ├── src/
    │   │   ├── androidTest/
    │   │   │   ├── java/
    │   │   │   │   ├── com/
    │   │   │   │   │   ├── github/
    │   │   │   │   │   │   ├── oOSatyamOo/
    │   │   │   │   │   │   │   ├── weatherforcast/
    │   │   │   │   │   │   │   │   ├── ExampleInstrumentedTest.kt
    │   │   ├── main/
    │   │   │   ├── java/
    │   │   │   │   ├── com/
    │   │   │   │   │   ├── github/
    │   │   │   │   │   │   ├── oOSatyamOo/
    │   │   │   │   │   │   │   ├── weatherforcast/
    │   │   │   │   │   │   │   │   ├── data/
    │   │   │   │   │   │   │   │   │   ├── local/
    │   │   │   │   │   │   │   │   │   │   ├── Converters.kt
    │   │   │   │   │   │   │   │   │   │   ├── DailyForcast.kt
    │   │   │   │   │   │   │   │   │   │   ├── ForcastDao.kt
    │   │   │   │   │   │   │   │   │   │   ├── WeatherDatabase.kt
    │   │   │   │   │   │   │   │   │   │   ├── WeatherForcastEntity.kt
    │   │   │   │   │   │   │   │   │   ├── remote/
    │   │   │   │   │   │   │   │   │   │   ├── ForcastResponse.kt
    │   │   │   │   │   │   │   │   ├── di/
    │   │   │   │   │   │   │   │   │   ├── component/
    │   │   │   │   │   │   │   │   │   │   ├── AppComponent.kt
    │   │   │   │   │   │   │   │   │   ├── network/
    │   │   │   │   │   │   │   │   │   │   ├── NetworkModule.kt
    │   │   │   │   │   │   │   │   ├── repo/
    │   │   │   │   │   │   │   │   │   ├── usecase/
    │   │   │   │   │   │   │   │   │   │   ├── GetWeatherForcastUsecase.kt
    │   │   │   │   │   │   │   │   │   ├── WeatherApiService.kt
    │   │   │   │   │   │   │   │   │   ├── WeatherApiServiceImpl.kt
    │   │   │   │   │   │   │   │   │   ├── WeatherRepo.kt
    │   │   │   │   │   │   │   │   ├── ui/
    │   │   │   │   │   │   │   │   │   ├── components/
    │   │   │   │   │   │   │   │   │   │   ├── PrimaryButton.kt
    │   │   │   │   │   │   │   │   │   │   ├── PrimaryTextField.kt
    │   │   │   │   │   │   │   │   │   │   ├── WeatherAppBar.kt
    │   │   │   │   │   │   │   │   │   │   ├── WeatherCard.kt
    │   │   │   │   │   │   │   │   │   ├── screens/
    │   │   │   │   │   │   │   │   │   │   ├── HomeScreen.kt
    │   │   │   │   │   │   │   │   │   ├── theme/
    │   │   │   │   │   │   │   │   │   │   ├── Color.kt
    │   │   │   │   │   │   │   │   │   │   ├── Theme.kt
    │   │   │   │   │   │   │   │   │   │   ├── Type.kt
    │   │   │   │   │   │   │   │   │   ├── viewmodel/
    │   │   │   │   │   │   │   │   │   │   ├── state/
    │   │   │   │   │   │   │   │   │   │   │   └── UIState.kt
    │   │   │   │   │   │   │   │   │   │   ├── WeatherViewModel.kt
    │   │   │   │   │   │   │   │   ├── utils/
    │   │   │   │   │   │   │   │   │   ├── FormatValidations.kt
    │   │   │   │   │   │   │   │   ├── MainActivity.kt
    │   │   │   │   │   │   │   │   ├── WeatherApp.kt
    │   │   │   ├── res/
    │   │   ├── test/
    │   │   │   └── java/
    │   │   │       └── com/
    │   │   │           └── github/
    │   │   │               └── oOSatyamOo/
    │   │   │                   └── weatherforcast/
    │   │   │                       ├── domain/
    │   │   │                       │   ├── usecase/
    │   │   │                       │   │   ├── GetWeatherForecastUseCaseTest.kt
    │   │   │                       └── presentation/
    │   │   │                           └── ui/
    │   │   │                               └── WeatherViewModel.kt
    │   ├── .gitignore
    │   ├── build.gradle.kts
    │   ├── proguard-rules.pro
    ├── gradle/
    │   ├── wrapper/
    │   │   └── gradle-wrapper.properties
    │   ├── gradle-daemon-jvm.properties
    │   └── libs.versions.toml
    ├── .gitattributes
    ├── .gitignore
    ├── build.gradle.kts
    ├── gradle.properties
    ├── gradlew
    ├── gradlew.bat
    ├── README.md
    └── settings.gradle.kts

## 📱 Screenshots
<div align="center">
  <table border="0">
    <tr>
      <td width="48%" align="center" valign="top">
          <img src="https://github.com/user-attachments/assets/aa9827a0-ffb2-4942-9170-090ba33bfdf4" width="100%" />
        </a>
        <br><br>
        <a href="https://github.com/oOSatyamOo/GitHub-Language-Stats">
          <img src="https://github.com/user-attachments/assets/ad22d68e-8a6d-4fa8-b5c6-1df6a77c3275" width="100%" />
        </a>
      </td>
      <td width="48%" align="center" valign="top">
        <a href="https://github.com/oOSatyamOo/GitHub-Language-Stats">
          <img src="https://github.com/user-attachments/assets/ff5af621-6c42-4dd8-a33c-901a4fcd738e" width="100%" />
        </a>
      </td>
    </tr>
  </table>
</div>

## 🚀 How to Run the Project

### Prerequisites
- Android Studio Meerkat | Ladybug | or newer
- Minimum SDK: 24 (Android 7.0)
- An API key from [OpenWeatherMap](https://openweathermap.org/api)
- add API KEY to local.properties
   ```
   OPENWEATHER_BASE_URL=https://api.openweathermap.org/
   OPENWEATHER_API_KEY=your_actual_api_key_here
   ```

### Available Tests Cases
- Unit Tests: UseCase, ViewModel
- UI Tests: Compose UI testing with Hilt

   ```
   ./gradlew test

   ./gradlew connectedAndroidTest
   ```

### 📌 Key Design Decisions

Debounced Search: API call triggers only after user stops typing for 2 seconds
Offline First: Always tries network first, falls back to cached data
SOLID Principles: Clear separation of concerns using Use Cases
Dependency Inversion: ViewModel depends on UseCase, not Repository directly


