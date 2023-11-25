package com.rol.fidofriend_app.model

data class Meeting(
    var id: Int = 0,
    var date: String = "",
    var finish: Boolean = false,
    var vetId: Int = 0,
    var clientId: Int = 0

)
