package com.rol.fidofriend_app.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.rol.fidofriend_app.databinding.ActivityPaymentBinding
import com.rol.fidofriend_app.ui.viewmodel.ProductViewModel
import com.rol.fidofriend_app.ui.viewmodel.ServiceViewModel

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var serviceViewModel: ServiceViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        serviceViewModel = ViewModelProvider(this).get(ServiceViewModel::class.java)

        val total = intent.getDoubleExtra("total", 0.0)

        binding.totalTextView.text = "S/.  $total"

        binding.btnPayed.setOnClickListener {
            productViewModel.clearProducts()
            serviceViewModel.clearServices()
            finish()
        }

    }

}