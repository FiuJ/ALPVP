package com.example.alpvp.uiStates

interface CommunityStatusUIState {
    data class Success(val message: String = "") : CommunityStatusUIState
    object Loading : CommunityStatusUIState
    object Start : CommunityStatusUIState
    data class Failed(val errorMessage: String) : CommunityStatusUIState
}