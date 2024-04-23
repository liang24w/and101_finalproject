package com.example.and101finalproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random
interface ButtonClickListener {
    fun onButtonClick(a: String, b: String, c: String, d: String)
}

class ViewPokemon : AppCompatActivity(), ButtonClickListener {

    private lateinit var pokeList: MutableList<String>
    private lateinit var rvPokes: RecyclerView
    private lateinit var adapter: PokeAdapter
    private lateinit var pokeNameList: MutableList<String>
    private lateinit var pokeIdList: MutableList<String>
    private lateinit var pokeDescList: MutableList<String>

    val pokeList2 : ArrayList<String> = ArrayList()
    val pokeNameList2 : ArrayList<String> = ArrayList()
    val pokeIdList2 : ArrayList<String> = ArrayList()
    val pokeDescList2 : ArrayList<String> = ArrayList()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pokemon)

        rvPokes = findViewById(R.id.poke_list)
        pokeList = mutableListOf()
        pokeNameList = mutableListOf()
        pokeIdList = mutableListOf()
        pokeDescList = mutableListOf()
        adapter = PokeAdapter(pokeList, pokeNameList, pokeIdList, pokeDescList, this)

        findViewById<Button>(R.id.checkoutAdoption).setOnClickListener() {
            val intent = Intent(this, AdoptPokemonForm::class.java)
            intent.putExtra("a", pokeList2)
            intent.putExtra("b", pokeNameList2)
            intent.putExtra("c", pokeIdList2)
            intent.putExtra("d", pokeDescList2)
            startActivity(intent)
        }

        rvPokes.adapter = adapter
        rvPokes.layoutManager = LinearLayoutManager(this)
        rvPokes.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        getPokemonImageArray()

    }

    private fun getPokemonImageArray() {
        val client = AsyncHttpClient()
        val client2 = AsyncHttpClient()
        for (i in 0 until 30) {
            val k = Random.nextInt(1,500)
            val url = "https://pokeapi.co/api/v2/pokemon/$k"
            val descurl = "https://pokeapi.co/api/v2/pokemon-species/$k"

            client.get(url, object : JsonHttpResponseHandler() {
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    throwable: Throwable?
                ) {
                    Log.d("Pokemon Error", errorResponse)
                }

                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers?,
                    pokejson: JsonHttpResponseHandler.JSON
                ) {
                    val pokeImageUrl =
                        pokejson.jsonObject.getJSONObject("sprites").getString("front_default")
                    val pokeName = pokejson.jsonObject.getString("name").capitalize()
                    val pokeId = pokejson.jsonObject.getJSONArray("abilities").getJSONObject(0)
                        .getJSONObject("ability").getString("name")

                    pokeIdList.add(pokeId)
                    pokeNameList.add(pokeName)
                    pokeList.add(pokeImageUrl)

                    adapter.notifyDataSetChanged()
                }
            }) //end client

            client2.get(descurl, object : JsonHttpResponseHandler() {
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String,
                    throwable: Throwable?
                ) {
                    Log.d("Pokemon Description Error", response)
                }

                override fun onSuccess(statusCode: Int, headers: Headers?, json: JsonHttpResponseHandler.JSON) {
                    val pokeDesc = json.jsonObject.getJSONArray("flavor_text_entries").getJSONObject(0).getString("flavor_text")

                    pokeDescList.add(pokeDesc)

                    adapter.notifyDataSetChanged()
                }

            }) //end client2
        }
    }

    override fun onButtonClick(a: String, b: String, c: String, d: String) {
        pokeList2.add(a)
        pokeNameList2.add(b)
        pokeIdList2.add(c)
        pokeDescList2.add(d)
    }
}