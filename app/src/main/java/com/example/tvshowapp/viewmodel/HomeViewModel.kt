package com.example.tvshowapp.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tvshowapp.model.Show
import com.example.tvshowapp.model.ShowRepository
import kotlinx.coroutines.launch

data class HomeUiContent(
    val featured: List<Show>,
    val highestRated: List<Show>,
    val byYear: List<Show>,
    val alphabetical: List<Show>
)

class HomeViewModel(
    private val repository: ShowRepository
) : ViewModel() {

    var uiState by mutableStateOf<UiState<HomeUiContent>>(
        UiState.Loading
    )
        private set

    init {
        loadShows()
    }

    fun loadShows(){

        viewModelScope.launch {
            uiState = UiState.Loading

            try {
                val shows = repository.getShows()

                val featured = shows
                    .sortedByDescending { it.rating?.average ?: 0.0 }
                    .take(5)

                val highestRated = shows
                    .sortedByDescending { it.rating?.average ?: 0.0 }

                val byYear = shows
                    .sortedByDescending { it.premiered ?: "" }

                val alphabetical = shows.sortedBy { it.name }

                uiState = UiState.Success(
                    HomeUiContent(
                        featured = featured,
                        highestRated = highestRated,
                        byYear = byYear,
                        alphabetical = alphabetical
                    )
                )
            } catch (e: Exception) {
                uiState = UiState.Error(
                    e.message ?: "Failed to load shows, please try again"
                )
            }
        }
    }
}