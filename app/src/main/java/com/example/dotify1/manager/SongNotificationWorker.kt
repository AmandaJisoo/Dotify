package com.example.dotify1.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ericchee.songdataprovider.Song
import com.example.dotify1.CUR_SONG
import com.example.dotify1.DotifyApplication
import com.example.dotify1.PlayerActivity
import com.example.dotify1.R
import java.lang.Exception
import kotlin.random.Random

private const val NEW_SONG_CHANNEL_ID = "NEW_SONG_CHANNEL_ID"

class SongNotificationWorker (
                            private val context: Context,
                            workerParameters: WorkerParameters):
                            CoroutineWorker(context, workerParameters) {

    private val application by lazy { context.applicationContext as DotifyApplication }
    private val dataRepository by lazy { application.dataRepository }
    private val newSongNotificationManager by lazy { application.newSongNotificationManager }
    private val DotifyApplication by lazy { context.applicationContext as DotifyApplication }

    private val notificationManager = NotificationManagerCompat.from(context)

    override suspend fun doWork(): Result {
        initNewSongNotificationChannel()

        val selectedSong = selectRandomSong()

        return try {
            publishNewSongNotification(selectedSong)
            val songs = dataRepository.getSongs()
            newSongNotificationManager.updateSongList(songs)
            Result.success()
        }catch(ex: Exception){
            Result.failure()
        }
    }

    private suspend fun selectRandomSong(): Song {
        val listOfSongs = DotifyApplication.dataRepository.getSongs()
        val songs: List<Song> = listOfSongs.songs
        return songs.random()
    }

    private fun publishNewSongNotification(curSong: Song) {
        val intent = Intent(context, PlayerActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(CUR_SONG, curSong)
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)


        val builder = NotificationCompat.Builder(context, NEW_SONG_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_music_note_24)
            .setContentTitle(context.getString(R.string.newSongMsg, curSong.artist))
            .setContentText(context.getString(R.string.newSongMsg2, curSong.title))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(notificationManager) {
            val notificationId = Random.nextInt()
            notify(notificationId, builder.build())
        }
    }


    private fun initNewSongNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel For Newly Updated Song"
            val descriptionText = "Notification About New Song"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(NEW_SONG_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}

