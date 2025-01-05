package com.example.alpvp.services

import com.example.alpvp.models.CommunityResponse
import com.example.alpvp.models.GeneralResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CommunityAPIService {

    @POST("api/community")
    fun createCommunity(@Header("X-API-TOKEN") token: String, @Body communityMap: HashMap<String, String>): Call<GeneralResponseModel>

    @GET("api/community")
    fun getAllCommunities(@Header("X-API-TOKEN") token: String): Call<CommunityResponse>

    @GET("api/community/user/{userId}")
    fun getAllCommunitiesByUserId(@Header("X-API-TOKEN") token: String, @Path("userId") userId: Int): Call<CommunityResponse>

}