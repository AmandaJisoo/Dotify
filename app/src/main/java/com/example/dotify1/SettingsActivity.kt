package com.example.dotify1


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.dotify1.PlayerActivity.Companion.CUR_SONG
import com.example.dotify1.PlayerActivity.Companion.NUM_OF_PLAY


fun navigateToSettingsActivity(context: Context, song: com.example.dotify1.model.Song, playCount: Int) = with(context){
    val intent = Intent(this, SettingsActivity::class.java).apply {
        val bundle = Bundle().apply {
            putParcelable(CUR_SONG, song)
            putInt(NUM_OF_PLAY, playCount)
        }
        putExtras(bundle)
    }
    startActivity(intent)
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