package com.rol.fidofriend_app.data.sharedpref

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class ThemeManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        const val PREFS_NAME = "com.example.app.PREFS"
        const val DARK_MODE = "DarkMode"
    }

    var isDarkMode: Boolean
        get() = prefs.getBoolean(DARK_MODE, false)
        set(value) {
            val editor = prefs.edit()
            editor.putBoolean(DARK_MODE, value)
            editor.apply()
        }

    fun applyTheme() {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}

