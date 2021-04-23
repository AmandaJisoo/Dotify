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
        val adapter = SongListAdapter(listOfSongs)

        with(binding) {
            songList.adapter = adapter

            adapter.onSongClickListener = { song ->
                songContainer.isInvisible = false
                curSong.text = getString(R.string.songContainer).format(song.title, song.artist)
                //TODO:boolean needed?
                playingSing = song
            }

            adapter.onSongLongPressClickListener = {title, position ->
                Toast.makeText(this@SongListActivity, getString(R.string.deleteMessage).format(title), Toast.LENGTH_SHORT).show()
                adapter.songList.removeAt(position)
            }

            shuffleButton.setOnClickListener {
                val shuffedSongs = listOfSongs.shuffled()
                adapter.updateSongs(shuffedSongs)
            }

            curSong.setOnClickListener {
                onClickToSpecificAlmbum()
            }
        }
    }

    fun onClickToSpecificAlmbum() {
        if (playingSing != null) {
            val intent = Intent(this@SongListActivity, MainActivity::class.java)
            intent.putExtra(ALBUM_KEY, playingSing)
            startActivity(intent)
        }
    }
}