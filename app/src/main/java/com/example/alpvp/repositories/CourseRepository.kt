package com.example.alpvp.repositories

import com.example.alpvp.models.CourseModel
import com.example.alpvp.models.CourseRequest
import com.example.alpvp.models.GetAllCourseResponse
import com.example.alpvp.models.GetCourseResponse
import com.example.alpvp.services.CourseAPIService
import retrofit2.Call

interface CourseRepository {
    fun getAllCourses(token: String): Call<GetAllCourseResponse>

    fun createCourse(token: String, name: String, description: String, duration: Int): Call<CourseModel>

    fun getCourse(token: String, courseId: Int): Call<GetCourseResponse>

    fun updateCourse(token: String, courseId: Int, name: String, description: String, duration: Int): Call<CourseModel>
}

class NetworkCourseRepository(
    private val courseAPIService: CourseAPIService
) : CourseRepository {

    override fun getAllCourses(token: String): Call<GetAllCourseResponse> {
        return courseAPIService.getAllCourses(token)
    }

    override fun createCourse(
        token: String,
        name: String,
        description: String,
        duration: Int
    ): Call<CourseModel> {
        val courseRequest = CourseRequest(name, description, duration)
        return courseAPIService.createCourse(token, courseRequest)
    }

    override fun getCourse(token: String, courseId: Int): Call<GetCourseResponse> {
        return courseAPIService.getCourse(token, courseId)
    }

    override fun updateCourse(
        token: String,
        courseId: Int,
        name: String,
        description: String,
        duration: Int
    ): Call<CourseModel> {
        val courseRequest = CourseRequest(name, description, duration)
        return courseAPIService.updateCourse(token, courseId, courseRequest)
    }
}
