package com.example.dotify1

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

import com.example.dotify1.manager.ApiManager
import com.example.dotify1.manager.NotificationManager
import com.example.dotify1.model.Song
import com.example.dotify1.repository.DataRepository

const val DOTIFY_SETTING_PREFERENCE = "DOTIFY_SETTINGS_PREFERENCES"
const val NOTIFICATION_ON = "NOTIFICATION_ON"

class DotifyApplication : Application() {

    lateinit var dataRepository: DataRepository
    lateinit var apiManager: ApiManager
    lateinit var newSongNotificationManager: NotificationManager
    lateinit var preferences: SharedPreferences
    var selectedSong : Song? = null


    override fun onCreate() {
        super.onCreate()

        this.dataRepository = DataRepository()
        this.apiManager = ApiManager()
        this.newSongNotificationManager = NotificationManager(this)

        this.preferences = getSharedPreferences(DOTIFY_SETTING_PREFERENCE, Context.MODE_PRIVATE)

        if(preferences.getBoolean(NOTIFICATION_ON, false)){
            newSongNotificationManager.triggerNotificationRepetitive()
        }
    }

}