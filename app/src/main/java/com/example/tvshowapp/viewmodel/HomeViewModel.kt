package com.example.tvshowapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvshowapp.model.Show
import com.example.tvshowapp.model.ShowRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ShowRepository
) : ViewModel() {

    var uiState by mutableStateOf<UiState<List<Show>>>(
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

                uiState = UiState.Success(shows)
            } catch (e: Exception) {
                uiState = UiState.Error(
                    e.message ?: "Failed to load shows, please try again"
                )
            }
        }
    }
}