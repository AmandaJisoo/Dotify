package com.example.dotify1

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    var initalNumOfPlay = Random.nextInt(20, 200)
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpUsernameUpdateButton()
        setUpNumOfPlay()
        setUpPlayBtn()
        setUpPreviousButton()
        setUpNextButton()
        chageAlbumCoverColor()
    }

    @SuppressLint("SetTextI18n")
    fun setUpUsernameUpdateButton() {
        val updateUsernameButton = findViewById<Button>(R.id.usernameUpdateBtn);
        val usernameText = findViewById<TextView>(R.id.username);
        val usernameEditText = findViewById<EditText>(R.id.user_edit_input);

        updateUsernameButton.setOnClickListener() {
            if (updateUsernameButton.text == "CHANGE USER") {
                Log.i("once", "here")
                updateUsernameButton.text = "Apply";
                usernameEditText.setText("");
                usernameText.visibility = View.GONE;
                usernameEditText.visibility = View.VISIBLE;
            } else if (updateUsernameButton.text == "Apply" && usernameEditText.text.toString() != "") {
                updateUsernameButton.text = "CHANGE USER";
                usernameText.text = usernameEditText.text.toString();
                usernameEditText.visibility = View.GONE;
                usernameText.visibility = View.VISIBLE;
            } else {
                Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun setUpNumOfPlay() {
        val songNumber = findViewById<TextView>(R.id.numOfPlay)
        songNumber.text = "$initalNumOfPlay plays"
    }

    fun setUpPlayBtn() {
        val playButton= findViewById<ImageView>(R.id.playBtn);
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
}


