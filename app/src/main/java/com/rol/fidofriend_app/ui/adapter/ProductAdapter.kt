package com.rol.fidofriend_app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rol.fidofriend_app.databinding.ItemProductBinding
import com.rol.fidofriend_app.model.Product

class ProductAdapter(private val onAddToCartClicked: (Product) -> Unit, private val showButton: Boolean,
private val isVet: Boolean) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(prod: Product) {
            binding.textNombre.text = "Nombre: " + prod.name
            binding.textDescription.text = "Descripción " + prod.description
            binding.textPrice.text = "Precio S/. : " + prod.price
            Glide.with(binding.root)
                .load(prod.imgUrl)
                .into(binding.imageProduct)

            //binding.btnAddToCart.visibility = if (showButton) View.VISIBLE else View.GONE
            binding.btnAddToCart.visibility = if (showButton && !isVet) View.VISIBLE else View.GONE
            binding.btnAddToCart.setOnClickListener {
                onAddToCartClicked(prod)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}