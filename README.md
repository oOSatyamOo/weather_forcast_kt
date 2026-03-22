# Weather Forecast App

A modern, clean Android weather forecast application built with **Jetpack Compose**, following **Clean Architecture** and **SOLID principles**. The app displays a 3-day weather forecast for any city with full offline support.

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

## 📱 Screenshots

*(Add your screenshots here once you have them)*

## 🚀 How to Run the Project

### Prerequisites
- Android Studio Meerkat | Ladybug | or newer
- Minimum SDK: 24 (Android 7.0)
- An API key from [OpenWeatherMap](https://openweathermap.org/api)

### Setup Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/WeatherForecastApp.git
   cd WeatherForecastApp
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

