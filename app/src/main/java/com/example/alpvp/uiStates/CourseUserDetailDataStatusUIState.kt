package com.example.alpvp.uiStates

import com.example.alpvp.models.CourseUserModel

interface CourseUserDetailDataStatusUIState {
        data class Success(val data: CourseUserModel): CourseUserDetailDataStatusUIState
        object Start: CourseUserDetailDataStatusUIState
        object Loading: CourseUserDetailDataStatusUIState
        data class Failed(val errorMessage:String): CourseUserDetailDataStatusUIState
}