package com.example.and101finalproject

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import okhttp3.internal.http2.Header
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var pokeList: MutableList<String>
    private lateinit var rvPokes: RecyclerView
    private lateinit var adapter: PokeAdapter
    private lateinit var pokeNameList: MutableList<String>
    private lateinit var pokeIdList: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvPokes = findViewById(R.id.pet_list)
        pokeList = mutableListOf()
        pokeNameList = mutableListOf()
        pokeIdList = mutableListOf()
        adapter = PokeAdapter(pokeList, pokeNameList, pokeIdList)

        rvPokes.adapter = adapter
        rvPokes.layoutManager = LinearLayoutManager(this@MainActivity)
        rvPokes.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))

        getPokemonImageArray()
    }

    private fun getPokemonImageArray() {
        val client = AsyncHttpClient()
        for (i in 0 until 10) {
            val url = "https://pokeapi.co/api/v2/pokemon/$i"

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
                    val pokeName = pokejson.jsonObject.getJSONObject("species").getString("name")
                    val pokeId = pokejson.jsonObject.getString("id")

                    pokeIdList.add(pokeId)
                    pokeNameList.add(pokeName)
                    pokeList.add(pokeImageUrl)

                    adapter.notifyDataSetChanged()
                }
            })
        }
    }
}