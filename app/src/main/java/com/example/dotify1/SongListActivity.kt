package com.example.dotify1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.lifecycle.lifecycleScope
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify1.databinding.ActivitySongListBinding
import com.example.dotify1.model.SongList
import kotlinx.coroutines.launch

const val CUR_SONG = "curSong"

class SongListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongListBinding
    private lateinit var DotifyApplication: DotifyApplication
    private lateinit var allSong: SongList
    var playingSing: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }
        DotifyApplication = (application as DotifyApplication)


        val listOfSongs = SongDataProvider.getAllSongs() as MutableList<Song>
        val adapter = SongListAdapter(listOfSongs)

        lifecycleScope.launch {
            getListOfSongs()
        }

        with(binding) {
            songList.adapter = adapter

            adapter.onSongClickListener = { song ->
                songContainer.isInvisible = false
                curSong.text = getString(R.string.songContainer).format(song.title, song.artist)
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
                onClickToSpecificAlbum()
            }

            if (savedInstanceState != null) {
                val savedCurrentlyPlaying = savedInstanceState.getParcelable<Song>(CUR_SONG)
                if (savedCurrentlyPlaying != null ) {
                    songContainer.isInvisible = false
                    playingSing = savedCurrentlyPlaying
                    curSong.text = root.context.getString(R.string.songContainer,
                                                            savedCurrentlyPlaying.title,
                                                            savedCurrentlyPlaying.artist)
                }
            }

            songContainer.setOnClickListener {
                val selectedSong: Song = DotifyApplication.selectedSong ?: return@setOnClickListener
                navigateToPlayerActivity(this@SongListActivity)
            }
        }

    }

    suspend fun getListOfSongs() {
        this.allSong =  DotifyApplication.dataRepository.getSongs()
    }

    fun onClickToSpecificAlbum() {
        if (playingSing != null) {
            val intent = Intent(this@SongListActivity, PlayerActivity::class.java)
            intent.putExtra(CUR_SONG, playingSing)
            startActivity(intent)
        }
    }

    //store the information before it is destroyed
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(CUR_SONG, playingSing)
        super.onSaveInstanceState(outState)
    }

}