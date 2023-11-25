package com.rol.fidofriend_app.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.rol.fidofriend_app.databinding.ActivityRegisterBinding
import com.rol.fidofriend_app.model.RegisterLogin
import com.rol.fidofriend_app.ui.viewmodel.RegisterLoginViewModel
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterLoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.registrationStatus = { success ->
            if (success) {
                Toast.makeText(this, "Se ha registrado con éxito", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "No se pudo registrar...", Toast.LENGTH_SHORT).show()
            }
        }

        binding.txvLogueate.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        /*binding.btnCreateAccount1.setOnClickListener {
            val now = OffsetDateTime.now()
            val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            val dob = now.format(formatter)

            val user = RegisterLogin(
                firstName = binding.firstNameEditText.text.toString(),
                lastName = binding.lastNameEditText.text.toString(),
                email = binding.userEmailEditText.text.toString(),
                password = binding.passwordEditText.text.toString(),
                dob = dob,
                isVet = binding.isVet.isChecked
            )
            viewModel.registerUser(user)
        }*/
        binding.btnCreateAccount1.setOnClickListener {
            val firstName = binding.firstNameEditText.text.toString()
            val lastName = binding.lastNameEditText.text.toString()
            val email = binding.userEmailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos obligatorios", Toast.LENGTH_SHORT).show()
            } else {
                val now = OffsetDateTime.now()
                val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
                val dob = now.format(formatter)

                val user = RegisterLogin(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password,
                    dob = dob,
                    isVet = binding.isVet.isChecked
                )
                viewModel.registerUser(user)
            }
        }
    }
}
