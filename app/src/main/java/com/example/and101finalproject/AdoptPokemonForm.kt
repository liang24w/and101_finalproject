package com.example.and101finalproject

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class AdoptPokemonForm : AppCompatActivity() {

    private lateinit var rvPokes: RecyclerView
    private lateinit var adapter: SecondPokeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adopt_pokemon_form)

        rvPokes = findViewById(R.id.endRV)

        adapter = SecondPokeAdapter(intent.getStringArrayListExtra("a") as ArrayList<String>,
                                    intent.getStringArrayListExtra("b") as ArrayList<String>,
                                    intent.getStringArrayListExtra("c") as ArrayList<String>,
                                    intent.getStringArrayListExtra("d") as ArrayList<String>)

        rvPokes.adapter = adapter
        rvPokes.layoutManager = LinearLayoutManager(this)
        rvPokes.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        findViewById<Button>(R.id.goBack).setOnClickListener() {
            finish()
        }

    }
}