package com.example.dotify1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val updateUsernameButton = findViewById<Button>(R.id.username_update_btn);
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
}


