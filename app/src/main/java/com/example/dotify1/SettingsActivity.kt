package com.example.dotify1


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.ericchee.songdataprovider.Song

private const val SONG = "curSong"
private const val PLAY_COUNT_KEY = "numOfPlay"


fun activateSettingsActivity(context: Context, song: Song, playCount: Int) {
    with(context) {
        startActivity(Intent(this, SettingsActivity::class.java).apply {
            putExtra(SONG, song)
            putExtra(PLAY_COUNT_KEY, playCount)
        })
    }
}

class SettingsActivity : AppCompatActivity() {
    private val navController by lazy { findNavController(R.id.settingNavHost) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val extras: Bundle? = intent.extras

        if (extras != null) {
            navController.setGraph(R.navigation.nav_graph, extras)
        }
        setupActionBarWithNavController(navController)

    }

    override fun onSupportNavigateUp() = navController.navigateUp()

}