package com.rol.fidofriend_app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rol.fidofriend_app.data.local.entity.FavoritePet
import com.rol.fidofriend_app.databinding.ItemFavoriteBinding

class FavoriteAdapter(var favorite:List<FavoritePet>, val onFavoriteClick: (FavoritePet) -> Unit) : RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding, onFavoriteClick)
    }

    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(favorite[position])
    }

    inner class FavViewHolder(private val binding: ItemFavoriteBinding, val onFavoriteClick: (FavoritePet) -> Unit): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(favorite: FavoritePet){
            binding.textNamePetF.text = "Nombre: ${favorite.name}"
            binding.textAgePetF.text = "Edad: ${favorite.age}"
            binding.textDescriptionPetF.text = "Descripción: ${favorite.description}"
            binding.textSexPetF.text = "Sexo: ${favorite.sex}"

            Glide.with(binding.root.context)
                .load(favorite.imgUrl)
                .into(binding.imagePetF)

            binding.root.setOnClickListener {
                onFavoriteClick(favorite)
            }
        }
    }
}