package com.example.alpvp.models

data class CourseWorkoutModel(
    val course_workout_id: Int = 0,
    val day: Int = 0,
    val workout_id: Int = 0,
    val course_id: Int = 0
)

data class CourseWorkoutRequest(
    val day: Int = 0,
    val workout_id: Int = 0,
    val course_id: Int = 0
)

data class GetAllCourseWorkoutsResponse(
    val data: List<CourseWorkoutModel>
)

data class GetCourseWorkoutResponse(
    val data: CourseWorkoutModel
)
