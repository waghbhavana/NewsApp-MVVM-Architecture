# News App
This NewsApp project is developed with MVVM architecture and Jetpack Compose.
It provides live top and breaking headlines for a country. Allow you to browse by specific category such as by country, by news source, by languages. You can also search with keywords. 

## Design Architecture used
<p align="center">
<img alt="mvvm-architecture"  src="https://github.com/waghbhavana/NewsApp-MVVM-Architecture/blob/main/assets/mvvm%20diagram.png">
</p>

## Features
- Browse top headlines from news sources.
- Search for specific news articles with keyword.
- Offline First architecture with a single source of truth.
- Browse news articles by specific categories like country, new source, language and country.
- Article detailed screen displaying full article content.


## Libraries and Frameworks Used
- **Kotlin** as it provides modern features, concise syntax, null safety.
- **Dagger- Hilt**  for dependency injection.
- **Retrofit and OkHttp** for making network requests to RESTful APIs.
- **Jetpack Compose** as it is modern UI toolkit for declarative UI.
- **MVVM Architecture** is Design pattern to separate program logic and user interface controls.
- **Flow API** to handles streams of data asynchronously and applies transformations.
- **StateFlow** used for propagating state changes.
- **Coroutines**  for concurrent tasks.
- **Pagination** to efficiently load data in chunks
- **WorkManager** for fetching news periodically
- **Instant Search** it's a real-time search feature providing immediate feedback to users
- **Unit and UI tests** to verify the app behave and ensure that they meet the expected functionality.


## Dependency Used
- Retrofit for Networking: A type-safe HTTP client for smooth network requests
- Dagger Hilt for Dependency Injection: Simplifies dependency injection
- Room for Database: A SQLite object mapping library for local data storage
- Jetpack Compose for UI: Modern UI toolkit for building native Android UIs
- Paging Compose for Pagination: Simplifies the implementation of paginated lists
- Mockito, JUnit, Turbine for Testing: Ensures the reliability of the application

## Screenshots from the app
<p align="center">
<img alt="screenshots"  src="https://github.com/waghbhavana/NewsApp-MVVM-Architecture/blob/main/assets/sreenshots.jpg">
</p>
