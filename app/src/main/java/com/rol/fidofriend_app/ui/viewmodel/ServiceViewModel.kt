package com.rol.fidofriend_app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rol.fidofriend_app.data.retrofit.ApiClient
import com.rol.fidofriend_app.model.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceViewModel : ViewModel() {

    val services = MutableLiveData<List<Service>>()

    fun getServices() {
        val call: Call<List<Service>> = ApiClient.fidoService.getServices()
        call.enqueue(object : Callback<List<Service>> {
            override fun onResponse(call: Call<List<Service>>, response: Response<List<Service>>) {
                if (response.isSuccessful) {
                    services.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<Service>>, t: Throwable) {
            }
        })
    }

    fun createService(service: Service) {
        val call: Call<Service> = ApiClient.fidoService.createService(service)
        call.enqueue(object : Callback<Service> {
            override fun onResponse(call: Call<Service>, response: Response<Service>) {
                if (response.isSuccessful) {
                    // Aquí puedes hacer lo que necesites con el servicio creado
                }
            }

            override fun onFailure(call: Call<Service>, t: Throwable) {
            }
        })
    }

    fun getServicesByName(name: String) {
        val call: Call<List<Service>> = ApiClient.fidoService.getServices()
        call.enqueue(object : Callback<List<Service>> {
            override fun onResponse(call: Call<List<Service>>, response: Response<List<Service>>) {
                if (response.isSuccessful) {
                    val services = response.body()
                    val filteredServices = services?.filter { service -> service.name == name }
                    this@ServiceViewModel.services.value = filteredServices
                }
            }

            override fun onFailure(call: Call<List<Service>>, t: Throwable) {
            }
        })
    }

    // Lista para almacenar los servicios seleccionados
    val selectedServices = MutableLiveData<MutableList<Service>>(mutableListOf())

    fun isServiceInCartS(service: Service): Boolean {
        return selectedServices.value?.contains(service) ?: false
    }

    fun clearServices() {
        selectedServices.value?.clear()
    }
}
