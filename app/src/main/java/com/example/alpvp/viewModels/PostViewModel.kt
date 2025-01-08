package com.example.alpvp.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.alpvp.BoxingApplication
import com.example.alpvp.enums.PagesEnum
import com.example.alpvp.models.CommunityModel
import com.example.alpvp.models.CommunityResponse
import com.example.alpvp.models.ErrorModel
import com.example.alpvp.models.GeneralResponseModel
import com.example.alpvp.models.GetAllPostResponse
import com.example.alpvp.models.PostModel
import com.example.alpvp.models.PostUpdateRequest
import com.example.alpvp.repositories.PostRepository
import com.example.alpvp.repositories.UserRepository
import com.example.alpvp.uiStates.CommunityStatusUIState
import com.example.alpvp.uiStates.PostStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.security.PrivateKey
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class PostViewModel (
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
): ViewModel(){

    val username: StateFlow<String> = userRepository.currentUsername.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    val token: StateFlow<String> = userRepository.currentUserToken.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    var uiState: PostStatusUIState by mutableStateOf(PostStatusUIState.Start)
        private set

    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }



    var post_name_input: String by mutableStateOf("")
        private set
    var post_description_input: String by mutableStateOf("")
        private set
//    var post_photo_input: String by mutableStateOf("")
//        private  set
//    var post_date_input: String by mutableStateOf("")
//        private  set
    var isPublicInput: Boolean by mutableStateOf(true)
        private set

    fun setName(name: String) {
        post_name_input = name
    }
    fun setDescription(description: String) {
        post_description_input = description
    }
//    fun setPhoto(photo: String) {
//        post_photo_input = photo
//    }
//    fun setDate(date: String) {
//        post_date_input = date
//    }
    fun setIsPublic(isPublic: Boolean) {
        isPublicInput = isPublic
    }

    fun resetViewModel() {
        setName("")
        setDescription("")
        setIsPublic(false)
    }

    private val _publicPost = MutableStateFlow<List<PostModel>>(emptyList())
    val publicPost: StateFlow<List<PostModel>> = _publicPost

    private val _postById = MutableStateFlow<List<PostModel>>(emptyList())
    val postById: StateFlow<List<PostModel>> = _postById


    //ADD ROUTE KE FORM POST
    fun createPost(token: String, id: Int, navController: NavHostController) {
        uiState = PostStatusUIState.Loading
        viewModelScope.launch {
            try {
                val response = postRepository.createPost(
                    token,
                    post_name = post_name_input,
                    post_description = post_description_input,
                    post_photo = "",
                    post_date = getCurrentDate(),
                    post_likes = 0,
                    user_id = id,
                    isPublic = isPublicInput
                )
                response.enqueue(object : Callback<GeneralResponseModel> {
                    override fun onResponse(
                        call: Call<GeneralResponseModel>,
                        res: Response<GeneralResponseModel>
                    ) {
                        if (res.isSuccessful) {
                            uiState = PostStatusUIState.Success(res.body()!!.data)
                            resetViewModel()
                            navController.navigate(PagesEnum.Community.name){
                                popUpTo(PagesEnum.CreatePost.name){
                                    inclusive = true
                                }
                            }
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: $errorMessage")
                        }
                    }

                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                    }
                })
            } catch (e: Exception) {
                Log.d("error-data", "ERROR DATA: ${e.localizedMessage}")
            }
        }
    }

    fun getAllPostsByUser(token: String, userId: Int) {
        viewModelScope.launch {
            try {
                val response = postRepository.getAllPostsByUser(token, userId)

                response.enqueue(object : Callback<GetAllPostResponse>{
                    override fun onResponse(
                        call: Call<GetAllPostResponse>,
                        res: Response<GetAllPostResponse>
                    ){
                        if (res.isSuccessful){
                            val postById = res.body()!!.data
                            _postById.value = postById
                        } else{
                            val errorMessage = Gson().fromJson(
                                res.errorBody()?.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("getPost-error", "ERROR DATA: ${errorMessage}")
                        }
                    }
                    override fun onFailure(p0: Call<GetAllPostResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                    }
                })

            } catch (e: Exception) {
                Log.d("register-error", "REGISTER ERROR: ${e.localizedMessage}")
            }
        }
    }


    fun getAllPublicPosts(token: String) {
        viewModelScope.launch {
            try {
                val response = postRepository.getAllPublicPosts(token)

                response.enqueue(object : Callback<GetAllPostResponse>{
                    override fun onResponse(
                        call: Call<GetAllPostResponse>,
                        res: Response<GetAllPostResponse>
                    ){
                        if (res.isSuccessful){
                            val postPublic = res.body()!!.data
                            _publicPost.value = postPublic
                        } else{
                            val errorMessage = Gson().fromJson(
                                res.errorBody()?.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("getPost-error", "ERROR DATA: ${errorMessage}")
                        }
                    }
                    override fun onFailure(p0: Call<GetAllPostResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                    }
                })

            } catch (e: Exception) {
                Log.d("register-error", "REGISTER ERROR: ${e.localizedMessage}")
            }
        }
    }
//
//    fun getPostById(postId: Int) {
//        viewModelScope.launch {}
//    }


    fun updatePost(token: String, id: Int, navController: NavHostController) {
        uiState = PostStatusUIState.Loading
        viewModelScope.launch {
            try {
                val response = postRepository.updatePost(
                    token,
                    postId = id,
                    post_name = post_name_input,
                    post_description = post_description_input,
                    post_photo = "",
                    isPublic = isPublicInput
                )
                response.enqueue(object : Callback<GeneralResponseModel> {
                    override fun onResponse(
                        call: Call<GeneralResponseModel>,
                        res: Response<GeneralResponseModel>
                    ) {
                        if (res.isSuccessful) {
                            uiState = PostStatusUIState.Success(res.body()!!.data)
                            resetViewModel()
                            navController.navigate(PagesEnum.Community.name){
                                popUpTo(PagesEnum.CreatePost.name){
                                    inclusive = true
                                }
                            }
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: $errorMessage")
                        }
                    }

                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                    }
                })
            } catch (e: Exception) {
                Log.d("error-data", "ERROR DATA: ${e.localizedMessage}")
            }
        }
    }


    fun deletePost(token: String, postId: Int) {
        uiState = PostStatusUIState.Loading
        viewModelScope.launch {
            try {
                val response = postRepository.deletePost(token, postId)
                response.enqueue(object: Callback<GeneralResponseModel>{
                    override fun onResponse(
                        call: Call<GeneralResponseModel>,
                        res: Response<GeneralResponseModel>
                    ) {
                        if (res.isSuccessful){
                            uiState = PostStatusUIState.Success(res.body()!!.data)
                        }
                        else{
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: $errorMessage")
                        }
                    }
                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                    }
                })
            } catch (e: Exception) {
                uiState = PostStatusUIState.Failed(e.message ?: "Unknown Error")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BoxingApplication)
                val postRepository = application.container.postRepository
                val userRepository = application.container.userRepository
                PostViewModel(postRepository, userRepository)
            }
        }
    }



}