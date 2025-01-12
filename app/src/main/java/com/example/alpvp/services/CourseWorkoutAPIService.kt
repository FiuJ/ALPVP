package com.example.alpvp.services

import com.example.alpvp.models.CourseWorkoutRequest
import com.example.alpvp.models.GetAllCourseWorkoutsResponse
import com.example.alpvp.models.GetCourseWorkoutResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.*

interface CourseWorkoutAPIService {

        // Create a new workout-course relation
        @POST("api/workout-courses")
        fun createWorkoutCourse(
            @Header("X-API-TOKEN") token: String,
            @Body workoutCoursesCreateRequest: CourseWorkoutRequest
        ): Call<String>

        // Get all workout-course relations
        @GET("api/workout-courses")
        fun getAllWorkoutCourses(
            @Header("X-API-TOKEN") token: String
        ): Call<GetAllCourseWorkoutsResponse>

        // Get a specific workout-course relation by ID
        @GET("api/workout-courses/{workoutCourseId}")
        fun getWorkoutCourse(
            @Header("X-API-TOKEN") token: String,
            @Path("workoutCourseId") workoutCourseId: Int
        ): Call<GetCourseWorkoutResponse>

        // Get workout-course relations for a specific course ID
        @GET("api/workout-courses/course/{courseId}")
        fun getAllWorkoutCoursesByCourseId(
            @Header("X-API-TOKEN") token: String,
            @Path("courseId") courseId: Int
        ): Call<GetAllCourseWorkoutsResponse>

        // Update a workout-course relation
        @PUT("api/workout-courses/{workoutCourseId}")
        fun updateWorkoutCourse(
            @Header("X-API-TOKEN") token: String,
            @Path("workoutCourseId") workoutCourseId: Int,
            @Body workoutCoursesCreateRequest: CourseWorkoutRequest
        ): Call<String>


}