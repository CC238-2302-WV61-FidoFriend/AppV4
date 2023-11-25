package com.rol.fidofriend_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.rol.fidofriend_app.data.sharedpref.ThemeManager
import com.rol.fidofriend_app.databinding.FragmentFSettingBinding

class F_Setting : Fragment() {

    private var _binding: FragmentFSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var themeManager: ThemeManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFSettingBinding.inflate(inflater, container, false)
        themeManager = ThemeManager(requireContext())
        themeManager.applyTheme()

        binding.themedark.setOnClickListener {
            if (!themeManager.isDarkMode) {
                themeManager.isDarkMode = true
                themeManager.applyTheme()
            } else {
                Toast.makeText(context, "Ya se encuentra en modo oscuro", Toast.LENGTH_SHORT).show()
            }
        }

        binding.themelight.setOnClickListener {
            if (themeManager.isDarkMode) {
                themeManager.isDarkMode = false
                themeManager.applyTheme()
            } else {
                Toast.makeText(context, "Ya se encuentra en modo claro", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
