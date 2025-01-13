package com.example.alpvp.uiStates

import com.example.alpvp.models.GeneralResponseModel
import com.example.alpvp.models.PostModel

interface PostStatusUIState {
    data class Success(val postData: String) : PostStatusUIState
    object Loading : PostStatusUIState
    object Start : PostStatusUIState
    data class Failed(val errorMessage: String) : PostStatusUIState
}