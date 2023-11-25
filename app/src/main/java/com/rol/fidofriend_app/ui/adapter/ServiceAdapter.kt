package com.rol.fidofriend_app.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rol.fidofriend_app.databinding.ItemServiceBinding
import com.rol.fidofriend_app.model.Service


class ServiceAdapter(private val onAddToCartClickedS: (Service) -> Unit, private val showButton: Boolean,
   private val isVet: Boolean) : ListAdapter<Service, ServiceAdapter.ServiceViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ItemServiceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ServiceViewHolder(private val binding: ItemServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(service: Service) {
            binding.textNameService.text = "Nombre: " + service.name
            binding.textDescriptionService.text = "Descripción: " + service.description
            binding.textPriceService.text = "Precio: " + service.price
            Glide.with(binding.root)
                .load(service.imgUrl)
                .into(binding.imageService)

            binding.btnAddToCartService.visibility = if (showButton && !isVet) View.VISIBLE else View.GONE
            binding.btnAddToCartService.setOnClickListener {
                onAddToCartClickedS(service)
                Log.d("ServiceAdapter", "Servicio agregado al carrito: ${service.name}")
            }

        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Service>() {
        override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem == newItem
        }
    }
}