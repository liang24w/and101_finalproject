package com.example.and101finalproject


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SecondPokeAdapter (
    private val pokeList: ArrayList<String>,
    private val pokeNameList: ArrayList<String>,
    private val pokeIdList: ArrayList<String>,
    private val pokeDescList: ArrayList<String>
) : RecyclerView.Adapter<SecondPokeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokeImage: ImageView
        val nameView: TextView
        val idView: TextView
        val pokeDescView: TextView
        val button: Button

        init {
            // Find our RecyclerView item's ImageView for future use
            button = view.findViewById(R.id.adoptButton)
            button.text = "Unadopt"
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
        holder.idView.setText(pokeIdList[position])
        holder.pokeDescView.setText(pokeDescList[position])

        // `holder` can used to reference any View within the RecyclerView item's layout file
        holder.pokeImage.setOnClickListener {
            Toast.makeText(holder.itemView.context, "clicked ${pokeNameList[position]}", Toast.LENGTH_SHORT).show()
        }

        holder.button.setOnClickListener() {
            pokeList.removeAt(position)
            pokeNameList.removeAt(position)
            pokeDescList.removeAt(position)
            pokeIdList.removeAt(position)

            this.notifyDataSetChanged()
        }
    }
}