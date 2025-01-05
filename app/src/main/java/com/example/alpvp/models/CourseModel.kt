package com.example.alpvp.models


data class GetAllCourseResponse(
    val data: List<CourseModel>
)

data class GetCourseResponse(
    val data: CourseModel
)

data class CourseModel(
    val course_id: Int = 0,
    val detail_course: String = "",
    val photo_course: String = "",
    val course_duration: Int = 0,
    val community_id: Int = 0
)

data class CourseRequest(
    val detail_course: String = "",
    val photo_course: String = "",
    val course_duration: Int = 0,
    val community_id: Int = 0
)

