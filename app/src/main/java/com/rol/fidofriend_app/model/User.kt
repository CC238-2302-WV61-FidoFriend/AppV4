package com.rol.fidofriend_app.model

data class User(
    val id: Int,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val dob: String = "",
    val isVet: Boolean = false,
    val address: String = "",
    val description: String = "",
    val imgUrl: String = ""
)
