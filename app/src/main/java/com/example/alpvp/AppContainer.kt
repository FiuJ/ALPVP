package com.example.alpvp


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.alpvp.repositories.AuthenticationRepository
import com.example.alpvp.repositories.CommunityRepository
import com.example.alpvp.repositories.CourseRepository
import com.example.alpvp.repositories.CourseUserRepository
import com.example.alpvp.repositories.CourseWorkoutRepository
import com.example.alpvp.repositories.NetworkAuthenticationRepository
import com.example.alpvp.repositories.NetworkCommunityRepository
import com.example.alpvp.repositories.NetworkCourseRepository
import com.example.alpvp.repositories.NetworkCourseUserRepository
import com.example.alpvp.repositories.NetworkCourseWorkoutRepository
import com.example.alpvp.repositories.NetworkUserRepository
import com.example.alpvp.repositories.NetworkWorkoutRepository
import com.example.alpvp.repositories.UserRepository
import com.example.alpvp.repositories.WorkoutRepository
import com.example.alpvp.services.AuthenticationAPIService
import com.example.alpvp.services.CommunityAPIService
import com.example.alpvp.services.CourseAPIService
import com.example.alpvp.services.CourseUserAPIService
import com.example.alpvp.services.CourseWorkoutAPIService
import com.example.alpvp.services.UserAPIService
import com.example.alpvp.services.WorkoutAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

interface AppContainer {
    val authenticationRepository: AuthenticationRepository
    val userRepository: UserRepository
    val communityRepository: CommunityRepository
    val courseRepository: CourseRepository
    val courseWorkoutRepository: CourseWorkoutRepository
    val workoutRepository: WorkoutRepository
    val courseUserRepository: CourseUserRepository
}


class DefaultAppContainer(
    private val userDataStore: DataStore<Preferences>
):AppContainer{
    //link baseurl
//    private val baseUrl = "http://192.168.43.222:3000/"
    private val baseUrl = "http://192.168.1.6:3000/"

    //IP TEST
//    private val authenticationRetrofitService: AuthenticationAPIService by lazy {
//        val retrofit = initRetrofit()
//
//        retrofit.create(AuthenticationAPIService::class.java)
//    }

    //retrofit disini

    private val courseUserRetrofitService: CourseUserAPIService by lazy {
        val retrofit = initRetrofit()
        retrofit.create(CourseUserAPIService::class.java)
    }

    private val workoutRetrofitService: WorkoutAPIService by lazy {
        val retrofit = initRetrofit()
        retrofit.create(WorkoutAPIService::class.java)
    }

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

    private val courseWorkoutRetrofitService: CourseWorkoutAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(CourseWorkoutAPIService::class.java)
    }





    //repository init disini

    override val courseUserRepository: CourseUserRepository by lazy {
        NetworkCourseUserRepository(courseUserRetrofitService)
    }

    override val workoutRepository: WorkoutRepository by lazy {
        NetworkWorkoutRepository(workoutRetrofitService)
    }

    override val courseWorkoutRepository: CourseWorkoutRepository by lazy {
        NetworkCourseWorkoutRepository(courseWorkoutRetrofitService)
    }
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
