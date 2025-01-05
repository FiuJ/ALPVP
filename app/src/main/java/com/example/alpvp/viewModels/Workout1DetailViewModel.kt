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
import com.example.alpvp.models.GetCourseResponse
import com.example.alpvp.repositories.CourseRepository
import com.example.alpvp.uiStates.CourseDetailDataStatusUIState
import com.example.alpvp.uiStates.StringDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class Workout1DetailViewModel (
    private val courseRepository: CourseRepository
): ViewModel() {
    var dataStatus: CourseDetailDataStatusUIState by mutableStateOf(CourseDetailDataStatusUIState.Start)
        private set

    var deleteStatus: StringDataStatusUIState by mutableStateOf(StringDataStatusUIState.Start)
        private set

    fun getTodo(token: String, courseId: Int, navController: NavHostController, isUpdating: Boolean) {
        viewModelScope.launch {
            dataStatus = CourseDetailDataStatusUIState.Loading

            try {
                val call = courseRepository.getCourse(token, courseId)

                call.enqueue(object: Callback<GetCourseResponse> {
                    override fun onResponse(call: Call<GetCourseResponse>, res: Response<GetCourseResponse>) {
                        if (res.isSuccessful) {
                            dataStatus = CourseDetailDataStatusUIState.Success(res.body()!!.data)

                            Log.d("get-RESULT", "GET : ${res.body()}")

                            if (isUpdating) {
                                navController.popBackStack()
                            } else {
                                navController.navigate(PagesEnum.CourseDetail.name) {
                                    popUpTo(PagesEnum.Home.name) {
                                        inclusive = false
                                    }
                                }
                            }
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            dataStatus = CourseDetailDataStatusUIState.Failed(errorMessage.errors)
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BoxingApplication)
                val courseRepository = application.container.courseRepository
                Workout1DetailViewModel(courseRepository)
            }
        }
    }




}