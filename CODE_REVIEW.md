# Code Review Task

An AI generated the code below. Imagine you're reviewing this PR. In a file called `CODE_REVIEW.md`, list every problem you'd flag and how you'd fix each one.

If applying for Android:

```kotlin
class MovieViewModel : ViewModel() {

    var movies: List<Movie> = emptyList()

    fun loadMovies() {
        val url = URL("https://api.example.com/movies")
        val data = url.readText()
        movies = parseMovies(data)
    }
}
```
# 1. Hardcoded API URL

## Problem

```kotlin
val url = URL("https://api.example.com/movies")
```

The API URL is hardcoded directly inside the ViewModel.

This makes the ViewModel responsible for both the UI logic and the API configuration. If the API URL needs to be changed later, we would have to modify the ViewModel.

## Fix

Move the API endpoint into a separate API/service layer and let the repository handle the API call.

For example:

```kotlin
interface MovieApi {
    @GET("movies")
    suspend fun getMovies(): List<Movie>
}
```

Then:

```kotlin

class MovieRepository(
    private val api: MovieApi
) {
    suspend fun getMovies(): List<Movie> {
        return api.getMovies()
    }
}
```

The ViewModel can then use the repository instead of creating the URL itself.

# 2. No Error Handling

## Problem

There is no error handling around the network request or parsing operation:

```kotlin
val data = url.readText()
movies = parseMovies(data)
```

If the request fails, for example because there is no internet connection, the server is unavailable, or the response cannot be parsed, the application could throw an exception.

## Fix

Handle the possible error and update the UI state accordingly.

For example:

```kotlin
try {
    val movies = repository.getMovies()
    _uiState.value = UiState.Success(movies)
} catch (e: Exception) {
    _uiState.value = UiState.Error(
        e.message ?: "Failed to load movies"
    )
}
```

This allows the UI to show an error message instead of letting the request fail without being handled.

# 3. No Loading State

## Problem

The ViewModel only stores the movie list:

var movies: List<Movie> = emptyList()

The UI has no way to know whether the application is currently loading the movies.

For example, an empty list could mean that the data is still loading, the API returned no movies, or the request has not been completed yet.

## Fix

Create a UI state that represents the different states of the API request:

``` kotlin
sealed interface UiState {
    data object Loading : UiState
    data class Success(val movies: List<Movie>) : UiState
    data class Error(val message: String) : UiState
}
```

Then the ViewModel can update the state while loading the data:

```kotlin
fun loadMovies() {
    viewModelScope.launch {
        _uiState.value = UiState.Loading

        try {
            val movies = repository.getMovies()
            _uiState.value = UiState.Success(movies)
        } catch (e: Exception) {
            _uiState.value = UiState.Error(
                e.message ?: "Failed to load movies"
            )
        }
    }
}
```

The UI can observe this state and show a loading indicator, the movie list, or an error message depending on the current state.
