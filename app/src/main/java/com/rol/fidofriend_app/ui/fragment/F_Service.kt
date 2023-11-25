package com.rol.fidofriend_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rol.fidofriend_app.R
import com.rol.fidofriend_app.databinding.FragmentFServiceBinding


class F_Service : Fragment() {

    private var _binding: FragmentFServiceBinding? = null
    private val binding get() = _binding!!
    private val isVet by lazy {
        arguments?.getBoolean("IS_VET", false) ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardViewTop.setOnClickListener {
            val fragment = F_Grooming()

            val bundle = Bundle()
            bundle.putBoolean("IS_VET", isVet)
            fragment.arguments = bundle

            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.fragment_container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }

        binding.cardViewBottom.setOnClickListener {
            val fragment = F_PetHotel()

            val bundle = Bundle()
            bundle.putBoolean("IS_VET", isVet)
            fragment.arguments = bundle

            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.fragment_container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}
