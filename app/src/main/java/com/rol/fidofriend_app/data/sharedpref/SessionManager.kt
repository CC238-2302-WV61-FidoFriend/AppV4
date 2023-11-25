package com.rol.fidofriend_app.data.sharedpref

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        const val PREFS_NAME = "com.example.app.PREFS"
        const val IS_LOGIN = "IsLoggedIn"
        const val KEY_ID = "id"
        const val IS_VET = "isVet"
    }

    fun createLoginSession(id: Int, isVet: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(IS_LOGIN, true)
        editor.putInt(KEY_ID, id)
        editor.putBoolean(IS_VET, isVet)
        editor.apply()
    }

    val isLoggedIn: Boolean
        get() = prefs.getBoolean(IS_LOGIN, false)

    val userId: Int
        get() = prefs.getInt(KEY_ID, -1)

    val isVet: Boolean
        get() = prefs.getBoolean(IS_VET, false)

    fun logoutUser() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}



