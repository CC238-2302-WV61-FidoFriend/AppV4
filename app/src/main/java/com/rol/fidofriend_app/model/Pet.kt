package com.rol.fidofriend_app.model

import java.io.Serializable

data class Pet(
    var name: String = "",
    var age: Int = 0,
    var description: String = "",
    var imgUrl: String = "",
    var sex: String = "",
    var ownerId: Int = 0,
    var isFavorite: Boolean = false
): Serializable
