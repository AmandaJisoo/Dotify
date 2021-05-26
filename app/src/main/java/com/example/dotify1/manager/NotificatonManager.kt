package com.example.dotify1.manager

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.wifi.WifiConfiguration.AuthAlgorithm.strings
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify1.PlayerActivity
import com.example.dotify1.model.SongList
import java.util.concurrent.TimeUnit


private const val NEW_UPLOADED_MUSIC = "New Uploaded Music"


class NotificatonManager(private val context: Context){


    private val workManager: WorkManager = WorkManager.getInstance(context)
    private val newSongNotificationManager = NotificationManagerCompat.from(context)
    private var songs: SongList? = null

    fun triggerNotificationOnce() {
        val request = OneTimeWorkRequestBuilder<SongNotificationWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .addTag(NEW_UPLOADED_MUSIC)
            .build()

        workManager.enqueue(request)
    }

    fun triggerNotificationRepetitive() {
        if (isRepetitiveNotificationOn()) {
           return
        }

        //20, TimeUnit.MINUTES
        val requestNotification = PeriodicWorkRequestBuilder<SongNotificationWorker>(5, TimeUnit.SECONDS)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .addTag(NEW_UPLOADED_MUSIC)
            .build()

        workManager.enqueue(requestNotification)
    }


    fun cancelNotificationRepetitive() {
        workManager.cancelAllWorkByTag(NEW_UPLOADED_MUSIC)
    }

    private fun isRepetitiveNotificationOn(): Boolean {
        return workManager.getWorkInfosByTag(NEW_UPLOADED_MUSIC).get().any {
            when(it.state) {
                WorkInfo.State.RUNNING,
                WorkInfo.State.ENQUEUED -> true
                else -> false
            }
        }
    }


    fun updateSongList(songs: SongList) {
        this.songs  = songs
    }

    private fun selectRandomSongFromList(): Song {
        val randomSong =  SongDataProvider.getAllSongs()
        return randomSong.random()
    }
}