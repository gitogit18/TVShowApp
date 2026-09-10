# TV Show App

A simple Android TV show browser built with Kotlin and Jetpack Compose using the TVMaze API.

## Features

- Browse TV shows from the TVMaze API
- Display show poster, title, and rating
- Handle shows with unavailable ratings
- View TV show details
- Display original poster, title, summary, and premiere date
- Properly render HTML content from show summaries
- Share TV show information
- Loading, success, and error states
- Retry after an API error
- MVVM architecture
- Unit tests for the HomeViewModel

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Retrofit
- Gson
- Coil
- Kotlin Coroutines
- Android Architecture Components
- JUnit

## API

This application uses the [TVMaze API](https://www.tvmaze.com/api).

### Endpoints

Show list:

`GET https://api.tvmaze.com/shows?page=0`

Show detail:

`GET https://api.tvmaze.com/shows/{id}`

## Architecture

The project uses **MVVM (Model-View-ViewModel)** architecture.

```text
UI (Jetpack Compose)
        |
        v
ViewModel
        |
        v
Repository
        |
        v
TVMaze API
```
## Architecture

The application follows a simple **MVVM (Model-View-ViewModel)** architecture to separate data, business logic, and UI responsibilities.

### Model

The Model layer contains:

* Data models such as `Show`, `Image`, and `Rating`
* `TvMazeApi` for defining API endpoints
* `RetrofitInstance` for configuring Retrofit
* `ShowRepository` for accessing and managing API data

The Repository separates the data source from the ViewModel, making the data layer easier to maintain and test.

### ViewModel

The ViewModel is responsible for:

* Loading TV show data
* Managing loading, success, and error states
* Processing the show list into different categories
* Preparing data for the UI

The UI state is represented using a sealed `UiState`:

```kotlin
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
```

### View

The View layer is implemented using **Jetpack Compose**.

It contains:

* Home screen
* Show list items
* Detail screen
* Navigation

The UI observes the state exposed by the ViewModel and displays the appropriate screen based on the current `UiState`.

## Error Handling

The application handles three main states:

### Loading

A loading indicator is displayed while show data is being fetched from the API.

### Success

When the API request succeeds, the retrieved TV shows are processed and displayed to the user.

### Error

If the API request fails, an error message and a **Retry** button are displayed, allowing the user to attempt the request again.

## Testing

The project contains unit tests for `HomeViewModel`.

The tests cover:

1. Successful API responses and show data processing
2. API failure and error state handling

The tests use a **fake API implementation**, so they do not depend on the actual TVMaze API or an internet connection.

This allows the ViewModel logic to be tested in isolation and makes the tests more reliable and repeatable.

## How to Run

Follow these steps to run the application locally:

1. **Clone this repository**

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
```

2. **Open the project** in Android Studio.

3. **Sync the project with Gradle** and allow Android Studio to download all required dependencies.

4. **Run the application** on an Android emulator or a physical Android device.

5. **Make sure the device has an internet connection**, as the application retrieves TV show data from the **TVMaze API**.

### Requirements

* Android Studio
* Android SDK
* An Android emulator or physical Android device
* Internet connection

* ## Architecture Decisions

This project uses a simple **MVVM (Model-View-ViewModel)** architecture with a Repository layer.

### 1. MVVM Architecture

**Decision:**  
Use MVVM to separate UI rendering, UI state, and data-related logic.

**Reason:**  
The application has multiple UI states (loading, success, and error) and requires API data to be transformed before being displayed. Keeping this logic inside the ViewModel prevents the Compose UI from handling networking and data processing directly.

**Drawbacks:**  
For a small application with only two screens, MVVM introduces additional classes and boilerplate compared to putting the logic directly in the UI. However, the separation makes the application easier to test and maintain as the project grows.

---

### 2. Repository Pattern

**Decision:**  
Use a `ShowRepository` between the ViewModel and `TvMazeApi`.

**Reason:**  
The Repository provides a single abstraction for accessing show data. It keeps the ViewModel independent from the specific networking implementation and makes it possible to replace the real API with a fake implementation during unit testing.

**Drawbacks:**  
For this relatively small application, the Repository adds another layer of abstraction that may not be strictly necessary. It could be considered over-engineering for such a simple data source.

---

### 3. Retrofit for Networking

**Decision:**  
Use Retrofit to communicate with the TVMaze API.

**Reason:**  
Retrofit provides a clear way to define API endpoints using Kotlin interfaces and integrates well with Kotlin coroutines. It also handles HTTP requests and response conversion without requiring manual networking code.

**Drawbacks:**  
It introduces an external dependency and additional configuration. For this small project, a simpler HTTP client could potentially be sufficient.

---

### 4. Jetpack Compose for UI

**Decision:**  
Use Jetpack Compose for all application UI.

**Reason:**  
Jetpack Compose was required by the technical test and allows the UI to be built declaratively. UI state can be directly represented using Compose state and the ViewModel's `UiState`.

**Drawbacks:**  
Compose can introduce a learning curve and requires familiarity with state management and recomposition. It may also require additional consideration when dealing with more complex UI architectures.

---
### 5. Local JVM Unit Tests with a Fake API

**Decision:**  
Use local unit tests with a fake `TvMazeApi` instead of making real network requests.

**Reason:**  
Tests should be deterministic and should not depend on internet connectivity or the availability of the TVMaze API. A fake API also allows success and failure scenarios to be tested consistently.

**Drawbacks:**  
A fake API does not verify that the real Retrofit implementation communicates correctly with TVMaze. Integration or instrumented tests would be needed to validate the complete networking stack.

## What I'd Improve With More Time

- Add pagination for browsing more shows
- Add local caching for offline access
- Add more comprehensive unit tests
- Improve network error handling
- Add season, episode, and cast information
- Improve UI/UX, accessibility, and responsive design

## Known Limitations

- Only the first page of TVMaze results is loaded.
- The app requires an internet connection to fetch show data.
- No local caching or offline support.
- Network errors are handled with a general error message.
- Detail screen does not include season, episode, or cast information.
- Unit test coverage is limited to the HomeViewModel.
- No UI/instrumented tests.

## Additional Documentation

- [AI Usage Log](AI_LOG.md) — Documents how AI tools were used during development.
- [Code Review](CODE_REVIEW.md) — Review of the provided AI-generated code and identified issues.
- [Reflection](REFLECTION.md) — Reflection on the development process and technical decisions.
- [Walkthrough Video](<YOUR_VIDEO_LINK>) — Short demonstration of the application and development process.


