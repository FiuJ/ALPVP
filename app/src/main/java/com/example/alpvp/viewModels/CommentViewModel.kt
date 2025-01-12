package com.example.alpvp.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.alpvp.BoxingApplication
import com.example.alpvp.models.CommentModel
import com.example.alpvp.models.CommentResponse
import com.example.alpvp.models.ErrorModel
import com.example.alpvp.models.GetAllPostResponse
import com.example.alpvp.models.PostModel
import com.example.alpvp.repositories.CommentRepository
import com.example.alpvp.repositories.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentViewModel(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository
): ViewModel() {

    val token: StateFlow<String> = userRepository.currentUserToken.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    private val _comment = MutableStateFlow<List<CommentModel>>(emptyList())
    val comment: StateFlow<List<CommentModel>> = _comment

    fun getAllCommentsByPost(postId: Int){
        viewModelScope.launch {
            try {
                val token = token.value
                val response = commentRepository.getAllCommentsByPost(token, postId)

                response.enqueue(object : Callback<CommentResponse> {
                    override fun onResponse(
                        call: Call<CommentResponse>,
                        res: Response<CommentResponse>
                    ){
                        if (res.isSuccessful){
                            val comment = res.body()!!.data
                            _comment.value = comment
                        } else{
                            val errorMessage = Gson().fromJson(
                                res.errorBody()?.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("getPost-error", "ERROR DATA: ${errorMessage}")
                        }
                    }
                    override fun onFailure(p0: Call<CommentResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                    }
                })

            } catch (e: Exception) {
                Log.d("register-error", "REGISTER ERROR: ${e.localizedMessage}")
            }
        }
    }

    suspend fun createComment(token: String, postId: Int, content: String){
        val response = commentRepository.createComment(token, postId, content)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BoxingApplication)
                val commentRepository = application.container.commentRepository
                val userRepository = application.container.userRepository
                CommentViewModel(commentRepository, userRepository)
            }
        }
    }



}