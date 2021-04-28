package com.example.dotify1

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.annotation.RequiresApi
import com.ericchee.songdataprovider.Song
import kotlin.random.Random


class PlayerActivity : AppCompatActivity() {
    private var initalNumOfPlay: Int = Random.nextInt(20, 200)
    private lateinit var curSong: Song

    companion object {
        const val ALBUM_KEY = "ALBUM_KEY"
        const val NUM_OF_PLAY = "numOfSong"

//        const val SONG_KEY = ""
//        const val
    }


    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pickedSong: Song? = intent.getParcelableExtra(ALBUM_KEY)
        val songCount = findViewById<TextView>(R.id.numOfPlay)


        if (pickedSong != null) {

            //how can I not do this?
            curSong = pickedSong
            val albumImage = findViewById<ImageButton>(R.id.albumImage);
            val albumTitle = findViewById<TextView>(R.id.albumTitle);
            val artist = findViewById<TextView>(R.id.artist);

            albumImage.setImageResource(pickedSong.largeImageID)
            albumTitle.text = pickedSong.title
            artist.text = pickedSong.artist
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState != null) {
            songCount.text =  savedInstanceState.getInt(NUM_OF_PLAY, 0).toString()
            initalNumOfPlay = savedInstanceState.getInt(NUM_OF_PLAY, 0)
        } else  {
            songCount.text = initalNumOfPlay.toString()
        }


        setUpNumOfPlay()
        setUpPlayBtn()
        setUpPreviousButton()
        setUpNextButton()
        chageAlbumCoverColor()
        setUpSettingsButton()
    }

    @SuppressLint("SetTextI18n")
    fun setUpNumOfPlay() {
        val songNumber = findViewById<TextView>(R.id.numOfPlay)
        songNumber.text = "$initalNumOfPlay plays"
    }

    @SuppressLint("SetTextI18n")
    fun setUpPlayBtn() {
        val playButton= findViewById<ImageButton>(R.id.playBtn);
        val numOfPlay = findViewById<TextView>(R.id.numOfPlay)
        playButton.setOnClickListener() {
            initalNumOfPlay++;
            numOfPlay.text = "$initalNumOfPlay plays";
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
    fun chageAlbumCoverColor() {
        val albumCover = findViewById<ImageButton>(R.id.albumImage)
        val numOfPlay = findViewById<TextView>(R.id.numOfPlay)
        albumCover.setOnLongClickListener() {
            val rnd = Random.Default
            numOfPlay.setTextColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)))
            return@setOnLongClickListener true
        }
    }

    fun setUpSettingsButton() {
        val nextPlatBtn= findViewById<Button>(R.id.settingBtn)
        nextPlatBtn.setOnClickListener() {
            setUpSettingAcivity()
        }

    }
    private fun setUpSettingAcivity() {
        activateSettingsActivity(this@PlayerActivity, curSong, initalNumOfPlay)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(NUM_OF_PLAY, initalNumOfPlay)
        super.onSaveInstanceState(outState)
    }
}


