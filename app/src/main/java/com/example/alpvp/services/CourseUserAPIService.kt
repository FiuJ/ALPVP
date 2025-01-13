package com.example.alpvp.services

import com.example.alpvp.models.CourseUserRequest
import com.example.alpvp.models.GeneralResponseModel
import com.example.alpvp.models.GetAllCourseUserResponse
import com.example.alpvp.models.GetCourseUserResponse
import retrofit2.Call
import retrofit2.http.*

interface CourseUserAPIService {

    // Create a new course-user relation
    @POST("api/courses-users")
    fun createCourseUser(
        @Header("X-API-TOKEN") token: String,
        @Body courseUserRequest: CourseUserRequest
    ): Call<GeneralResponseModel>

    // Get all course-user relations
    @GET("api/courses-users")
    fun getAllCourseUsers(
        @Header("X-API-TOKEN") token: String
    ): Call<GetAllCourseUserResponse>

    // Get a specific course-user relation by ID
    @GET("api/courses-users/{courseUserId}")
    fun getCourseUser(
        @Header("X-API-TOKEN") token: String,
        @Path("courseUserId") courseUserId: Int
    ): Call<GetCourseUserResponse>

    // Get all course-user relations for a specific user
    @GET("api/courses-users/user/{userId}")
    fun getAllCourseUsersByUserId(
        @Header("X-API-TOKEN") token: String,
        @Path("userId") userId: Int
    ): Call<GetAllCourseUserResponse>

    // Update a course-user relation
    @PUT("api/courses-users/{courseUserId}")
    fun updateCourseUser(
        @Header("X-API-TOKEN") token: String,
        @Path("courseUserId") courseUserId: Int,
        @Body courseUserRequest: CourseUserRequest
    ): Call<GeneralResponseModel>

    // Delete a course-user relation
    @DELETE("api/courses-users/{courseUserId}")
    fun deleteCourseUser(
        @Header("X-API-TOKEN") token: String,
        @Path("courseUserId") courseUserId: Int
    ): Call<String>
}
