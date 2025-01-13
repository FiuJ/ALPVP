package com.example.alpvp.repositories

import com.example.alpvp.models.CourseWorkoutRequest
import com.example.alpvp.models.GetAllCourseWorkoutsResponse
import com.example.alpvp.models.GetCourseWorkoutResponse
import com.example.alpvp.services.CourseWorkoutAPIService
import retrofit2.Call

interface CourseWorkoutRepository {
    fun getAllCourseWorkouts(token: String): Call<GetAllCourseWorkoutsResponse>

    fun createCourseWorkout(
        token: String,
        day: Int,
        workoutId: Int,
        courseId: Int
    ): Call<String>

    fun getCourseWorkout(token: String, workoutCourseId: Int): Call<GetCourseWorkoutResponse>

    fun getAllCourseWorkoutsByCourseId(token: String, courseId: Int): Call<GetAllCourseWorkoutsResponse>

    fun updateCourseWorkout(
        token: String,
        workoutCourseId: Int,
        day: Int,
        workoutId: Int,
        courseId: Int
    ): Call<String>
}

class NetworkCourseWorkoutRepository(
    private val courseWorkoutAPIService: CourseWorkoutAPIService
) : CourseWorkoutRepository {

    override fun getAllCourseWorkouts(token: String): Call<GetAllCourseWorkoutsResponse> {
        return courseWorkoutAPIService.getAllWorkoutCourses(token)
    }

    override fun createCourseWorkout(
        token: String,
        day: Int,
        workoutId: Int,
        courseId: Int
    ): Call<String> {
        return courseWorkoutAPIService.createWorkoutCourse(
            token, CourseWorkoutRequest(day, workoutId, courseId)
        )
    }

    override fun getCourseWorkout(token: String, workoutCourseId: Int): Call<GetCourseWorkoutResponse> {
        return courseWorkoutAPIService.getWorkoutCourse(token, workoutCourseId)
    }

    override fun getAllCourseWorkoutsByCourseId(token: String, courseId: Int): Call<GetAllCourseWorkoutsResponse> {
        return courseWorkoutAPIService.getAllWorkoutCoursesByCourseId(token, courseId)
    }

    override fun updateCourseWorkout(
        token: String,
        workoutCourseId: Int,
        day: Int,
        workoutId: Int,
        courseId: Int
    ): Call<String> {
        return courseWorkoutAPIService.updateWorkoutCourse(
            token, workoutCourseId, CourseWorkoutRequest(day, workoutId, courseId)
        )
    }
}
