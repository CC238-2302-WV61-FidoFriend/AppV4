package com.rol.fidofriend_app.data.service

import com.rol.fidofriend_app.model.*
import retrofit2.Call
import retrofit2.http.*

interface FidoFriendApi {

    @GET("/api/User/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    @POST("/api/User/login")
    fun loginUser(@Body requestBody: Login): Call<User>

    @POST("/api/User")
    fun registerUser(@Body requestBody: RegisterLogin): Call<User>

    @PUT("/api/User/{id}")
    fun updateUser(@Path("id") id: Int, @Body user: User): Call<User>

    //@GET("/api/Pet/{id}")
    //fun getPets(@Path("id") id: Int): Call<List<Pet>>

    //@POST("/api/Pet")
    //fun postPet(@Body requestBody: Pet): Call<Pet>
    @POST("/api/Pet")
    fun postPet(@Body requestBody: Pet): Call<Boolean>

    @GET("/api/User/{userId}/pets")
    fun getUserPets(@Path("userId") userId: Int): Call<List<Pet>>

    // ---- PRODUCTS ----
    @GET("/api/Product")
    fun getProducts(): Call<List<Product>>

    @POST("/api/Product")
    fun postProduct(@Body product: Product): Call<Product>

    // ---- USER VETS ----
    @GET("/api/User/vets")
    fun getVets(): Call<List<User>>


    // ---- SERVICES ----
    @GET("/api/Service")
    fun getServices(): Call<List<Service>>

    @POST("/api/Service")
    fun createService(@Body service: Service): Call<Service>

    // ---- MEETING ----
    @GET("/api/Meeting")
    fun getMeetings(): Call<List<Meeting>>

    @GET("/api/Meeting/{id}")
    fun getMeeting(@Path("id") id: Int): Call<Meeting>

    @POST("/api/Meeting")
    fun postMeeting(@Body meeting: Meeting): Call<Meeting>

    @DELETE("/api/Meeting/{id}")
    fun deleteMeeting(@Path("id") id: Int): Call<Void>

    @GET("/api/Meeting/Vet/{vetId}")
    fun getMeetingsByVet(@Path("vetId") vetId: Int): Call<List<Meeting>>

    @GET("/api/Meeting/Owner/{ownerId}")
    fun getMeetingsByOwner(@Path("ownerId") ownerId: Int): Call<List<Meeting>>

}