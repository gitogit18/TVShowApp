package com.example.tvshowapp.viewmodel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvshowapp.model.Show
import com.example.tvshowapp.model.ShowRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: ShowRepository,
    private val showId: Int
) : ViewModel() {

    var uiState by mutableStateOf<UiState<Show>>(
        UiState.Loading
    )
        private set

    init {
        loadShow()
    }

    fun loadShow() {

        viewModelScope.launch {

            uiState = UiState.Loading

            try {

                val show = repository.getShow(showId)

                uiState = UiState.Success(show)

            } catch (e: Exception) {

                uiState = UiState.Error(
                    e.message ?: "Failed to load show"
                )
            }
        }
    }
}