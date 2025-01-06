package com.example.alpvp.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpvp.enums.PagesEnum
import com.example.alpvp.models.PostModel
import com.example.alpvp.repositories.PostRepository
import com.example.alpvp.repositories.UserRepository
import com.example.alpvp.uiStates.PostStatusUIState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.security.PrivateKey

class PostViewModel (
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
): ViewModel(){
    var uiState: PostStatusUIState by mutableStateOf(PostStatusUIState.Start)
        private set

    private suspend fun getToken(): String {
        return userRepository.currentUserToken.first()
    }

    //ADD ROUTE KE FORM POST
    fun createPost(postModel: PostModel) {
        uiState = PostStatusUIState.Loading
        viewModelScope.launch {
            try {
                val token = getToken()
                val postMap = hashMapOf(
                    "post_name" to postModel.post_name,
                    "post_description" to postModel.post_description,
                    "post_photo" to postModel.post_photo,
                    "post_date" to postModel.post_date,
                    "post_likes" to postModel.post_likes.toString(),
                    "post_isLike" to postModel.post_isLike.toString(),
                    "user_id" to postModel.user_id.toString(),
                    "isPublic" to postModel.isPublic.toString()
                )
                val response = postRepository.createPost(token, postMap).execute()
                if (response.isSuccessful) {
                    uiState = PostStatusUIState.Success(emptyList())
//                    navController.navigate(PagesEnum.Home.name) {
//                        popUpTo(PagesEnum.Login.name) {
//                            inclusive = true
//                        }
//                    }
                } else {
                    uiState = PostStatusUIState.Failed(
                        response.errorBody()?.string() ?: "Unknown Error"
                    )
                }
            } catch (e: Exception) {
                uiState = PostStatusUIState.Failed(e.message ?: "Unknown Error")
            }
        }
    }

    fun getAllPostsByUser(userId: Int) {
        uiState = PostStatusUIState.Loading
        viewModelScope.launch {
            try {
                val token = getToken()
                val response = postRepository.getAllPostsByUser(token, userId).execute()
                if (response.isSuccessful) {
                    response.body()?.let { postResponse ->
                        uiState = PostStatusUIState.Success(postResponse.data)
                    } ?: run {
                        uiState = PostStatusUIState.Failed("No data received")
                    }
                } else {
                    uiState = PostStatusUIState.Failed(
                        response.errorBody()?.string() ?: "Unknown Error"
                    )
                }
            } catch (e: Exception) {
                uiState = PostStatusUIState.Failed(e.message ?: "Unknown Error")
            }
        }
    }

    fun getAllPublicPosts() {
        uiState = PostStatusUIState.Loading
        viewModelScope.launch {
            try {
                val token = getToken()
                val response = postRepository.getAllPublicPosts(token).execute()
                if (response.isSuccessful) {
                    response.body()?.let { postResponse ->
                        uiState = PostStatusUIState.Success(postResponse.data)
                    } ?: run {
                        uiState = PostStatusUIState.Failed("No data received")
                    }
                } else {
                    uiState = PostStatusUIState.Failed(
                        response.errorBody()?.string() ?: "Unknown Error"
                    )
                }
            } catch (e: Exception) {
                uiState = PostStatusUIState.Failed(e.message ?: "Unknown Error")
            }
        }
    }

    fun updatePost(postId: Int, postModel: PostModel) {
        uiState = PostStatusUIState.Loading
        viewModelScope.launch {
            try {
                val token = getToken()
                val postMap = hashMapOf(
                    "post_name" to postModel.post_name,
                    "post_description" to postModel.post_description,
                    "post_photo" to postModel.post_photo,
                    "post_date" to postModel.post_date,
                    "post_likes" to postModel.post_likes.toString(),
                    "post_isLike" to postModel.post_isLike.toString(),
                    "user_id" to postModel.user_id.toString(),
                    "isPublic" to postModel.isPublic.toString()
                )
                val response = postRepository.updatePost(token, postId, postMap).execute()
                if (response.isSuccessful) {
                    uiState = PostStatusUIState.Success(emptyList())
                } else {
                    uiState = PostStatusUIState.Failed(
                        response.errorBody()?.string() ?: "Unknown Error"
                    )
                }
            } catch (e: Exception) {
                uiState = PostStatusUIState.Failed(e.message ?: "Unknown Error")
            }
        }
    }


    fun deletePost(postId: Int) {
        uiState = PostStatusUIState.Loading
        viewModelScope.launch {
            try {
                val token = getToken()
                val response = postRepository.deletePost(token, postId).execute()
                if (response.isSuccessful) {
                    uiState = PostStatusUIState.Success(emptyList())
                } else {
                    uiState = PostStatusUIState.Failed(
                        response.errorBody()?.string() ?: "Unknown Error"
                    )
                }
            } catch (e: Exception) {
                uiState = PostStatusUIState.Failed(e.message ?: "Unknown Error")
            }
        }
    }





}