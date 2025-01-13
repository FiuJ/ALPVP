package com.example.alpvp.models

data class WorkoutModel(
    val workout_id: Int = 0,
    val name_workout: String = "",
    val detail_workout: String = "",
    val workout_type: String = "",
    val photo_workout: String = "",
    val workout_duration: Int = 0
)

data class WorkoutRequest(
    val name_workout: String = "",
    val detail_workout: String = "",
    val workout_type: String = "",
    val photo_workout: String = "",
    val workout_duration: Int = 0
)


data class GetAllWorkoutResponse(
    val data: List<WorkoutModel>
)

data class GetWorkoutResponse(
    val data: WorkoutModel
)
