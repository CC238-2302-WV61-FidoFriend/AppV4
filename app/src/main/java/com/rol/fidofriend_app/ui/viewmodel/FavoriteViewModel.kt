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

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : FavoriteRepository
    private var _favorites: MutableLiveData<List<FavoritePet>> = MutableLiveData()

    var favorites: LiveData<List<FavoritePet>> = _favorites

    init {
        val db = FavoriteDatabase.getDatabase(application)
        repository = FavoriteRepository(db)
    }

    fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getFavorites()
            _favorites.postValue(data)
        }
    }
}