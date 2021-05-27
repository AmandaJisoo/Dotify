package com.example.dotify1.model

import kotlinx.parcelize.Parcelize

import android.os.Parcelable

@Parcelize
data class Song (
    val id: String,
    val title: String,
    val artist: String,
    val durationMillis: Int,
    val smallImageURL: String? = null,
    val largeImageURL: String? = null
): Parcelable