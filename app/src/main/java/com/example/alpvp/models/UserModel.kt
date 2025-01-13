package com.example.alpvp.models

data class UserResponse(
    val data: UserModel
)

data class UserModel (
    val id: Int,
    val username: String,
    val token: String?,
    val user_id: Int
)

