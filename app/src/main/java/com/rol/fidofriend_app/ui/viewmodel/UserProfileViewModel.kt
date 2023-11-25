package com.rol.fidofriend_app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rol.fidofriend_app.data.retrofit.ApiClient
import com.rol.fidofriend_app.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileViewModel : ViewModel() {

    var userProfile: ((User?) -> Unit)? = null
    val vets = MutableLiveData<List<User>>()

    fun getUser(id: Int) {
        val call: Call<User> = ApiClient.fidoService.getUserById(id)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.d("UserProfileViewModel", "Response: $response")
                if (response.isSuccessful) {
                    Log.d("UserProfileViewModel", "Obtención de usuario exitosa: ${response.body()}")
                    userProfile?.invoke(response.body())
                } else {
                    Log.d("UserProfileViewModel", "Error al obtener el usuario: ${response.errorBody()?.string()}")
                    userProfile?.invoke(null)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("ProfileViewModel", "Error al obtener el usuario", t)
                userProfile?.invoke(null)
            }
        })
    }

    fun getVets() {
        val call: Call<List<User>> = ApiClient.fidoService.getVets()
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    vets.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
            }
        })
    }

    fun updateUser(user: User) {
        val call: Call<User> = ApiClient.fidoService.updateUser(user.id, user)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Log.d("UserProfileViewModel", "Actualización de usuario exitosa: ${response.body()}")
                    // Llama a getUser para obtener los datos actualizados del usuario
                    getUser(user.id)
                } else {
                    Log.d("UserProfileViewModel", "Error al actualizar el usuario: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("UserProfileViewModel", "Error al actualizar el usuario", t)
            }
        })
    }



}