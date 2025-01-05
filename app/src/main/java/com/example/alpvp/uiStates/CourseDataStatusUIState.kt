package com.example.alpvp.uiStates

import com.example.alpvp.models.CourseModel


interface CourseDataStatusUIState {
    data class Success(val data: List<CourseModel>): CourseDataStatusUIState
    object Start: CourseDataStatusUIState
    object Loading: CourseDataStatusUIState
    data class Failed(val errorMessage:String): CourseDataStatusUIState
}