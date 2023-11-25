package com.rol.fidofriend_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.rol.fidofriend_app.databinding.FragmentFFavoriteBinding
import com.rol.fidofriend_app.ui.adapter.FavoriteAdapter
import com.rol.fidofriend_app.ui.viewmodel.FavoriteViewModel


class F_Favorite : Fragment() {

    private var _binding: FragmentFFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFFavoriteBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FavoriteAdapter(listOf()) {
        }

        binding.recyclerViewF.adapter = adapter

        viewModel.getFavorites()

        viewModel.favorites.observe(requireActivity()){ favorites ->
            adapter.favorite = favorites
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavorites()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}