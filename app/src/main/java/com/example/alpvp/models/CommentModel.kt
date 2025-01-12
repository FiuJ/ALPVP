package com.example.alpvp.models

data class CommentResponse(
    val data: List<CommentModel>
)

data class CommentModel(
    val comment_id: Int,
    val comment: String,
    val comment_date: String,
    val user_id: Int,
    val username: String,
    val post_id: Int
)

data class CommentCreateRequest(
    val comment: String,
    val comment_date: String,
    val post_id: Int
)