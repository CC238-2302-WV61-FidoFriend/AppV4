package com.rol.fidofriend_app.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.rol.fidofriend_app.databinding.ActivityGroomingBinding
import com.rol.fidofriend_app.ui.adapter.ServiceAdapter
import com.rol.fidofriend_app.ui.viewmodel.ServiceViewModel
import kotlin.properties.Delegates

class GroomingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroomingBinding
    private val viewModel: ServiceViewModel by viewModels()
    private var isVet by Delegates.notNull<Boolean>()
    private val adapter by lazy {
        ServiceAdapter ({ service ->
            if (viewModel.isServiceInCartS(service)) {
                Toast.makeText(this, "Este servicio ya está agregado.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.selectedServices.value = viewModel.selectedServices.value?.toMutableList()?.apply { add(service) }
                Toast.makeText(this, "Se agregó al carrito de compras.", Toast.LENGTH_SHORT).show()
            }
        }, true, isVet)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroomingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val isVet = intent.getBooleanExtra("IS_VET", false)
        isVet = intent.getBooleanExtra("IS_VET", false)
        binding.btnAddGrooming.visibility = if (isVet) View.VISIBLE else View.GONE

        binding.btnAddGrooming.setOnClickListener {
            val intent = Intent(this, AddServiceActivity::class.java)
            intent.putExtra("SERVICE_NAME", "Aseo")
            startActivity(intent)
        }

        binding.recyclerViewGrooming.adapter = adapter

        viewModel.services.observe(this) { services ->
            if (services != null) {
                adapter.submitList(services)
            } else {
                Toast.makeText(this, "No se pudieron cargar los servicios...", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.getServicesByName("Aseo")
    }

    override fun onResume() {
        super.onResume()
        viewModel.getServicesByName("Aseo")
    }

}