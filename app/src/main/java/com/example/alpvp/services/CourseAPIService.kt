package com.example.alpvp.services

import com.example.alpvp.models.CourseModel
import com.example.alpvp.models.CourseRequest
import com.example.alpvp.models.GetAllCourseResponse
import com.example.alpvp.models.GetCourseResponse
import retrofit2.Call
import retrofit2.http.*

interface CourseAPIService {
    @POST("api/courses")
    fun createCourse(
        @Header("X-API-TOKEN") token: String,
        @Body courseRequest: CourseRequest
    ): Call<CourseModel>

    @GET("api/courses")
    fun getAllCourses(
        @Header("X-API-TOKEN") token: String
    ): Call<GetAllCourseResponse>

    @GET("api/courses/{courseId}")
    fun getCourse(
        @Header("X-API-TOKEN") token: String,
        @Path("courseId") courseId: Int
    ): Call<GetCourseResponse>

    @PUT("api/courses/{courseId}")
    fun updateCourse(
        @Header("X-API-TOKEN") token: String,
        @Path("courseId") courseId: Int,
        @Body courseRequest: CourseRequest
    ): Call<CourseModel>
}