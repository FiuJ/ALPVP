package com.example.alpvp.services

import com.example.alpvp.models.GeneralResponseModel
import com.example.alpvp.models.GetAllPostResponse
import com.example.alpvp.models.PostRequest
import com.example.alpvp.models.PostUpdateRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PostAPIService {
    @POST("api/post")
    fun createPost(@Header("X-API-TOKEN") token: String, @Body postRequest: PostRequest): Call<GeneralResponseModel>

    @PUT("api/post/{postId}")
    fun updatePost(@Header("X-API-TOKEN") token: String, @Path("postId") postId: Int, @Body postUpdateRequest: PostUpdateRequest): Call<GeneralResponseModel>

    @DELETE("api/post/{postId}")fun deletePost(@Header("X-API-TOKEN") token: String, @Path("postId") postId: Int): Call<GeneralResponseModel>

    @GET("api/public")fun getAllPostsIsPublic(@Header("X-API-TOKEN") token: String): Call<GetAllPostResponse>

    @GET("api/post/user/{userId}")fun getAllPostsByUser(@Header("X-API-TOKEN") token: String, @Path("userId") userId: Int): Call<GetAllPostResponse>

}