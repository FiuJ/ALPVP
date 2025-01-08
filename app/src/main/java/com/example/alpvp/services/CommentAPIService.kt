package com.example.alpvp.services

import com.example.alpvp.models.CommentResponse
import com.example.alpvp.models.GeneralResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentAPIService {
    @POST("api/comments")
    fun createComment(@Header("X-API-TOKEN") token: String, @Body commentMap: HashMap<String, String>): Call<GeneralResponseModel>

    @GET("api/comments/post/{postId}")
    fun getAllCommentsByPostId(@Header("X-API-TOKEN") token: String, @Path("postId") postId: Int): Call<CommentResponse>
}