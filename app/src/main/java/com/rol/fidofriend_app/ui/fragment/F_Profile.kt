package com.rol.fidofriend_app.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.rol.fidofriend_app.R
import com.rol.fidofriend_app.databinding.FragmentFProfileBinding
import com.rol.fidofriend_app.model.User
import com.rol.fidofriend_app.ui.viewmodel.LoginViewModel
import com.rol.fidofriend_app.ui.viewmodel.UserProfileViewModel

class F_Profile : Fragment() {

    private var _binding: FragmentFProfileBinding? = null
    private val binding get() = _binding ?: error("Binding not initialized")
    private val viewModel: UserProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstName = binding.edtNombres
        val lastName = binding.edtApellidos
        val email = binding.edtCorreo
        val address = binding.edtDireccion
        val description = binding.edtDescripcion
        val photo = binding.edtFoto
        invisibleText()

        binding.btnEdit.setOnClickListener {
            showText()
            binding.btnEdit.visibility = View.GONE
        }

        viewModel.userProfile = { user ->
            if (user != null) {
                firstName.setText(user.firstName)
                lastName.setText(user.lastName)
                email.setText(user.email)
                address.setText(user.address)
                description.setText(user.description)
                photo.setText(user.imgUrl)

                binding.nameText.text = "Nombre: ${user.firstName} ${user.lastName}"
                binding.emailText.text = "Email: ${user.email}"
                binding.vetText.text = if (user.isVet) "Tipo Usuario: Veterinario" else "Tipo Usuario: Cliente"

                Glide.with(this)
                    .load(user.imgUrl)
                    .placeholder(R.mipmap.ic_launcher) //loading
                    .error(R.mipmap.ic_launcher) //error
                    .into(binding.profileImage)

            } else {
                Toast.makeText(context, "No se pudo obtener los datos del usuario...", Toast.LENGTH_SHORT).show()
            }
        }


        binding.btnUpdateSave.setOnClickListener {
            val userId = arguments?.getInt("USER_ID", -1)
            if (userId != null && userId != -1) {
                val user = User(
                    id = userId,
                    firstName = firstName.text.toString(),
                    lastName = lastName.text.toString(),
                    email = email.text.toString(),
                    address = address.text.toString(),
                    description = description.text.toString(),
                    imgUrl = photo.text.toString()

                )
                viewModel.updateUser(user)
                invisibleText()
                binding.btnEdit.visibility = View.VISIBLE
                cleanText()
                Toast.makeText(context, "El usuario ha sido Actualizado.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "No se pudo actualizar al usuario.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cleanText(){
        binding.edtNombres.setText("")
        binding.edtApellidos.setText("")
        binding.edtCorreo.setText("")
        binding.edtDescripcion.setText("")
        binding.edtDireccion.setText("")
        binding.edtFoto.setText("")
    }

    private fun invisibleText(){
        binding.edtNombres.visibility = View.INVISIBLE
        binding.edtApellidos.visibility = View.INVISIBLE
        binding.edtCorreo.visibility = View.INVISIBLE
        binding.edtDireccion.visibility = View.INVISIBLE
        binding.edtDescripcion.visibility = View.INVISIBLE
        binding.edtFoto.visibility = View.INVISIBLE
        binding.btnUpdateSave.visibility = View.INVISIBLE
    }

    private fun showText(){
        binding.edtNombres.visibility  = View.VISIBLE
        binding.edtApellidos.visibility = View.VISIBLE
        binding.edtCorreo.visibility = View.VISIBLE
        binding.edtDireccion.visibility = View.VISIBLE
        binding.edtDescripcion.visibility = View.VISIBLE
        binding.edtFoto.visibility = View.VISIBLE
        binding.btnUpdateSave.visibility = View.VISIBLE

    }

    override fun onResume() {
        super.onResume()

        val userId = arguments?.getInt("USER_ID", -1)
        Log.d("MainActivity", "User ID: $userId")
        if (userId != null && userId != -1) {
            viewModel.getUser(userId)
        } else {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



