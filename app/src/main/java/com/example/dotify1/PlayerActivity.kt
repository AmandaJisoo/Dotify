package com.example.dotify1

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.annotation.RequiresApi
import coil.load
import com.example.dotify1.databinding.ActivityMainBinding
import com.example.dotify1.model.Song
import kotlin.random.Random




fun navigateToPlayerActivity(context: Context) = with(context){
    val intent = Intent(this, PlayerActivity::class.java).apply {
    }
    startActivity(intent)
}

class PlayerActivity : AppCompatActivity() {
    private var initalNumOfPlay: Int = Random.nextInt(20, 200)
    private lateinit var curSong: Song
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val CUR_SONG = "curSong"
        const val NUM_OF_PLAY = "numOfSong"
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pickedSong: Song? = intent.getParcelableExtra(CUR_SONG)
        val songCount = findViewById<TextView>(R.id.numOfPlay)


        val DotifyApplication = (application as DotifyApplication)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        if (savedInstanceState != null) {
            songCount.text =  savedInstanceState.getInt(NUM_OF_PLAY, 0).toString()
            initalNumOfPlay = savedInstanceState.getInt(NUM_OF_PLAY, 0)
        } else  {
            val intentSong: Song? = intent.extras?.getParcelable(CUR_SONG)
            if(intentSong != null) {
                val songFromIntent: Song? = intent.extras?.getParcelable(CUR_SONG)
                if(songFromIntent != null) {
                    DotifyApplication.selectedSong = intentSong
                }
            }
        }

        with(binding) {
            DotifyApplication.selectedSong?.let { selected ->
                albumImage.load(selected.smallImageURL) {
                    crossfade(true)
                    placeholder(R.drawable.doraemon)
                }
                albumTitle.text = selected.title
                artist.text = selected.artist
            }
        }

        setUpNumOfPlay()
        setUpPlayBtn()
        setUpPreviousButton()
        setUpNextButton()
        changeAlbumCoverColor()



        if (pickedSong != null) {
            curSong = pickedSong
            val albumImage = findViewById<ImageButton>(R.id.albumImage);
            val albumTitle = findViewById<TextView>(R.id.albumTitle);
            val artist = findViewById<TextView>(R.id.artist);

            albumImage.load(pickedSong.largeImageURL)
            albumTitle.text = pickedSong.title
            artist.text = pickedSong.artist
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }


    fun setUpNumOfPlay() {
        val songNumber = findViewById<TextView>(R.id.numOfPlay)
        songNumber.text = String.format(getString(R.string.playNum), this.initalNumOfPlay.toString())
    }

    fun setUpPlayBtn() {
        val playButton= findViewById<ImageButton>(R.id.playBtn);
        val numOfPlay = findViewById<TextView>(R.id.numOfPlay)
        playButton.setOnClickListener() {
            initalNumOfPlay++;
            numOfPlay.text = String.format(getString(R.string.playNum), this.initalNumOfPlay.toString())
        }
    }

    fun setUpPreviousButton() {
        val prevPlayBtn= findViewById<ImageButton>(R.id.previousBtn);
        prevPlayBtn.setOnClickListener() {
            Toast.makeText(applicationContext,"Skipping to previous track", Toast.LENGTH_SHORT).show();
        }
    }

    fun setUpNextButton() {
        val nextPlatBtn= findViewById<ImageButton>(R.id.nextPlayBtn)
        nextPlatBtn.setOnClickListener() {
            Toast.makeText(applicationContext,"Skipping to next track", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun changeAlbumCoverColor() {
        val albumCover = findViewById<ImageButton>(R.id.albumImage)
        val numOfPlay = findViewById<TextView>(R.id.numOfPlay)
        albumCover.setOnLongClickListener() {
            val rnd = Random.Default
            numOfPlay.setTextColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)))
            return@setOnLongClickListener true
        }
    }

    

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }

        if (item.itemId == R.id.action_settings) {
            val dotifyApplication = (application as DotifyApplication)

            dotifyApplication.selectedSong?.let { nonNullSongObj ->
                navigateToSettingsActivity(this@PlayerActivity, nonNullSongObj, initalNumOfPlay)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(NUM_OF_PLAY, initalNumOfPlay)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu_items, menu)
        return true
    }
}

