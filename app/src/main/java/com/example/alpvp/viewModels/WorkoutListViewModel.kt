package com.example.alpvp.viewModels

import android.R
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
import com.example.alpvp.models.GetAllCourseResponse
import com.example.alpvp.models.GetAllCourseUserResponse
import com.example.alpvp.models.GetAllCourseWorkoutsResponse
import com.example.alpvp.models.GetAllWorkoutResponse
import com.example.alpvp.models.GetCourseUserResponse
import com.example.alpvp.models.GetWorkoutResponse
import com.example.alpvp.repositories.CourseUserRepository
import com.example.alpvp.repositories.CourseWorkoutRepository
import com.example.alpvp.repositories.UserRepository
import com.example.alpvp.repositories.WorkoutRepository
import com.example.alpvp.uiStates.CourseDataStatusUIState
import com.example.alpvp.uiStates.CourseDetailDataStatusUIState
import com.example.alpvp.uiStates.CourseUserDataStatesUIStates
import com.example.alpvp.uiStates.CourseUserDetailDataStatusUIState
import com.example.alpvp.uiStates.StringDataStatusUIState
import com.example.alpvp.uiStates.WorkoutCourseDataStatesUIState
import com.example.alpvp.uiStates.WorkoutDataStatesUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class WorkoutListViewModel(
    private val courseWorkoutRepository: CourseWorkoutRepository,
    private val workoutRepository: WorkoutRepository,
    //todo: add private val courseUserRepository for isdone button
    private val courseUserRepository: CourseUserRepository
) : ViewModel() {

//    val course_id: StateFlow<Int> = courseWorkoutRepository.currentUserID.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000),
//        initialValue = 0
//    )

    private val _workoutDataState =
        MutableStateFlow<WorkoutCourseDataStatesUIState>(WorkoutCourseDataStatesUIState.Start)
    val workoutDataState: StateFlow<WorkoutCourseDataStatesUIState> =
        _workoutDataState.asStateFlow()

    var dataStatus: WorkoutCourseDataStatesUIState by mutableStateOf(WorkoutCourseDataStatesUIState.Start)
        private set

    var dataStatusWorkout: WorkoutDataStatesUIState by mutableStateOf(WorkoutDataStatesUIState.Start)
        private set

    var dataStatusCourseUser: CourseUserDataStatesUIStates by mutableStateOf(CourseUserDataStatesUIStates.Start)
    private set

    var detailDataStatusCourseUser: CourseUserDetailDataStatusUIState by mutableStateOf(CourseUserDetailDataStatusUIState.Start)
        private set

    var stringStatus: StringDataStatusUIState by mutableStateOf (StringDataStatusUIState.Start)
        private set

    fun clearDataErrorMessage() {
        dataStatus = WorkoutCourseDataStatesUIState.Start
    }

    fun checkCourseUserisDone(token: String, courseUserId: Int) {
        viewModelScope.launch {
            detailDataStatusCourseUser = CourseUserDetailDataStatusUIState.Loading

            try {
                val call = courseUserRepository.getCourseUser(token = token, courseUserId = courseUserId)
                call.enqueue(object : Callback<GetCourseUserResponse> {
                    override fun onResponse(
                        call: Call<GetCourseUserResponse>,
                        res: Response<GetCourseUserResponse>
                    ) {
                        if (res.isSuccessful) {
                            val courseUserData = res.body()?.data
                            if (courseUserData != null) {
                                detailDataStatusCourseUser = CourseUserDetailDataStatusUIState.Success(courseUserData)

                                if (courseUserData.isDone) {
                                    stringStatus = StringDataStatusUIState.Success("Course is completed!")
                                } else {
                                    stringStatus = StringDataStatusUIState.Success("Course is not completed yet.")
                                }
                            }
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()?.charStream(),
                                ErrorModel::class.java
                            )
                            detailDataStatusCourseUser = CourseUserDetailDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }
                    override fun onFailure(call: Call<GetCourseUserResponse>, t: Throwable) {
                        stringStatus = StringDataStatusUIState.Failed(t.localizedMessage ?: "Unknown error occurred")
                    }
                })
            } catch (error: IOException) {
                stringStatus = StringDataStatusUIState.Failed(error.localizedMessage ?: "Network error occurred")
            }
        }
    }


    fun updateIsDoneCourseUser(token: String, courseId: Int, courseUserId: Int, userId: Int) {
        viewModelScope.launch {
            stringStatus = StringDataStatusUIState.Loading
            Log.d("token-check", "TOKEN: $token")

            try {
                // Setting isDone to true directly
                val call = courseUserRepository.updateCourseUser(
                    token = token,
                    isDone = true,
                    userId = userId,
                    courseId = courseId,
                    courseUserId = courseUserId
                )
                call.enqueue(object : Callback<GeneralResponseModel> {
                    override fun onResponse(
                        call: Call<GeneralResponseModel>,
                        res: Response<GeneralResponseModel>
                    ) {
                        if (res.isSuccessful) {
                            stringStatus = StringDataStatusUIState.Success(res.body()!!.data)
                            Log.d("isDone Setting", "Has been Setted into True")
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


    fun getWorkoutbyCourseId(token: String, courseId: Int, navController: NavHostController,) {
        viewModelScope.launch {
            dataStatus = WorkoutCourseDataStatesUIState.Loading
            try {
                val call = courseWorkoutRepository.getAllCourseWorkoutsByCourseId(token, courseId)
                call.enqueue(
                    object : Callback<GetAllCourseWorkoutsResponse> {
                        override fun onResponse(
                            call: Call<GetAllCourseWorkoutsResponse>,
                            res: Response<GetAllCourseWorkoutsResponse>
                        ) {
                            if (res.isSuccessful) {
                                dataStatus =
                                    WorkoutCourseDataStatesUIState.Success(res.body()!!.data)

                                navController.navigate(PagesEnum.workoutList.name + "/" + courseId) {
                                    popUpTo(PagesEnum.Home.name) {
                                        inclusive = false
                                    }
                                }
//

                            } else {
                                val errorMessage = Gson().fromJson(
                                    res.errorBody()!!.charStream(),
                                    ErrorModel::class.java
                                )
                                dataStatus =
                                    WorkoutCourseDataStatesUIState.Failed(errorMessage.errors)
                            }
                        }

                        override fun onFailure(
                            call: Call<GetAllCourseWorkoutsResponse>,
                            t: Throwable
                        ) {
                            dataStatus = WorkoutCourseDataStatesUIState.Failed(t.localizedMessage)
                        }
                    }
                )
            } catch (error: IOException) {
                dataStatus = WorkoutCourseDataStatesUIState.Failed(error.localizedMessage)
            }

        }
    }

    fun getWorkoutbyCourseIdDuplicate(token: String, courseId: Int) {
        if (dataStatus !is WorkoutCourseDataStatesUIState.Success) {
            viewModelScope.launch {
                dataStatus = WorkoutCourseDataStatesUIState.Loading
                try {
                    val call = courseWorkoutRepository.getAllCourseWorkoutsByCourseId(token, courseId)
                    call.enqueue(
                        object : Callback<GetAllCourseWorkoutsResponse> {
                            override fun onResponse(
                                call: Call<GetAllCourseWorkoutsResponse>,
                                res: Response<GetAllCourseWorkoutsResponse>
                            ) {
                                if (res.isSuccessful) {
                                    dataStatus =
                                        WorkoutCourseDataStatesUIState.Success(res.body()!!.data)
//
                                } else {
                                    val errorMessage = Gson().fromJson(
                                        res.errorBody()!!.charStream(),
                                        ErrorModel::class.java
                                    )
                                    dataStatus =
                                        WorkoutCourseDataStatesUIState.Failed(errorMessage.errors)
                                }
                            }

                            override fun onFailure(
                                call: Call<GetAllCourseWorkoutsResponse>,
                                t: Throwable
                            ) {
                                dataStatus = WorkoutCourseDataStatesUIState.Failed(t.localizedMessage)
                            }
                        }
                    )
                } catch (error: IOException) {
                    dataStatus = WorkoutCourseDataStatesUIState.Failed(error.localizedMessage)
                }

            }
        } else {
            Log.d("WorkoutListViewModel", "Data already successful, skipping API call")
        }
    }

    fun getWorkoutbyWorkoutId(
        token: String,
        workoutId: Int
    ) {
        if (dataStatusWorkout !is WorkoutDataStatesUIState.Success) {
            viewModelScope.launch {
                dataStatusWorkout = WorkoutDataStatesUIState.Loading
                try {
                    val call = workoutRepository.getWorkoutById(token, workoutId)
                    call.enqueue(object : Callback<GetWorkoutResponse> {
                        override fun onResponse(
                            call: Call<GetWorkoutResponse>,
                            res: Response<GetWorkoutResponse>
                        ) {
                            if (res.isSuccessful) {
                                dataStatusWorkout = WorkoutDataStatesUIState.Success(res.body()!!.data)
                            }
                            else {
                                val errorMessage = Gson().fromJson(
                                    res.errorBody()!!.charStream(),
                                    ErrorModel::class.java
                                )
                                dataStatusWorkout = WorkoutDataStatesUIState.Failed(errorMessage.errors)
                            }
                        }

                        override fun onFailure(
                            call: Call<GetWorkoutResponse>,
                            t: Throwable
                        ) {
                            dataStatusWorkout = WorkoutDataStatesUIState.Failed(t.localizedMessage)
                        }
                    })

                } catch (error: IOException) {
                    dataStatusWorkout = WorkoutDataStatesUIState.Failed(error.localizedMessage)
                }

            }
        }
        else {
            Log.d("WorkoutCall", "Data already successful, skipping API call")
        }
    }



    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BoxingApplication)
                val courseWorkoutRepository = application.container.courseWorkoutRepository
                val workoutRepository = application.container.workoutRepository
                val courseUserRepository = application.container.courseUserRepository
                WorkoutListViewModel(courseWorkoutRepository, workoutRepository, courseUserRepository)
            }
        }
    }
}



