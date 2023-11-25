package com.rol.fidofriend_app.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.rol.fidofriend_app.R
import com.rol.fidofriend_app.data.sharedpref.SessionManager
import com.rol.fidofriend_app.databinding.FragmentFGroomingBinding
import com.rol.fidofriend_app.databinding.FragmentFPetHotelBinding
import com.rol.fidofriend_app.ui.activity.AddServiceActivity
import com.rol.fidofriend_app.ui.adapter.ServiceAdapter
import com.rol.fidofriend_app.ui.viewmodel.ServiceViewModel


class F_PetHotel : Fragment() {

    private var _binding: FragmentFPetHotelBinding? = null
    private val binding get() = _binding!!
    /*private val isVet by lazy {
        arguments?.getBoolean("IS_VET", false) ?: false
    }*/
    private val sessionManager by lazy { SessionManager(requireContext()) }
    private val isVet by lazy { sessionManager.isVet }

    private val adapter by lazy {
        ServiceAdapter ({ service ->
            if (viewModel.isServiceInCartS(service)) {
                Toast.makeText(context, "Este producto ya está agregado.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.selectedServices.value = viewModel.selectedServices.value?.toMutableList()?.apply { add(service) }
                Toast.makeText(context, "Se agregó al carrito de compras.", Toast.LENGTH_SHORT).show()
            }
        }, true, isVet)
    }

    private val viewModel: ServiceViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFPetHotelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewPetHotel.adapter = adapter

        binding.btnAddPetHotel.setOnClickListener {
            val intent = Intent(requireContext(), AddServiceActivity::class.java)
            intent.putExtra("SERVICE_NAME", "Hotel-Mascota")
            startActivity(intent)
        }

        //val isVet = arguments?.getBoolean("IS_VET", false) ?: false
        binding.btnAddPetHotel.visibility = if (isVet) View.VISIBLE else View.GONE

        viewModel.services.observe(requireActivity()) { services ->
            if (services != null) {
                adapter.submitList(null)
                adapter.submitList(services)
            } else {
                Toast.makeText(requireContext(), "No se pudieron cargar los servicios...", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.getServicesByName("Hotel-Mascota")
    }

    override fun onResume() {
        super.onResume()
        viewModel.getServicesByName("Hotel-Mascota")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}