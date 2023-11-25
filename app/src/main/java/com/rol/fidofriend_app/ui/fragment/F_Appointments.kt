package com.rol.fidofriend_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.rol.fidofriend_app.databinding.FragmentFAppointmentsBinding
import com.rol.fidofriend_app.ui.adapter.AppointmentAdapter
import com.rol.fidofriend_app.ui.viewmodel.UserProfileViewModel

class F_Appointments : Fragment() {

    private var _binding: FragmentFAppointmentsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserProfileViewModel by viewModels()
    private val adapter by lazy {
        AppointmentAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFAppointmentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewUser.adapter = adapter

        viewModel.vets.observe(viewLifecycleOwner) { vets ->
            if (vets != null) {
                adapter.submitList(null)
                adapter.submitList(vets)
            } else {
                Toast.makeText(
                    context,
                    "No se pudieron cargar los veterinarios...",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.getVets()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
