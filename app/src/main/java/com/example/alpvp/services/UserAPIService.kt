package com.example.alpvp.services

import com.example.alpvp.models.GeneralResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserAPIService {
    @POST("api/logout")
    fun logout(@Header("X-API-TOKEN") token: String): Call<GeneralResponseModel>

    @GET("api/user")
    fun getUserIdFromToken(@Header("X-API-TOKEN") token: String): Call<GeneralResponseModel>

    @POST("api/emergency-logout")
    fun emergencyLogout(@Header("X-API-TOKEN") token: String): Call<GeneralResponseModel>


}

