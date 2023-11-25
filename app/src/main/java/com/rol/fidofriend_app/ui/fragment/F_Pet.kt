package com.rol.fidofriend_app.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.rol.fidofriend_app.R
import com.rol.fidofriend_app.databinding.FragmentFPetBinding
import com.rol.fidofriend_app.databinding.FragmentFProductBinding
import com.rol.fidofriend_app.ui.activity.AddPetActivity
import com.rol.fidofriend_app.ui.activity.AddProductActivity
import com.rol.fidofriend_app.ui.adapter.PetAdapter
import com.rol.fidofriend_app.ui.viewmodel.PetViewModel


class F_Pet : Fragment() {

    private var _binding: FragmentFPetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PetViewModel by activityViewModels()
    private val adapter by lazy {
        PetAdapter()
    }

    private val userId by lazy {
        arguments?.getInt("USER_ID") ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFPetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewPet.adapter = adapter

        binding.btnAddPet.setOnClickListener {
            val intent = Intent(requireContext(), AddPetActivity::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }

        viewModel.petStatus = { pets ->
            if (pets != null) {
                adapter.submitList(null)
                adapter.submitList(pets)
            } else {
                Toast.makeText(context, "No se pudieron cargar las mascotas...", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getUserPets(userId)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserPets(userId)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

