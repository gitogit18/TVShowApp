package com.example.tvshowapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tvshowapp.model.ShowRepository

class DetailViewModelFactory(
    private val repository: ShowRepository,
    private val showId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                DetailViewModel::class.java
            )
        ) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(
                repository,
                showId
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}