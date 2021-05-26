package com.example.dotify1.manager

import android.R
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
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify1.CUR_SONG
import com.example.dotify1.DotifyApplication
import com.example.dotify1.PlayerActivity
import java.lang.Exception
import kotlin.random.Random

private const val NEW_SONG_CHANNEL_ID = "NEW_SONG_CHANNEL_ID"

class SongNotificationWorker (
    private val context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    private val application by lazy { context.applicationContext as DotifyApplication }
    private val dataRepository by lazy { application.dataRepository }
    private val newSongNotificationManager by lazy { application.newSongNotificationManager }
    private val notificationManager = NotificationManagerCompat.from(context)
    private val DotifyApp by lazy { context.applicationContext as DotifyApplication }

    override suspend fun doWork(): Result {
        initNewSongChannel()
        val allSong = DotifyApp.dataRepository.getSongs()
        val songs: List<Song> = allSong.songs
        val randomSong: Song = songs.random()

        return try {
            publishNewSongNotification(randomSong)
            val library = dataRepository.getSongs()
            newSongNotificationManager.updateSongList(library)
            Result.success()
        }catch(ex: Exception){
            Result.failure()
        }
    }



    private fun publishNewSongNotification(selectedSong: Song) {
        val intent = Intent(context, PlayerActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(CUR_SONG, selectedSong)
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)


        //Here: WRONG asset
        val builder = NotificationCompat.Builder(context, NEW_SONG_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_btn_speak_now)
            .setContentTitle("${selectedSong.artist} just released a new song!!!")
            .setContentText("Listen to ${selectedSong.title} now on Dotify")
            .setContentIntent(pendingIntent)    // sets the action when user clicks on notification
            .setAutoCancel(true)    // This will dismiss the notification tap
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(notificationManager) {
            val notificationId = kotlin.random.Random.nextInt()
            notify(notificationId, builder.build())
        }
    }



    private fun initNewSongChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "New Uploaded Music"
            val descriptionText = "Notification About new song"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(NEW_SONG_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            notificationManager.createNotificationChannel(channel)
        }
    }
}