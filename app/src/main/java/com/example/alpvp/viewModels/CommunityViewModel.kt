package com.example.alpvp.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.alpvp.BoxingApplication
import com.example.alpvp.models.CommunityModel
import com.example.alpvp.models.CommunityResponse
import com.example.alpvp.models.ErrorModel
import com.example.alpvp.repositories.CommunityRepository
import com.example.alpvp.repositories.UserRepository
import com.example.alpvp.uiStates.CommunityStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityViewModel(
    private val communityRepository: CommunityRepository,
    private val userRepository: UserRepository
): ViewModel() {

    var uiState: CommunityStatusUIState by mutableStateOf(CommunityStatusUIState.Start)
        private set

    private val _communities = MutableStateFlow<List<CommunityModel>>(emptyList())
    val communities: StateFlow<List<CommunityModel>> = _communities.asStateFlow()

//    var nameInput by mutableStateOf("")
//        private set
//
//    var descriptionInput by mutableStateOf("")
//        private set
//
//    var photoInput by mutableStateOf("")
//        private set
//
//    fun changeNameInput(name: String) {
//        nameInput = name
//    }
//
//    fun changeDescriptionInput(description: String) {
//        descriptionInput = description
//    }
//
//    fun changePhotoInput(photo: String) {
//        photoInput = photo
//    }

//    fun resetForm() {
//        nameInput = ""
//        descriptionInput = ""
//        photoInput = ""
//    }

//    fun getAllCommunities() {
//        viewModelScope.launch {
//            uiState = CommunityStatusUIState.Loading
//            try {
//                val token = userRepository.
//                val call = communityRepository.getAllCommunities(token)
//
//                call.enqueue(object : Callback<CommunityResponse> {
//                    override fun onResponse(
//                        call: Call<CommunityResponse>,
//                        response: Response<CommunityResponse>
//                    ) {
//                        if (response.isSuccessful) {
//                            _communities.value = response.body()?.data ?: emptyList()
//                            uiState = CommunityStatusUIState.Success()
//                        } else {
//                            val errorMessage = Gson().fromJson(
//                                response.errorBody()?.charStream(),
//                                ErrorModel::class.java
//                            )
//                            uiState = CommunityStatusUIState.Failed(errorMessage.errors)
//                        }
//                    }
//
//                    override fun onFailure(call: Call<CommunityResponse>, t: Throwable) {
//                        uiState = CommunityStatusUIState.Failed(t.localizedMessage ?: "Unknown error")
//                    }
//                })
//            } catch (e: Exception) {
//                uiState = CommunityStatusUIState.Failed(e.localizedMessage ?: "Unknown error")
//            }
//        }
//    }

    fun getAllCommunities() {
        viewModelScope.launch {
            uiState = CommunityStatusUIState.Loading
            try {
                // Collect the token from the `UserRepository`
                userRepository.currentUserToken.collect { token ->
                    // Use the token in the API call
                    val call = communityRepository.getAllCommunities(token)
                    call.enqueue(object : Callback<CommunityResponse> {
                        override fun onResponse(
                            call: Call<CommunityResponse>,
                            response: Response<CommunityResponse>
                        ) {
                            if (response.isSuccessful) {
                                val communityData = response.body()?.data ?: emptyList()
                                _communities.value = communityData
                                uiState = CommunityStatusUIState.Success()
                            } else {
                                val errorMessage = Gson().fromJson(
                                    response.errorBody()?.charStream(),
                                    ErrorModel::class.java
                                )
                                uiState = CommunityStatusUIState.Failed(errorMessage.errors)
                            }
                        }

                        override fun onFailure(call: Call<CommunityResponse>, t: Throwable) {
                            uiState = CommunityStatusUIState.Failed(t.localizedMessage ?: "Unknown error")
                        }
                    })
                }
            } catch (e: Exception) {
                uiState = CommunityStatusUIState.Failed(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun getAllCommunitiesByUserId(userId: Int) {
        viewModelScope.launch {
            uiState = CommunityStatusUIState.Loading
            try {
                // Collect the token from the `UserRepository`
                userRepository.currentUserToken.collect { token ->
                    // Use the token and userId in the API call
                    val call = communityRepository.getAllCommunitiesByUserId(token, userId)

                    call.enqueue(object : Callback<CommunityResponse> {
                        override fun onResponse(
                            call: Call<CommunityResponse>,
                            response: Response<CommunityResponse>
                        ) {
                            if (response.isSuccessful) {
                                // Update the list of communities in LiveData
                                val communityData = response.body()?.data ?: emptyList()
                                _communities.value = communityData
                                uiState = CommunityStatusUIState.Success()
                            } else {
                                // Parse the error response
                                val errorMessage = Gson().fromJson(
                                    response.errorBody()?.charStream(),
                                    ErrorModel::class.java
                                )
                                uiState = CommunityStatusUIState.Failed(errorMessage.errors)
                            }
                        }

                        override fun onFailure(call: Call<CommunityResponse>, t: Throwable) {
                            // Handle network or server failure
                            uiState = CommunityStatusUIState.Failed(t.localizedMessage ?: "Unknown error")
                        }
                    })
                }
            } catch (e: Exception) {
                // Handle unexpected exceptions
                uiState = CommunityStatusUIState.Failed(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BoxingApplication)
                val communityRepository = application.container.communityRepository
                val userRepository = application.container.userRepository
                CommunityViewModel(communityRepository, userRepository)
            }
        }
    }



}