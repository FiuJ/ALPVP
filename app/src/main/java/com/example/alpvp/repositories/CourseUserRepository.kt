package com.example.alpvp.repositories

import com.example.alpvp.models.CourseUserRequest
import com.example.alpvp.models.GeneralResponseModel
import com.example.alpvp.models.GetAllCourseUserResponse
import com.example.alpvp.models.GetCourseUserResponse
import com.example.alpvp.services.CourseUserAPIService
import retrofit2.Call

interface CourseUserRepository {
    fun getAllCourseUsers(token: String): Call<GetAllCourseUserResponse>

    fun createCourseUser(
        token: String,
        isDone: Boolean,
        userId: Int,
        courseId: Int
    ): Call<GeneralResponseModel>

    fun getCourseUser(token: String, courseUserId: Int): Call<GetCourseUserResponse>

    fun getAllCourseUsersByUserId(token: String, userId: Int): Call<GetAllCourseUserResponse>

    fun updateCourseUser(
        token: String,
        courseUserId: Int,
        isDone: Boolean,
        userId: Int,
        courseId: Int
    ): Call<GeneralResponseModel>

    fun deleteCourseUser(token: String, courseUserId: Int): Call<String>
}

class NetworkCourseUserRepository(
    private val courseUserAPIService: CourseUserAPIService
) : CourseUserRepository {

    override fun getAllCourseUsers(token: String): Call<GetAllCourseUserResponse> {
        return courseUserAPIService.getAllCourseUsers(token)
    }

    override fun createCourseUser(
        token: String,
        isDone: Boolean,
        userId: Int,
        courseId: Int
    ): Call<GeneralResponseModel> {
        return courseUserAPIService.createCourseUser(
            token, CourseUserRequest(isDone = isDone, user_id = userId, course_id = courseId)
        )
    }

    override fun getCourseUser(token: String, courseUserId: Int): Call<GetCourseUserResponse> {
        return courseUserAPIService.getCourseUser(token, courseUserId)
    }

    override fun getAllCourseUsersByUserId(token: String, userId: Int): Call<GetAllCourseUserResponse> {
        return courseUserAPIService.getAllCourseUsersByUserId(token, userId)
    }

    override fun updateCourseUser(
        token: String,
        courseUserId: Int,
        isDone: Boolean,
        userId: Int,
        courseId: Int
    ): Call<GeneralResponseModel> {
        return courseUserAPIService.updateCourseUser(
            token, courseUserId, CourseUserRequest(isDone = isDone, user_id = userId, course_id = courseId)
        )
    }

    override fun deleteCourseUser(token: String, courseUserId: Int): Call<String> {
        return courseUserAPIService.deleteCourseUser(token, courseUserId)
    }
}
