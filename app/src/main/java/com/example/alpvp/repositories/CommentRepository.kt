package com.example.alpvp.repositories

import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.alpvp.models.CommentResponse
import com.example.alpvp.models.GeneralResponseModel
import com.example.alpvp.services.CommentAPIService
import com.example.alpvp.services.UserAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.Call
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface CommentRepository{
    suspend fun createComment(token: String, postId: Int, content: String): GeneralResponseModel
    fun getAllCommentsByPost(token: String, postId: Int): Call<CommentResponse>
}

class NetworkCommentRepository(
    private val commentAPIService: CommentAPIService,
    private val userRepository: UserRepository
): CommentRepository {


    private fun getCurrentDateTime(): String {
        return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date())
    }


    override suspend fun createComment(token: String, postId: Int, content: String): GeneralResponseModel{
        val commentMap = HashMap<String, String>().apply {
            put("comment", content)
            put("comment_date", getCurrentDateTime())
            put("user_id", userRepository.currentUserId.first().toString())
            put("post_id", postId.toString())
        }
        val response = commentAPIService.createComment(token, commentMap).execute()
        return response.body() ?: throw Exception("Failed to create comment")
    }



    override fun getAllCommentsByPost(token: String, postId: Int): Call<CommentResponse> {
        return commentAPIService.getAllCommentsByPostId(token, postId)
    }


}