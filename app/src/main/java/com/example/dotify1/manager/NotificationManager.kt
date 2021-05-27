package com.example.dotify1.manager

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.example.dotify1.model.SongList
import java.util.concurrent.TimeUnit


private const val NEW_UPLOADED_MUSIC = "New Uploaded Music"


class NotificationManager(private val context: Context){
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

        val requestNotification = PeriodicWorkRequestBuilder<SongNotificationWorker>(20, TimeUnit.MINUTES)
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
}