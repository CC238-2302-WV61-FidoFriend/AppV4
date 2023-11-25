package com.rol.fidofriend_app.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rol.fidofriend_app.databinding.ItemPetBinding
import com.rol.fidofriend_app.model.Pet
import com.rol.fidofriend_app.ui.activity.DetailPetActivity

class PetAdapter() : ListAdapter<Pet, PetAdapter.PetViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = ItemPetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PetViewHolder(private val binding: ItemPetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(pet: Pet) {
            binding.textNamePet.text = "Nombre: " + pet.name
            binding.textSexPet.text = "Sexo: " + pet.sex
            Glide.with(binding.root)
                .load(pet.imgUrl)
                .into(binding.imagePet)

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailPetActivity::class.java)
                intent.putExtra("pet", pet)
                binding.root.context.startActivity(intent)
            }

        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Pet>() {
        override fun areItemsTheSame(oldItem: Pet, newItem: Pet): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Pet, newItem: Pet): Boolean {
            return oldItem == newItem
        }
    }
}