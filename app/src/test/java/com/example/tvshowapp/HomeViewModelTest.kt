package com.example.tvshowapp.viewmodel

import com.example.tvshowapp.model.Image
import com.example.tvshowapp.model.Rating
import com.example.tvshowapp.model.Show
import com.example.tvshowapp.model.ShowRepository
import com.example.tvshowapp.model.TvMazeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadShows returns success with processed show categories`() = runTest {

        // Arrange
        val fakeShows = listOf(
            createShow(
                id = 1,
                name = "Low Rated Show",
                rating = 6.0,
                premiered = "2020-01-01"
            ),
            createShow(
                id = 2,
                name = "Breaking Bad",
                rating = 9.5,
                premiered = "2008-01-20"
            ),
            createShow(
                id = 3,
                name = "The Office",
                rating = 8.5,
                premiered = "2005-03-24"
            )
        )

        val fakeApi = FakeTvMazeApi(
            shows = fakeShows
        )

        val repository = ShowRepository(fakeApi)

        // Act
        val viewModel = HomeViewModel(repository)
        advanceUntilIdle()

        // Assert
        assertTrue(
            "Expected Success but got ${viewModel.uiState}",
            viewModel.uiState is UiState.Success
        )

        val state = viewModel.uiState as UiState.Success
        val content = state.data

        // Highest rated should be first
        assertEquals(
            "Breaking Bad",
            content.highestRated.first().name
        )

        // Featured contains the top 3 here because we only provide 3 shows
        assertEquals(
            3,
            content.featured.size
        )

        // Alphabetical order
        assertEquals(
            "Breaking Bad",
            content.alphabetical.first().name
        )

        assertEquals(
            "Low Rated Show",
            content.alphabetical[1].name
        )

        assertEquals(
            "The Office",
            content.alphabetical[2].name
        )
    }

    @Test
    fun `loadShows returns error when API fails`() = runTest {

        // Arrange
        val fakeApi = FakeTvMazeApi(
            error = Exception("Network error")
        )

        val repository = ShowRepository(fakeApi)

        // Act
        val viewModel = HomeViewModel(repository)
        advanceUntilIdle()

        // Assert
        assertTrue(
            "Expected Error but got ${viewModel.uiState}",
            viewModel.uiState is UiState.Error
        )

        val state = viewModel.uiState as UiState.Error

        assertEquals(
            "Network error",
            state.message
        )
    }

    private fun createShow(
        id: Int,
        name: String,
        rating: Double,
        premiered: String
    ): Show {
        return Show(
            id = id,
            name = name,
            image = Image(
                medium = "medium.jpg",
                original = "original.jpg"
            ),
            rating = Rating(rating),
            summary = "<p>Test summary</p>",
            premiered = premiered,
            url = "https://example.com"
        )
    }
}

private class FakeTvMazeApi(
    private val shows: List<Show> = emptyList(),
    private val error: Exception? = null
) : TvMazeApi {

    override suspend fun getShows(page: Int): List<Show> {
        error?.let { throw it }
        return shows
    }

    override suspend fun getShow(id: Int): Show {
        error?.let { throw it }
        return shows.first { it.id == id }
    }
}