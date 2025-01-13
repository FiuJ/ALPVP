package com.example.alpvp.services
import com.example.alpvp.models.WorkoutModel
import com.example.alpvp.models.WorkoutRequest
import com.example.alpvp.models.GetAllWorkoutResponse
import com.example.alpvp.models.GetWorkoutResponse
import retrofit2.Call
import retrofit2.http.*

interface WorkoutAPIService {
    // Create a new workout
    @POST("api/workouts")
    fun createWorkout(
        @Header("X-API-TOKEN") token: String,
        @Body workoutRequest: WorkoutRequest
    ): Call<String>

    // Get all workouts
    @GET("api/workouts")
    fun getAllWorkouts(
        @Header("X-API-TOKEN") token: String
    ): Call<GetAllWorkoutResponse>

    // Get a specific workout by ID
    @GET("api/workouts/{workoutId}")
    fun getWorkoutById(
        @Header("X-API-TOKEN") token: String,
        @Path("workoutId") workoutId: Int
    ): Call<GetWorkoutResponse>

    // Update an existing workout
    @PUT("api/workouts/{workoutId}")
    fun updateWorkout(
        @Header("X-API-TOKEN") token: String,
        @Path("workoutId") workoutId: Int,
        @Body workoutRequest: WorkoutRequest
    ): Call<String>
}