package com.example.dotify1.model

import com.ericchee.songdataprovider.Song

data class SongList(
    val title: String,
    val numOfSongs: Int,
    val songs: List<Song>,
)

