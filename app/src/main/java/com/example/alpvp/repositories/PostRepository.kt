package com.example.alpvp.repositories

import com.example.alpvp.models.GeneralResponseModel
import com.example.alpvp.models.GetAllPostResponse
import com.example.alpvp.models.PostRequest
import com.example.alpvp.models.PostUpdateRequest
import com.example.alpvp.services.PostAPIService
import retrofit2.Call

interface PostRepository {
    fun createPost(token: String, post_name: String, post_description: String, post_photo: String, post_date: String, post_likes: Int, user_id: Int, isPublic: Boolean): Call<GeneralResponseModel>
    fun updatePost(token: String, postId: Int, post_name: String, post_description: String, post_photo: String, isPublic: Boolean): Call<GeneralResponseModel>
    fun deletePost(token: String, postId: Int): Call<GeneralResponseModel>
    fun getAllPublicPosts(token: String): Call<GetAllPostResponse>
    fun getAllPostsByUser(token: String, userId: Int): Call<GetAllPostResponse>
}


class NetworkPostRepository(
    private val postAPIService: PostAPIService
) : PostRepository {

    override fun createPost(token: String, post_name: String, post_description: String, post_photo: String, post_date: String, post_likes: Int, user_id: Int, isPublic: Boolean): Call<GeneralResponseModel> {
        return postAPIService.createPost(token, PostRequest(post_name, post_description, post_photo, post_date, post_likes, user_id, isPublic))
    }

    override fun updatePost(token: String, postId: Int, post_name: String, post_description: String, post_photo: String, isPublic: Boolean): Call<GeneralResponseModel> {
        return postAPIService.updatePost(token, postId, PostUpdateRequest(post_name, post_description, post_photo, isPublic))
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
