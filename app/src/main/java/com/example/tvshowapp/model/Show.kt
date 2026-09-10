package com.example.tvshowapp.model

data class Show (
    val id: Int,
    val name: String,
    val image: Image?,
    val rating: Rating?,
    val summary: String?,
    val premiered: String?,
    val url: String?
)

data class Image(
    val medium: String?,
    val original: String?
)

data class Rating(
    val average: Double?
)