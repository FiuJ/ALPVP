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
import com.example.alpvp.models.GetWorkoutResponse
import com.example.alpvp.repositories.WorkoutRepository
import com.example.alpvp.uiStates.CourseDetailDataStatusUIState
import com.example.alpvp.uiStates.WorkoutDataStatesUIState
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class DetailWorkoutViewModel(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    var dataStatusWorkout: WorkoutDataStatesUIState by mutableStateOf(WorkoutDataStatesUIState.Start)
        private set

    fun clearErrorMessage() {
        dataStatusWorkout = WorkoutDataStatesUIState.Start
    }

    var remainingTime by mutableStateOf(0) // Timer state
        private set

    var isTimerRunning by mutableStateOf(false)
        private set

    fun startTimer(durationMinutes: Int) {
        if (!isTimerRunning) {
            remainingTime = durationMinutes * 60 // Convert minutes to seconds
            isTimerRunning = true
            viewModelScope.launch {
                while (remainingTime > 0 && isTimerRunning) {
                    delay(1000L)
                    remainingTime--
                }
                if (remainingTime <= 0) {
                    remainingTime = 0 // Ensure it doesn't show negative time
                }
                isTimerRunning = false
            }
        }
    }


    fun stopTimer() {
        isTimerRunning = false
        remainingTime = 0
    }


    fun getWorkoutbyWorkoutId(
        token: String,
        navController: NavHostController,
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
                                dataStatusWorkout =
                                    WorkoutDataStatesUIState.Success(res.body()!!.data)

                                navController.navigate(PagesEnum.workoutDetail.name + "/" + workoutId) {
                                    popUpTo(PagesEnum.Home.name) {
                                        inclusive = false
                                    }
                                }
                            } else {
                                val errorMessage = Gson().fromJson(
                                    res.errorBody()!!.charStream(),
                                    ErrorModel::class.java
                                )
                                dataStatusWorkout =
                                    WorkoutDataStatesUIState.Failed(errorMessage.errors)
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
        } else {
            Log.d("WorkoutCall", "Data already successful, skipping API call")
        }
    }

    fun getWorkoutbyWorkoutIdDuplicate(
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
                                dataStatusWorkout =
                                    WorkoutDataStatesUIState.Success(res.body()!!.data)
                            } else {
                                val errorMessage = Gson().fromJson(
                                    res.errorBody()!!.charStream(),
                                    ErrorModel::class.java
                                )
                                dataStatusWorkout =
                                    WorkoutDataStatesUIState.Failed(errorMessage.errors)
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
        } else {
            Log.d("WorkoutCall", "Data already successful, skipping API call")
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BoxingApplication)
                val workoutRepository = application.container.workoutRepository
                DetailWorkoutViewModel(workoutRepository)
            }
        }
    }

}