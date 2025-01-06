package com.example.alpvp.viewModels

import android.util.Log
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
import com.example.alpvp.models.ErrorModel
import com.example.alpvp.models.GetAllCourseResponse
import com.example.alpvp.repositories.CourseRepository
import com.example.alpvp.repositories.UserRepository
import com.example.alpvp.uiStates.CourseDataStatusUIState
import com.example.alpvp.uiStates.StringDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class Workout1ViewModel(
    private  val userRepository: UserRepository,
    private val courseRepository: CourseRepository
): ViewModel() {

    fun saveUsernameToken(token: String, username: String) {
        viewModelScope.launch {
            userRepository.saveUserToken(token)
            userRepository.saveUsername(username)
        }
    }

    val username: StateFlow<String> = userRepository.currentUsername.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    val id: StateFlow<Int> = userRepository.currentUserId.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    val token: StateFlow<String> = userRepository.currentUserToken.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    var dataStatus: CourseDataStatusUIState by mutableStateOf(CourseDataStatusUIState.Start)
        private set

    fun clearDataErrorMessage() {
        dataStatus = CourseDataStatusUIState.Start
    }

    fun getAllCourses(token: String) {
        viewModelScope.launch {
            Log.d("token-home", "TOKEN AT HOME: ${token}")

            dataStatus = CourseDataStatusUIState.Loading

            try {
                val call = courseRepository.getAllCourses(token)
                call.enqueue(object : Callback<GetAllCourseResponse> {
                    override fun onResponse(call: Call<GetAllCourseResponse>, res: Response<GetAllCourseResponse>) {
                        if (res.isSuccessful) {
                            dataStatus = CourseDataStatusUIState.Success(res.body()!!.data)

                            Log.d("data-result", "COURSE DATA: ${dataStatus}")
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            dataStatus = CourseDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetAllCourseResponse>, t: Throwable) {
                        dataStatus = CourseDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dataStatus = CourseDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BoxingApplication)
                val userRepository = application.container.userRepository
                val courseRepository = application.container.courseRepository
                Workout1ViewModel(userRepository, courseRepository)
            }
        }
    }


}