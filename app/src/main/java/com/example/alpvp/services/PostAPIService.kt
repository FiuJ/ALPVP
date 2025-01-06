package com.example.alpvp.services

import com.example.alpvp.models.GeneralResponseModel
import com.example.alpvp.models.GetAllPostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PostAPIService {
    @POST("api/posts")
    fun createPost(@Header("X-API-TOKEN") token: String, @Body postMap: HashMap<String, String>): Call<GeneralResponseModel>

    @PUT("api/posts/{postId}")
    fun updatePost(@Header("X-API-TOKEN") token: String, @Path("postId") postId: Int, @Body postMap: HashMap<String, String>): Call<GeneralResponseModel>

    @DELETE("api/posts/{postId}")fun deletePost(@Header("X-API-TOKEN") token: String, @Path("postId") postId: Int): Call<GeneralResponseModel>

    @GET("api/posts")fun getAllPostsIsPublic(@Header("X-API-TOKEN") token: String): Call<GetAllPostResponse>

    @GET("api/posts/user/{userId}")fun getAllPostsByUser(@Header("X-API-TOKEN") token: String, @Path("userId") userId: Int): Call<GetAllPostResponse>
}