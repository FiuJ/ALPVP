package com.example.alpvp.uiStates

import com.example.alpvp.models.CourseUserModel


interface CourseUserDataStatesUIStates {
    data class Success(val data: List<CourseUserModel>): CourseUserDataStatesUIStates
    object Start: CourseUserDataStatesUIStates
    object Loading: CourseUserDataStatesUIStates
    data class Failed(val errorMessage:String): CourseUserDataStatesUIStates
}