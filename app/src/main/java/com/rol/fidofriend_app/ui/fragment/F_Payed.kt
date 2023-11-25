package com.rol.fidofriend_app.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.rol.fidofriend_app.databinding.FragmentFPayedBinding
import com.rol.fidofriend_app.ui.viewmodel.ProductViewModel
import com.rol.fidofriend_app.ui.viewmodel.ServiceViewModel


class F_Payed : Fragment() {

    private var _binding: FragmentFPayedBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductViewModel by activityViewModels()
    private val serviceViewModel: ServiceViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFPayedBinding.inflate(inflater, container, false)

        val total = arguments?.getDouble("total", 0.0) ?: 0.0

        binding.totalTextView.text = "S/.  $total"

        binding.btnPayed.setOnClickListener {
            clearFields()
            productViewModel.clearProducts()
            serviceViewModel.clearServices()

            // Cerrar este fragmento y regresar a F_Payment
            parentFragmentManager.popBackStack()

            Toast.makeText(requireContext(), "Se realizó el pago con éxito.", Toast.LENGTH_SHORT).show()

        }

        return binding.root

    }

    private fun clearFields(){
        binding.editTitular.text.clear()
        binding.editNumeroTar.text.clear()
        binding.editCVV.text.clear()
        binding.editFech.text.clear()
        binding.totalTextView.text = ""
        binding.checkData.isChecked = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
