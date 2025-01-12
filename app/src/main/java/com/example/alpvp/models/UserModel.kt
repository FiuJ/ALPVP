package com.example.alpvp.models

data class UserResponse(
    val data: UserModel
)

data class UserModel (
    val username: String,
    val token: String?,
    val user_id: Int
)

