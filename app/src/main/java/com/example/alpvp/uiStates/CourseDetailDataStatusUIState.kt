package com.example.alpvp.uiStates

import com.example.alpvp.models.CourseModel

interface CourseDetailDataStatusUIState {
    data class Success(val data: CourseModel): CourseDetailDataStatusUIState
    object Start: CourseDetailDataStatusUIState
    object Loading: CourseDetailDataStatusUIState
    data class Failed(val errorMessage:String): CourseDetailDataStatusUIState
}