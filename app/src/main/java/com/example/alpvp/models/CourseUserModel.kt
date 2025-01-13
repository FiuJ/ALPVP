package com.example.alpvp.models


data class GetAllCourseUserResponse(
    val data: List<CourseUserModel>
)

data class GetCourseUserResponse(
    val data: CourseUserModel
)

data class getCourseUserRequest(
    val course_user_id : Int = 0,
    val isDone: Boolean = false,
    val user_id: Int = 0,
    val course_id: Int = 0,
)

data class CourseUserModel(
    val isDone: Boolean = false,
    val user_id: Int = 0,
    val course_id: Int = 0,
)

data class CourseUserRequest(
    val course_user_id : Int = 0,
    val isDone: Boolean = false,
    val user_id: Int = 0,
    val course_id: Int = 0,
)
