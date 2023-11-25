package com.rol.fidofriend_app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.rol.fidofriend_app.databinding.ActivityAddProductBinding
import com.rol.fidofriend_app.model.Product
import com.rol.fidofriend_app.ui.viewmodel.ProductViewModel

class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding
    private val viewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveProduct.setOnClickListener {
            val name = binding.editNameProduct.text.toString()
            val description = binding.editDescriptionProduct.text.toString()
            val priceText = binding.editPriceProduct.text.toString()
            val imgUrl = binding.editImage.text.toString()

            if (name.isEmpty() || description.isEmpty() || priceText.isEmpty()) {
                Toast.makeText(this, "A excepción de imagen, todos los campos son obligatorios.", Toast.LENGTH_SHORT).show()
            } else {
                val price = priceText.toDouble()
                val product = Product(name = name, description = description, price = price, imgUrl = imgUrl)
                viewModel.postProduct(product)
                Toast.makeText(this, "Se ha registrado con éxito.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    }
}