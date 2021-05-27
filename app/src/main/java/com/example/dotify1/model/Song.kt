package com.example.dotify1.model

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val durationMillis: Int,
    val smallImageURL: String? = null,
    val largeImageURL: String? = null
)