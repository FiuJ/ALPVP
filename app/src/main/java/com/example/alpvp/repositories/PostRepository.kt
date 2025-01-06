package com.example.alpvp.repositories

import com.example.alpvp.models.GeneralResponseModel
import com.example.alpvp.models.GetAllPostResponse
import com.example.alpvp.services.PostAPIService
import retrofit2.Call

interface PostRepository {
    fun createPost(token: String, postMap: HashMap<String, String>): Call<GeneralResponseModel>
    fun updatePost(token: String, postId: Int, postMap: HashMap<String, String>): Call<GeneralResponseModel>
    fun deletePost(token: String, postId: Int): Call<GeneralResponseModel>
    fun getAllPublicPosts(token: String): Call<GetAllPostResponse>
    fun getAllPostsByUser(token: String, userId: Int): Call<GetAllPostResponse>
}


class NetworkPostRepository(
    private val postAPIService: PostAPIService
) : PostRepository {

    override fun createPost(token: String, postMap: HashMap<String, String>): Call<GeneralResponseModel> {
        return postAPIService.createPost(token, postMap)
    }

    override fun updatePost(token: String, postId: Int, postMap: HashMap<String, String>): Call<GeneralResponseModel> {
        return postAPIService.updatePost(token, postId, postMap)
    }

    override fun deletePost(token: String, postId: Int): Call<GeneralResponseModel> {
        return postAPIService.deletePost(token, postId)
    }

    override fun getAllPublicPosts(token: String): Call<GetAllPostResponse> {
        return postAPIService.getAllPostsIsPublic(token)
    }

    override fun getAllPostsByUser(token: String, userId: Int): Call<GetAllPostResponse> {
        return postAPIService.getAllPostsByUser(token, userId)
    }
}
