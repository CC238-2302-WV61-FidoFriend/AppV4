package com.rol.fidofriend_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.rol.fidofriend_app.R
import com.rol.fidofriend_app.databinding.FragmentFPaymentBinding
import com.rol.fidofriend_app.model.Product
import com.rol.fidofriend_app.model.Service
import com.rol.fidofriend_app.ui.adapter.CartItemAdapter
import com.rol.fidofriend_app.ui.viewmodel.ProductViewModel
import com.rol.fidofriend_app.ui.viewmodel.ServiceViewModel



class F_Payment : Fragment() {

    private var _binding: FragmentFPaymentBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductViewModel by activityViewModels()
    private val serviceViewModel: ServiceViewModel by activityViewModels()
    private val adapter by lazy {
        CartItemAdapter()
    }

    private var products = emptyList<Product>()
    private var services = emptyList<Service>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewShopping.adapter = adapter

        productViewModel.selectedProducts.observe(viewLifecycleOwner) { products ->
            this.products = products
            updateCart()
        }

        serviceViewModel.selectedServices.observe(viewLifecycleOwner) { services ->
            this.services = services
            updateCart()
        }

        binding.btnPayment.setOnClickListener {
            val total = (products.sumOf { it.price } ?: 0.0) +
                    (services.sumOf { it.price } ?: 0.0)

            // Crear una nueva instancia de F_Payed
            val fragment = F_Payed().apply {
                arguments = Bundle().apply {
                    putDouble("total", total)
                }
            }

            // Obtener el FragmentManager y comenzar una transacción
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            // Reemplazar F_Payment con F_Payed
            fragmentTransaction.replace(R.id.fragment_container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

    }

    private fun updateCart() {
        val products = productViewModel.selectedProducts.value ?: emptyList()
        val services = serviceViewModel.selectedServices.value ?: emptyList()
        val items = mutableListOf<Any>()
        items.addAll(products)
        items.addAll(services)
        adapter.submitList(items)
        val total = (products.sumOf { it.price } ?: 0.0) +
                (services.sumOf { it.price } ?: 0.0)
        binding.textTotal.text = "Total: S/. $total"

        // Mostrar u ocultar la vista vacía
        if (items.isEmpty()) {
            binding.emptyView.visibility = View.VISIBLE
            binding.recyclerViewShopping.visibility = View.GONE
        } else {
            binding.emptyView.visibility = View.GONE
            binding.recyclerViewShopping.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




