package com.example.and101finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokeAdapter (
    private val pokeList: List<String>,
    private val pokeNameList: List<String>,
    private val pokeIdList: List<String>,
    private val pokeDescList: List<String>
) : RecyclerView.Adapter<PokeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokeImage: ImageView
        val nameView: TextView
        val idView: TextView
        val pokeDescView: TextView

        init {
            // Find our RecyclerView item's ImageView for future use
            pokeImage = view.findViewById(R.id.poke_image)
            nameView = view.findViewById(R.id.pokeName)
            idView = view.findViewById(R.id.pokeId)
            pokeDescView = view.findViewById(R.id.pokeDesc)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.api_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = pokeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(pokeList[position])
            .centerCrop()
            .into(holder.pokeImage)

        // passing in pokeName and pokeId from MainActivity
        holder.nameView.setText(pokeNameList[position])
        holder.idView.setText("Ability: " + pokeIdList[position])
        holder.pokeDescView.setText(pokeDescList[position])

        // `holder` can used to reference any View within the RecyclerView item's layout file
        holder.pokeImage.setOnClickListener {
            Toast.makeText(holder.itemView.context, "clicked ${pokeNameList[position]}", Toast.LENGTH_SHORT).show()
        }
    }
}