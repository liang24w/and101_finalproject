package com.example.and101finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class ViewPokemon : AppCompatActivity() {

    private lateinit var pokeList: MutableList<String>
    private lateinit var rvPokes: RecyclerView
    private lateinit var adapter: PokeAdapter
    private lateinit var pokeNameList: MutableList<String>
    private lateinit var pokeIdList: MutableList<String>
    private lateinit var pokeDescList: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pokemon)

//        // Set OnClickListener outside onCreate
//        nextBtn.setOnClickListener {
//            // Define actions to perform when the button is clicked
//            // For example, start a new activity:
//            val intent = Intent(this, AdoptPokemonForm::class.java)
//            startActivity(intent)
//        }
        rvPokes = findViewById(R.id.poke_list)
        pokeList = mutableListOf()
        pokeNameList = mutableListOf()
        pokeIdList = mutableListOf()
        pokeDescList = mutableListOf()
        adapter = PokeAdapter(pokeList, pokeNameList, pokeIdList, pokeDescList)

        rvPokes.adapter = adapter
        rvPokes.layoutManager = LinearLayoutManager(this)
        rvPokes.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        getPokemonImageArray()

    }

    private fun getPokemonImageArray() {
        val client = AsyncHttpClient()
        val client2 = AsyncHttpClient()
        for (i in 0 until 10) {
            val url = "https://pokeapi.co/api/v2/pokemon/$i"
            val descurl = "https://pokeapi.co/api/v2/pokemon-species/$i"

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
}