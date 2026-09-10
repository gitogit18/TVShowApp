package com.example.tvshowapp.model

class ShowRepository(
    private val api: TvMazeApi
) {
    private var shouldFail = true
    suspend fun getShows(): List<Show> {
        return api.getShows()
    }

    suspend fun getShow(id: Int): Show {
        return api.getShow(id)
    }
}