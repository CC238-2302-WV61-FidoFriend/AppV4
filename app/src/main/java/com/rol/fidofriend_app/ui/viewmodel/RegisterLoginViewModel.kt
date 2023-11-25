package com.rol.fidofriend_app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rol.fidofriend_app.data.retrofit.ApiClient
import com.rol.fidofriend_app.model.RegisterLogin
import com.rol.fidofriend_app.model.User
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterLoginViewModel : ViewModel() {

    var registrationStatus: ((Boolean) -> Unit)? = null

    fun registerUser(user: RegisterLogin) {
        val call: Call<User> = ApiClient.fidoService.registerUser(user)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Log.d("RegisterLoginViewModel", "Registro exitoso: ${response.body()}")
                    registrationStatus?.invoke(true)
                } else {
                    val errorBodyStr = response.errorBody()?.string()
                    Log.d("RegisterLoginViewModel", "Error en el registro: $errorBodyStr")
                    registrationStatus?.invoke(false)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("RegisterLoginViewModel", "Error en el registro", t)
                registrationStatus?.invoke(false)
            }
        })
    }
}

