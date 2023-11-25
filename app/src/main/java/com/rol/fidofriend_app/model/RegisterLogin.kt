package com.rol.fidofriend_app.model

data class RegisterLogin(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",
    var dob: String = "",
    var isVet: Boolean = false
)
