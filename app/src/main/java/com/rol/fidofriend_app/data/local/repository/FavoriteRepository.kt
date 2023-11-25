package com.rol.fidofriend_app.data.local.repository

import com.rol.fidofriend_app.data.local.db.FavoriteDao
import com.rol.fidofriend_app.data.local.db.FavoriteDatabase
import com.rol.fidofriend_app.data.local.entity.FavoritePet

class FavoriteRepository(val db: FavoriteDatabase? = null) {

    private val dao: FavoriteDao? = db?.favoriteDao()

    // Room SQLite
    suspend fun getFavorites() : List<FavoritePet> {
        dao?.let {return dao.getFavorites()
        } ?: kotlin.run {
            return listOf()
        }
    }


    suspend fun addFavorites(fav: FavoritePet,  onError: () -> Unit) {
        if (dao?.isFavorite(fav.ownerId)?.isEmpty() == true) {
            dao.addFavoriteToFavorite(fav)
        } else {
            onError()
        }
    }

    suspend fun isFavorite(id: Int): Boolean {
        return dao?.isFavorite(id)?.isNotEmpty() ?: false
    }

    //borrar
    suspend fun deleteFavorite(fav: FavoritePet, onError: () -> Unit) {
        if (dao?.isFavorite(fav.ownerId)?.isEmpty() != true) {
            dao?.deleteFavorite(fav)
        } else  {
            onError()
        }
    }

}