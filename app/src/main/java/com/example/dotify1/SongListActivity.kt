package com.example.dotify1

import SongList
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.lifecycle.lifecycleScope
import com.example.dotify1.databinding.ActivitySongListBinding
import com.example.dotify1.manager.ApiManager
import com.example.dotify1.model.Song
import kotlinx.coroutines.launch

const val SELECTED_SONG = "curSong"

class SongListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongListBinding
    private lateinit var DotifyApplication: DotifyApplication
    private lateinit var allSongs: SongList
    private lateinit var apiManager: ApiManager
    private lateinit var listOfsongs: List<Song>


    var playingSing: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }
        DotifyApplication = (application as DotifyApplication)
        this.apiManager = (this.applicationContext as DotifyApplication).apiManager

        lifecycleScope.launch {
            with(binding) {
                allSongs = DotifyApplication.dataRepository.getSongs()

                listOfsongs = allSongs.songs


                val adapter = SongListAdapter(listOfsongs)
                songList.adapter = adapter

                adapter.onSongClickListener = { song ->
                    songContainer.isInvisible = false
                    curSong.text = getString(R.string.songContainer).format(song.title, song.artist)
                    playingSing = song
                }

                adapter.onSongLongPressClickListener = { title, position ->
                    Toast.makeText(
                        this@SongListActivity,
                        getString(R.string.deleteMessage).format(title),
                        Toast.LENGTH_SHORT
                    ).show()
//                adapter.songList.removeAt(position)
                }

                shuffleButton.setOnClickListener {
//                val shuffedSongs = listOfSongs.shuffled()
                    adapter.updateSongs(listOfsongs)
                }

                curSong.setOnClickListener {
                    onClickToSpecificAlbum()
                }

                if (savedInstanceState != null) {
                    val savedCurrentlyPlaying =
                        savedInstanceState.getParcelable<Song>(SELECTED_SONG)
                    if (savedCurrentlyPlaying != null) {
                        songContainer.isInvisible = false
                        playingSing = savedCurrentlyPlaying
                        curSong.text = root.context.getString(
                            R.string.songContainer,
                            savedCurrentlyPlaying.title,
                            savedCurrentlyPlaying.artist
                        )
                    }
                }

                songContainer.setOnClickListener {
                    navigateToPlayerActivity(this@SongListActivity)
                }
            }
        }
    }


    fun onClickToSpecificAlbum() {
        if (playingSing != null) {
            val intent = Intent(this@SongListActivity, PlayerActivity::class.java)
            intent.putExtra(SELECTED_SONG, playingSing)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(SELECTED_SONG, playingSing)
        super.onSaveInstanceState(outState)
    }
}
