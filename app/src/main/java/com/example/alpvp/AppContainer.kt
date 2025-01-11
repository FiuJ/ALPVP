package com.example.alpvp


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.alpvp.repositories.AuthenticationRepository
import com.example.alpvp.repositories.CommentRepository
import com.example.alpvp.repositories.CommunityRepository
import com.example.alpvp.repositories.CourseRepository
import com.example.alpvp.repositories.NetworkAuthenticationRepository
import com.example.alpvp.repositories.NetworkCommentRepository
import com.example.alpvp.repositories.NetworkCommunityRepository
import com.example.alpvp.repositories.NetworkCourseRepository
import com.example.alpvp.repositories.NetworkPostRepository
import com.example.alpvp.repositories.NetworkUserRepository
import com.example.alpvp.repositories.PostRepository
import com.example.alpvp.repositories.UserRepository
import com.example.alpvp.services.AuthenticationAPIService
import com.example.alpvp.services.CommentAPIService
import com.example.alpvp.services.CommunityAPIService
import com.example.alpvp.services.CourseAPIService
import com.example.alpvp.services.PostAPIService
import com.example.alpvp.services.UserAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val authenticationRepository: AuthenticationRepository
    val userRepository: UserRepository
    val communityRepository: CommunityRepository
    val courseRepository: CourseRepository
    val postRepository: PostRepository
    val commentRepository: CommentRepository
}


class DefaultAppContainer(
    private val userDataStore: DataStore<Preferences>
):AppContainer{
    //link baseurl
    private val baseUrl = "http://192.168.181.222:3000/"
//    private val baseUrl = "http://192.168.43.222:3000/"
//    private val baseUrl = "http://192.168.1.6:3000/"

    //IP TEST

//    private val authenticationRetrofitService: AuthenticationAPIService by lazy {
//        val retrofit = initRetrofit()
//
//        retrofit.create(AuthenticationAPIService::class.java)
//    }

    //retrofit disini
    private val authenticationRetrofitService: AuthenticationAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(AuthenticationAPIService::class.java)
    }

    private val courseRetrofitService: CourseAPIService by lazy {
        val retrofit = initRetrofit()
        retrofit.create(CourseAPIService::class.java)
    }

    private val userRetrofitService: UserAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(UserAPIService::class.java)
    }

    private val communityRetrofitService: CommunityAPIService by lazy {
        val retrofit = initRetrofit()
        retrofit.create(CommunityAPIService::class.java)
    }

    private val postRetrofitService: PostAPIService by lazy {
        val retrofit = initRetrofit()
        retrofit.create(PostAPIService::class.java)
    }

    private val commentRetrofitService: CommentAPIService by lazy {
        val retrofit = initRetrofit()
        retrofit.create(CommentAPIService::class.java)
    }






    //repository init disini
    override val authenticationRepository: AuthenticationRepository by lazy {
        NetworkAuthenticationRepository(authenticationRetrofitService)
    }

    override val courseRepository : CourseRepository by lazy {
        NetworkCourseRepository(courseRetrofitService )
    }

    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userDataStore, userRetrofitService)
    }

    override val communityRepository: CommunityRepository by lazy {
        NetworkCommunityRepository(communityRetrofitService)
    }

    override val postRepository: PostRepository by lazy {
        NetworkPostRepository(postRetrofitService)
    }

    override val commentRepository: CommentRepository by lazy {
        NetworkCommentRepository(commentRetrofitService, userRepository)
    }




    private fun initRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
        client.addInterceptor(logging)

        return Retrofit
            .Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(client.build())
            .baseUrl(baseUrl)
            .build()
    }
}
