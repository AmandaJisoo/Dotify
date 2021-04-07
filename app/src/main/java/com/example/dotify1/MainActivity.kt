package com.example.dotify1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun changeUsername(view: View) {
        val username = findViewById<TextView>(R.id.username_update_btn)
        Log.i("username", username.toString())
    }
}


