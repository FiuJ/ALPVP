package com.example.alpvp.models

data class GetAllPostResponse(
    val data: List<PostModel>
)

data class GetPostResponse(
    val data: PostModel
)

data class PostModel(
    val post_id: Int = 0,
    val post_name: String = "",
    val post_description: String = "",
    val post_photo: String = "",
    val post_date: String = "",
    val post_likes: Int = 0,
    val user_id: Int = 0,
    val isPublic: Boolean = false
)

data class PostRequest(
    val post_name: String = "",
    val post_description: String = "",
    val post_photo: String = "",
    val post_date: String = "",
    val post_likes: Int = 0,
    val user_id: Int = 0,
    val isPublic: Boolean = false
)

data class PostUpdateRequest(
    val post_name: String = "",
    val post_description: String = "",
    val post_photo: String = "",
    val isPublic: Boolean = false
)