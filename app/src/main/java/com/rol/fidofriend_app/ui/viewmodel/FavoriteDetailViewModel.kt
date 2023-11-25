package com.rol.fidofriend_app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rol.fidofriend_app.data.local.db.FavoriteDatabase
import com.rol.fidofriend_app.data.local.entity.FavoritePet
import com.rol.fidofriend_app.data.local.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteDetailViewModel(application: Application): AndroidViewModel(application) {
    private val repository : FavoriteRepository

    init {
        val db = FavoriteDatabase.getDatabase(application)
        repository = FavoriteRepository(db)
    }

    fun addFavorites(note: FavoritePet,  onError: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavorites(note, onError)
        }
    }

    fun isFavorite(id: Int): LiveData<Boolean> {
        val isFavorite = MutableLiveData<Boolean>()
        viewModelScope.launch {
            isFavorite.value = repository.isFavorite(id)
        }
        return isFavorite
    }

    //borrar
    fun deleteFavorite(fav: FavoritePet,  onError: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(fav, onError)
        }
    }
}