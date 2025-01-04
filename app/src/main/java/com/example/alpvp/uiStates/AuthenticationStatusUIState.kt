package com.example.alpvp.uiStates

import com.example.alpvp.models.UserModel

interface AuthenticationStatusUIState {
    data class Success(val userModelData: UserModel): AuthenticationStatusUIState
    object Loading: AuthenticationStatusUIState
    object Start: AuthenticationStatusUIState
    data class Failed(val errorMessage: String):AuthenticationStatusUIState
}