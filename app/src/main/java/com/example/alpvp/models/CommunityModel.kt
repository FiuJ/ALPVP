package com.example.alpvp.models



data class CommunityResponse(
    val data: List<CommunityModel>
)

data class CommunityModel(
    val community_id: Int,
    val name: String,
    val description: String,
    val photo: String
)