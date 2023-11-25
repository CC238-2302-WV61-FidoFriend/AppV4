package com.rol.fidofriend_app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.rol.fidofriend_app.databinding.ActivityAddPetBinding
import com.rol.fidofriend_app.model.Pet
import com.rol.fidofriend_app.ui.viewmodel.PetViewModel

class AddPetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPetBinding
    private val viewModel: PetViewModel by viewModels()

    // obtener el ID del usuario actual
    private val userId by lazy {
        intent.getIntExtra("USER_ID", 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSavePet.setOnClickListener {
            val name = binding.editNamePet.text.toString()
            val ageText = binding.editAgePet.text.toString()
            val description = binding.editDescriptionPet.text.toString()
            val sex = binding.editSexPet.text.toString()
            val imgUrl = binding.editImagePet.text.toString()

            if (name.isEmpty() || ageText.isEmpty() || description.isEmpty() || sex.isEmpty()) {
                Toast.makeText(this, "A excepción de imagen, todos los campos son obligatorios.", Toast.LENGTH_SHORT).show()
            } else {
                val age = ageText.toInt()
                val pet = Pet(name, age, description, imgUrl, sex, userId)

                viewModel.postStatus = { pet ->
                    if (pet != null) {
                        Toast.makeText(this, "Mascota registrada con éxito.", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "No se pudo registrar la mascota...", Toast.LENGTH_SHORT).show()
                    }
                }

                viewModel.postPet(pet)
            }
        }
    }
}
