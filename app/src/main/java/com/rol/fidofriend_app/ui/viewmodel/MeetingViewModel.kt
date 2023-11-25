package com.rol.fidofriend_app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rol.fidofriend_app.data.retrofit.ApiClient
import com.rol.fidofriend_app.model.Meeting
import com.rol.fidofriend_app.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MeetingViewModel : ViewModel() {

    val meeting = MutableLiveData<Meeting>()
    val meetings = MutableLiveData<List<Meeting>>()
    var postStatus: ((Meeting?) -> Unit)? = null

    fun getMeeting(id: Int) {
        val call: Call<Meeting> = ApiClient.fidoService.getMeeting(id)
        call.enqueue(object : Callback<Meeting> {
            override fun onResponse(call: Call<Meeting>, response: Response<Meeting>) {
                if (response.isSuccessful) {
                    meeting.value = response.body()
                }
            }

            override fun onFailure(call: Call<Meeting>, t: Throwable) {
            }
        })
    }

    fun getMeetingsByVet(vetId: Int) {
        val call: Call<List<Meeting>> = ApiClient.fidoService.getMeetingsByVet(vetId)
        call.enqueue(object : Callback<List<Meeting>> {
            override fun onResponse(call: Call<List<Meeting>>, response: Response<List<Meeting>>) {
                if (response.isSuccessful) {
                    meetings.value = response.body()
                    Log.d("MeetingViewModel", "Reuniones obtenidas: ${response.body()}")
                } else {
                    Log.d("MeetingViewModel", "Error al obtener las reuniones: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Meeting>>, t: Throwable) {
                Log.d("MeetingViewModel", "Error al obtener las reuniones", t)
            }
        })
    }


    fun getMeetingsByOwner(ownerId: Int) {
        val call: Call<List<Meeting>> = ApiClient.fidoService.getMeetingsByOwner(ownerId)
        call.enqueue(object : Callback<List<Meeting>> {
            override fun onResponse(call: Call<List<Meeting>>, response: Response<List<Meeting>>) {
                if (response.isSuccessful) {
                    meetings.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<Meeting>>, t: Throwable) {
            }
        })
    }

    fun postMeeting(meeting: Meeting) {
        val call: Call<Meeting> = ApiClient.fidoService.postMeeting(meeting)
        call.enqueue(object : Callback<Meeting> {
            override fun onResponse(call: Call<Meeting>, response: Response<Meeting>) {
                if (response.isSuccessful) {
                    postStatus?.invoke(response.body())
                } else {
                    postStatus?.invoke(null)
                }
            }

            override fun onFailure(call: Call<Meeting>, t: Throwable) {
                postStatus?.invoke(null)
            }
        })
    }

    fun deleteMeeting(id: Int) {
        val call: Call<Void> = ApiClient.fidoService.deleteMeeting(id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // La reunión se eliminó correctamente
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
            }
        })
    }
}

