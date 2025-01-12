package com.example.alpvp.uiStates
import com.example.alpvp.models.CourseWorkoutModel

interface WorkoutCourseDataStatesUIState {
    data class Success(val data: List<CourseWorkoutModel>): WorkoutCourseDataStatesUIState
    object Start: WorkoutCourseDataStatesUIState
    object Loading: WorkoutCourseDataStatesUIState
    data class Failed(val errorMessage:String): WorkoutCourseDataStatesUIState
}