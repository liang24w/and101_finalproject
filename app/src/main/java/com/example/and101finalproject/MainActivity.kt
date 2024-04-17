package com.example.and101finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var nextBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nextBtn = findViewById(R.id.nextBtn)

        // Set OnClickListener outside onCreate
        nextBtn.setOnClickListener {
            // Define actions to perform when the button is clicked
            // For example, start a new activity:
            val intent = Intent(this, ViewPokemon::class.java)
            startActivity(intent)
        }
    }


}