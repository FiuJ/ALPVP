package com.example.alpvp.uiStates

import com.example.alpvp.models.CourseUserModel
import com.example.alpvp.models.getCourseUserRequest

//getCourseUserRequest
interface CourseUserRequestDataStatesUIState {
    data class Success(val data: List<getCourseUserRequest>): CourseUserRequestDataStatesUIState
    object Start: CourseUserRequestDataStatesUIState
    object Loading: CourseUserRequestDataStatesUIState
    data class Failed(val errorMessage:String): CourseUserRequestDataStatesUIState
}