package com.example.dotify1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isInvisible
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify1.MainActivity.Companion.ALBUM_KEY
import com.example.dotify1.databinding.ActivitySongListBinding

class SongListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongListBinding
    var playingSing: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }

        val listOfSongs = SongDataProvider.getAllSongs() as MutableList<Song>

        with(binding) {
            val adapter = SongListAdapter(listOfSongs)
            songList.adapter = adapter

            adapter.onSongClickListener = { song ->
                songContainer.isInvisible = false
                curSong.text = getString(R.string.song_container).format(song.title, song.artist)
                //TODO:boolean needed?
                playingSing = song
            }


            shuffleButton.setOnClickListener {
                Log.i("shuffled", "clicked1")

                val shuffedSongs = listOfSongs.shuffled()
                adapter.updateSongs(shuffedSongs)
            }

            curSong.setOnClickListener {
                onClickToSpecificAlmbum()
            }

        }
    }

    //TODO: IS this bad? for the toast
    fun onClickToSpecificAlmbum() {
        if (playingSing != null) {
            val intent = Intent(this@SongListActivity, MainActivity::class.java)
            intent.putExtra(ALBUM_KEY, playingSing)
            startActivity(intent)
            Log.i("curSong", "clicked2")

        }
//        else {
//            Toast.makeText(getBaseContext(), "No song selected", Toast.LENGTH_LONG).show()
//            Log.i("curSong", "clicked3")
//        }
    }
}