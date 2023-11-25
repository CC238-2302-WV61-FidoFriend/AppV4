package com.rol.fidofriend_app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rol.fidofriend_app.data.retrofit.ApiClient
import com.rol.fidofriend_app.model.Login
import com.rol.fidofriend_app.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {

    var loginStatus: ((User?) -> Unit)? = null

    fun loginUser(user: Login) {
        val call: Call<User> = ApiClient.fidoService.loginUser(user)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Log.d("LoginViewModel", "Inicio de sesión exitoso: ${response.body()}")
                    loginStatus?.invoke(response.body())
                } else {
                    Log.d("LoginViewModel", "Error en el inicio de sesión: ${response.errorBody()?.string()}")
                    loginStatus?.invoke(null)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("LoginViewModel", "Error en el inicio de sesión", t)
                loginStatus?.invoke(null)
            }
        })
    }
}







