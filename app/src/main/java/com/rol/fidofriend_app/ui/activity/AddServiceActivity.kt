package com.rol.fidofriend_app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.rol.fidofriend_app.databinding.ActivityAddServiceBinding
import com.rol.fidofriend_app.model.Service
import com.rol.fidofriend_app.ui.viewmodel.ServiceViewModel

class AddServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddServiceBinding
    private val viewModel: ServiceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityAddServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val serviceName = intent.getStringExtra("SERVICE_NAME") ?: ""
        binding.editNameService.setText(serviceName)
        binding.editNameService.isEnabled = false

        binding.btnSaveService.setOnClickListener {
            val description = binding.editDescriptionService.text.toString()
            val price = binding.editPriceService.text.toString().toDouble()
            val imgUrl = binding.editImage.text.toString()
            if (serviceName.isNotEmpty()) {
                val service = Service(name = serviceName, description = description, price = price
                    , imgUrl = imgUrl)
                viewModel.createService(service)
                Toast.makeText(this, "Se registro con éxito!!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "No se pudo registrar...", Toast.LENGTH_SHORT).show()
            }
        }
    }

}