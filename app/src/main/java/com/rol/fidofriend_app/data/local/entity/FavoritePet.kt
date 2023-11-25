package com.rol.fidofriend_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite")
data class FavoritePet(
    @PrimaryKey
    val name: String,
    val age: Int,
    val description: String,
    val imgUrl: String,
    val sex: String,
    val ownerId: Int,
    var isFavorite: Boolean = false
)
