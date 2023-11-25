package com.rol.fidofriend_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.rol.fidofriend_app.data.retrofit.ApiClient
import com.rol.fidofriend_app.model.Pet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PetViewModel : ViewModel() {

    var petStatus: ((List<Pet>?) -> Unit)? = null
    var postStatus: ((Pet?) -> Unit)? = null

    fun getUserPets(userId: Int) {
        val call: Call<List<Pet>> = ApiClient.fidoService.getUserPets(userId)
        call.enqueue(object : Callback<List<Pet>> {
            override fun onResponse(call: Call<List<Pet>>, response: Response<List<Pet>>) {
                if (response.isSuccessful) {
                    petStatus?.invoke(response.body())
                } else {
                    petStatus?.invoke(null)
                }
            }

            override fun onFailure(call: Call<List<Pet>>, t: Throwable) {
                petStatus?.invoke(null)
            }
        })
    }

    fun postPet(pet: Pet) {
        val call: Call<Boolean> = ApiClient.fidoService.postPet(pet)
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful && response.body() == true) {
                    postStatus?.invoke(pet)
                } else {
                    postStatus?.invoke(null)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                postStatus?.invoke(null)
            }
        })
    }
}
