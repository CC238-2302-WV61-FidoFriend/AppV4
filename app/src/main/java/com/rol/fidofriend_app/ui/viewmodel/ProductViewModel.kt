package com.rol.fidofriend_app.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rol.fidofriend_app.data.retrofit.ApiClient
import com.rol.fidofriend_app.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {

    var productStatus: ((List<Product>?) -> Unit)? = null
    var postStatus: ((Product?) -> Unit)? = null

    fun getProducts() {
        val call: Call<List<Product>> = ApiClient.fidoService.getProducts()
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    productStatus?.invoke(response.body())
                } else {
                    productStatus?.invoke(null)
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                productStatus?.invoke(null)
            }
        })
    }

    fun postProduct(product: Product) {
        val call: Call<Product> = ApiClient.fidoService.postProduct(product)
        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    postStatus?.invoke(response.body())
                } else {
                    postStatus?.invoke(null)
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                postStatus?.invoke(null)
            }
        })
    }

    // Lista para almacenar los productos seleccionados
    val selectedProducts = MutableLiveData<MutableList<Product>>(mutableListOf())

    fun isProductInCart(product: Product): Boolean {
        return selectedProducts.value?.contains(product) ?: false
    }

    fun clearProducts() {
        selectedProducts.value?.clear()
    }
}
