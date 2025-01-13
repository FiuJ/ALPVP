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
import androidx.navigation.NavHostController
import com.example.alpvp.BoxingApplication
import com.example.alpvp.enums.PagesEnum
import com.example.alpvp.models.ErrorModel
import com.example.alpvp.models.GeneralResponseModel
import com.example.alpvp.models.GetCourseResponse
import com.example.alpvp.models.GetCourseUserResponse
import com.example.alpvp.repositories.CourseRepository
import com.example.alpvp.repositories.CourseUserRepository
import com.example.alpvp.repositories.UserRepository
import com.example.alpvp.uiStates.CourseDetailDataStatusUIState
import com.example.alpvp.uiStates.CourseUserDataStatesUIStates
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

class Workout1DetailViewModel(
    private val courseRepository: CourseRepository,
    private val courseUserRepository: CourseUserRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    var dataStatus: CourseDetailDataStatusUIState by mutableStateOf(CourseDetailDataStatusUIState.Start)
        private set
    var dataStatusCourseUser: CourseUserDataStatesUIStates by mutableStateOf(
        CourseUserDataStatesUIStates.Start
    )
        private set
    var stringStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)




    fun createCourseUser(token: String, isDone: Boolean, courseId: Int, userId: Int) {
        viewModelScope.launch {
            stringStatus = StringDataStatusUIState.Loading
            Log.d("token-check", "TOKEN: ${token}")

            try {
                val call = courseUserRepository.createCourseUser(token = token, isDone = isDone, userId = userId, courseId = courseId)
                call.enqueue(object : Callback<GeneralResponseModel> {
                    override fun onResponse(
                        call: Call<GeneralResponseModel>,
                        res: Response<GeneralResponseModel>
                    ) {
                        if (res.isSuccessful) {
                            stringStatus = StringDataStatusUIState.Success(res.body()!!.data)
                            Log.d("Cek Sukses", stringStatus.toString())
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()?.charStream(),
                                ErrorModel::class.java
                            )
                            stringStatus = StringDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        stringStatus = StringDataStatusUIState.Failed(t.localizedMessage ?: "Unknown error occurred")
                    }
                })
            } catch (error: IOException) {
                stringStatus = StringDataStatusUIState.Failed(error.localizedMessage ?: "Network error occurred")
            }
        }
    }


    fun getCourse(
        token: String,
        courseId: Int,
        navController: NavHostController,
        isUpdating: Boolean
    ) {
        viewModelScope.launch {
            dataStatus = CourseDetailDataStatusUIState.Loading

            try {
                val call = courseRepository.getCourse(token, courseId)
                call.enqueue(object : Callback<GetCourseResponse> {
                    override fun onResponse(
                        call: Call<GetCourseResponse>,
                        res: Response<GetCourseResponse>
                    ) {
                        if (res.isSuccessful) {
                            Log.d(
                                "Workout1DetailView",
                                "Response received, updating dataStatus to Success"
                            )
                            dataStatus = CourseDetailDataStatusUIState.Success(res.body()!!.data)
                            Log.d(
                                "ON Response",
                                "dataStatus after successful response: ${dataStatus}"
                            )

                            navController.navigate(PagesEnum.CourseDetail.name + "/" + courseId) {
                                popUpTo(PagesEnum.Home.name) {
                                    inclusive = false
                                }
                            }

                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            dataStatus = CourseDetailDataStatusUIState.Failed(errorMessage.errors)
                            Log.d("Workout1Detail", "dataStatus: $dataStatus")
                        }
                    }

                    override fun onFailure(call: Call<GetCourseResponse>, t: Throwable) {
                        dataStatus = CourseDetailDataStatusUIState.Failed(t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                dataStatus = CourseDetailDataStatusUIState.Failed(error.localizedMessage)
            }

        }
    }

    fun getCourse1(token: String, courseId: Int, isUpdating: Boolean) {
        viewModelScope.launch {
            // Only update the state and make the call if dataStatus is not already successful
            if (dataStatus !is CourseDetailDataStatusUIState.Success) {
                dataStatus = CourseDetailDataStatusUIState.Loading

                try {
                    val call = courseRepository.getCourse(token, courseId)
                    call.enqueue(object : Callback<GetCourseResponse> {
                        override fun onResponse(
                            call: Call<GetCourseResponse>,
                            res: Response<GetCourseResponse>
                        ) {
                            if (res.isSuccessful) {
                                Log.d(
                                    "Workout1DetailView",
                                    "Response received, updating dataStatus to Success"
                                )
                                dataStatus =
                                    CourseDetailDataStatusUIState.Success(res.body()!!.data)
                                Log.d(
                                    "ON Response",
                                    "dataStatus after successful response: $dataStatus"
                                )

                            } else {
                                val errorMessage = Gson().fromJson(
                                    res.errorBody()!!.charStream(),
                                    ErrorModel::class.java
                                )
                                dataStatus =
                                    CourseDetailDataStatusUIState.Failed(errorMessage.errors)
                                Log.d("Workout1Detail", "dataStatus: $dataStatus")
                            }
                        }

                        override fun onFailure(call: Call<GetCourseResponse>, t: Throwable) {
                            dataStatus = CourseDetailDataStatusUIState.Failed(t.localizedMessage)
                        }
                    })
                } catch (error: IOException) {
                    dataStatus = CourseDetailDataStatusUIState.Failed(error.localizedMessage)
                }
            } else {
                Log.d("Workout1DetailView", "Data already successful, skipping API call")
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BoxingApplication)
                val courseRepository = application.container.courseRepository
                val courseUserRepository = application.container.courseUserRepository
                val userRepository = application.container.userRepository
                Workout1DetailViewModel(courseRepository, courseUserRepository, userRepository)
            }
        }
    }

    fun clearErrorMessage() {
        dataStatus = CourseDetailDataStatusUIState.Start
    }


}