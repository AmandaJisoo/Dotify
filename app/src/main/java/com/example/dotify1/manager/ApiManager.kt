package com.example.dotify1.manager

import com.ericchee.songdataprovider.Song
import com.example.dotify1.repository.DataRepository

class ApiManager {
    var dataRepository = DataRepository()

    var selectedSong: Song? = null
        private set

    fun onSongSelected(song: Song) {
        selectedSong = song
    }

}