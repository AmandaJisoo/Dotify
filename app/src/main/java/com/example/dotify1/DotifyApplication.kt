package com.example.dotify1

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.ericchee.songdataprovider.Song
import com.example.dotify1.manager.ApiManager
import com.example.dotify1.manager.NotificatonManager
import com.example.dotify1.repository.DataRepository

const val DITIFY_SETTING_PREFERECE = "DITIFY_SETTINGS_PREFERECE"
const val NOTIFICATION_ON = "NOTIFICATION_ON"

class DotifyApplication : Application() {
    lateinit var dataRepository: DataRepository
    lateinit var apiManager: ApiManager
    lateinit var newSongNotificationManager: NotificatonManager
    lateinit var preferences: SharedPreferences
    var selectedSong : Song? = null


    override fun onCreate() {
        super.onCreate()

        this.dataRepository = DataRepository()
        this.apiManager = ApiManager()
        this.newSongNotificationManager = NotificatonManager(this)

        //setting preference
        this.preferences = getSharedPreferences(DITIFY_SETTING_PREFERECE, Context.MODE_PRIVATE)

        if(preferences.getBoolean(NOTIFICATION_ON, false)){
            newSongNotificationManager.triggerNotificationRepetitive()
        }


    }

}