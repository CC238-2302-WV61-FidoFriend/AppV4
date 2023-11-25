package com.rol.fidofriend_app.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.rol.fidofriend_app.R
import com.rol.fidofriend_app.data.sharedpref.SessionManager
import com.rol.fidofriend_app.databinding.FragmentFProductBinding
import com.rol.fidofriend_app.databinding.FragmentFProfileBinding
import com.rol.fidofriend_app.ui.activity.AddProductActivity
import com.rol.fidofriend_app.ui.activity.RegisterActivity
import com.rol.fidofriend_app.ui.adapter.ProductAdapter
import com.rol.fidofriend_app.ui.viewmodel.ProductViewModel

class F_Product : Fragment() {

    private var _binding: FragmentFProductBinding? = null
    private val binding get() = _binding!!
    /*private val isVet by lazy {
        arguments?.getBoolean("IS_VET", false) ?: false
    }*/
    private val sessionManager by lazy { SessionManager(requireContext()) }
    private val isVet by lazy { sessionManager.isVet }

    private val adapter by lazy {
        ProductAdapter ({ product ->
            if (viewModel.isProductInCart(product)) {
                Toast.makeText(context, "Este producto ya está agregado.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.selectedProducts.value = viewModel.selectedProducts.value?.toMutableList()?.apply { add(product) }
                Toast.makeText(context, "Se agregó al carrito de compras.", Toast.LENGTH_SHORT).show()
            }
        }, true, isVet)
    }

    private val viewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter

        binding.btnAddProduct.setOnClickListener {
            startActivity(Intent(requireContext(), AddProductActivity::class.java))
        }

        //val isVet = arguments?.getBoolean("IS_VET", false) ?: false
        // Muestra u oculta el botón de agregar basándote en el valor de isVet
        binding.btnAddProduct.visibility = if (isVet) View.VISIBLE else View.GONE

        viewModel.productStatus = { products ->
            if (products != null) {
                adapter.submitList(null)
                adapter.submitList(products)
            } else {
                Toast.makeText(context, "No se pudieron cargar los productos...", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.getProducts()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProducts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}