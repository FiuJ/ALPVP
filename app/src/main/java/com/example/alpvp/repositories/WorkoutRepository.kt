package com.example.alpvp.repositories

import com.example.alpvp.models.GetAllWorkoutResponse
import com.example.alpvp.models.GetWorkoutResponse
import com.example.alpvp.models.WorkoutRequest
import com.example.alpvp.services.WorkoutAPIService
import retrofit2.Call

interface WorkoutRepository {
    fun getAllWorkouts(token: String): Call<GetAllWorkoutResponse>

    fun createWorkout(
        token: String,
        nameWorkout: String,
        detailWorkout: String,
        workoutType: String,
        photoWorkout: String,
        workoutDuration: Int
    ): Call<String>

    fun getWorkoutById(token: String, workoutId: Int): Call<GetWorkoutResponse>

    fun updateWorkout(
        token: String,
        workoutId: Int,
        nameWorkout: String,
        detailWorkout: String,
        workoutType: String,
        photoWorkout: String,
        workoutDuration: Int
    ): Call<String>
}

class NetworkWorkoutRepository(
    private val workoutAPIService: WorkoutAPIService
) : WorkoutRepository {

    override fun getAllWorkouts(token: String): Call<GetAllWorkoutResponse> {
        return workoutAPIService.getAllWorkouts(token)
    }

    override fun createWorkout(
        token: String,
        nameWorkout: String,
        detailWorkout: String,
        workoutType: String,
        photoWorkout: String,
        workoutDuration: Int
    ): Call<String> {
        val workoutRequest = WorkoutRequest(
            name_workout = nameWorkout,
            detail_workout = detailWorkout,
            workout_type = workoutType,
            photo_workout = photoWorkout,
            workout_duration = workoutDuration
        )
        return workoutAPIService.createWorkout(token, workoutRequest)
    }

    override fun getWorkoutById(token: String, workoutId: Int): Call<GetWorkoutResponse> {
        return workoutAPIService.getWorkoutById(token, workoutId)
    }

    override fun updateWorkout(
        token: String,
        workoutId: Int,
        nameWorkout: String,
        detailWorkout: String,
        workoutType: String,
        photoWorkout: String,
        workoutDuration: Int
    ): Call<String> {
        val workoutRequest = WorkoutRequest(
            name_workout = nameWorkout,
            detail_workout = detailWorkout,
            workout_type = workoutType,
            photo_workout = photoWorkout,
            workout_duration = workoutDuration
        )
        return workoutAPIService.updateWorkout(token, workoutId, workoutRequest)
    }
}
