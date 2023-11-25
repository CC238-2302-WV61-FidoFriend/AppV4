package com.rol.fidofriend_app.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.rol.fidofriend_app.data.local.entity.FavoritePet
import com.rol.fidofriend_app.databinding.ActivityDetailPetBinding
import com.rol.fidofriend_app.model.Pet
import com.rol.fidofriend_app.ui.viewmodel.FavoriteDetailViewModel

class DetailPetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPetBinding
    private lateinit var favorite: FavoritePet
    private lateinit var viewModel: FavoriteDetailViewModel


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicialización de la propiedad viewModel
        viewModel = ViewModelProvider(this).get(FavoriteDetailViewModel::class.java)

        val pet = intent.getSerializableExtra("pet") as Pet
        favorite = FavoritePet(pet.name, pet.age, pet.description, pet.imgUrl, pet.sex,
            pet.ownerId, pet.isFavorite)

        binding.nameTextViewFav.text = "Nombre: ${pet.name}"
        binding.descriptionTextViewFav.text = "Descripcion: ${pet.description}"
        binding.ageTextViewFav.text = "Edad: ${pet.age}"
        binding.sexTextViewFav.text = "Sexo: ${pet.sex}"

        if(favorite.isFavorite) {
            binding.favButton.visibility = View.GONE
        }
        binding.favButton.setOnClickListener {
            viewModel.addFavorites(favorite, onError = {
                Snackbar.make(binding.root, "Solo se puede agregar una vez!!.", Snackbar.LENGTH_SHORT).show()
            })
            Snackbar.make(binding.root, "La Mascota se agrego a favoritos!!.", Snackbar.LENGTH_SHORT).show()
        }
        binding.favRemove.setOnClickListener {
            viewModel.deleteFavorite(favorite, onError = {
                Snackbar.make(binding.root, "Ya se ha eliminado..", Snackbar.LENGTH_SHORT).show()
            })
            Snackbar.make(binding.root, "Se ha eliminado de favoritos.", Snackbar.LENGTH_SHORT).show()
        }

        Glide.with(this)
            .load(pet.imgUrl)
            .into(binding.imageViewFav)
    }

}