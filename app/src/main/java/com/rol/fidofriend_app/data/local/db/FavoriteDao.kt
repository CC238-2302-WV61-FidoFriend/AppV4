package com.rol.fidofriend_app.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rol.fidofriend_app.data.local.entity.FavoritePet

@Dao
interface FavoriteDao {

    @Insert
    fun addFavoriteToFavorite(favorite: FavoritePet)

    @Query("SELECT * FROM favorite")
    fun getFavorites() : List<FavoritePet>

    @Query("SELECT * FROM favorite WHERE ownerId = :id")
    fun isFavorite(id: Int): List<FavoritePet>

    @Delete
    fun deleteFavorite(favorite: FavoritePet)
}