package com.example.alpvp.repositories

import com.example.alpvp.models.CommunityResponse
import com.example.alpvp.models.GeneralResponseModel
import com.example.alpvp.services.CommunityAPIService
import retrofit2.Call

interface CommunityRepository {
    fun createCommunity(token: String, name: String, description: String, photo: String): Call<GeneralResponseModel>
    fun getAllCommunities(token: String): Call<CommunityResponse>
    fun getAllCommunitiesByUserId(token: String, userId: Int): Call<CommunityResponse>
}

class NetworkCommunityRepository(
    private val communityAPIService: CommunityAPIService
): CommunityRepository {
    override fun createCommunity(token: String, name: String, description: String, photo: String): Call<GeneralResponseModel> {
        val communityMap = HashMap<String, String>()
        communityMap["name"] = name
        communityMap["description"] = description
        communityMap["photo"] = photo

        return communityAPIService.createCommunity(token, communityMap)
    }

    override fun getAllCommunities(token: String): Call<CommunityResponse> {
        return communityAPIService.getAllCommunities(token)
    }

    override fun getAllCommunitiesByUserId(token: String, userId: Int): Call<CommunityResponse> {
        return communityAPIService.getAllCommunitiesByUserId(token, userId)
    }
}