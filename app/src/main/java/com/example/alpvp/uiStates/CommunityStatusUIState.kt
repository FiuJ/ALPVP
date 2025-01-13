package com.example.alpvp.uiStates

import com.example.alpvp.models.CommunityModel

interface CommunityStatusUIState {
//    data class Success(val message: String = "") : CommunityStatusUIState
    data class  Success(val data: List<CommunityModel>) : CommunityStatusUIState
    object Loading : CommunityStatusUIState
    object Start : CommunityStatusUIState
    data class Failed(val errorMessage: String) : CommunityStatusUIState
}