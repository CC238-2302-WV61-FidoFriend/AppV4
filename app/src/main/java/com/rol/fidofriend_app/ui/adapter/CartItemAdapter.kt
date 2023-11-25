package com.rol.fidofriend_app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rol.fidofriend_app.databinding.ItemProductBinding
import com.rol.fidofriend_app.databinding.ItemServiceBinding
import com.rol.fidofriend_app.model.Product
import com.rol.fidofriend_app.model.Service

class CartItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Any>()

    fun submitList(newItems: List<Any>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Product -> VIEW_TYPE_PRODUCT
            is Service -> VIEW_TYPE_SERVICE
            else -> throw IllegalArgumentException("Unsupported item type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_PRODUCT -> ProductViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            VIEW_TYPE_SERVICE -> ServiceViewHolder(ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalArgumentException("Unsupported view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is Product -> (holder as ProductViewHolder).bind(item)
            is Service -> (holder as ServiceViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    companion object {
        private const val VIEW_TYPE_PRODUCT = 0
        private const val VIEW_TYPE_SERVICE = 1
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            binding.textNombre.text = "Nombre: " + product.name
            binding.textDescription.text = "Descripción " + product.description
            binding.textPrice.text = "Precio S/. : " + product.price
            Glide.with(binding.root)
                .load(product.imgUrl)
                .into(binding.imageProduct)

            binding.btnAddToCart.visibility = View.GONE

        }
    }

    inner class ServiceViewHolder(private val binding: ItemServiceBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(service: Service) {
            binding.textNameService.text = "Nombre: " + service.name
            binding.textDescriptionService.text = "Descripción: " + service.description
            binding.textPriceService.text = "Precio: S/. " + service.price
            Glide.with(binding.root)
                .load(service.imgUrl)
                .into(binding.imageService)

            binding.btnAddToCartService.visibility = View.GONE

        }
    }
}

