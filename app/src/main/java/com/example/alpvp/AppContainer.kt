package com.example.alpvp


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.alpvp.repositories.AuthenticationRepository
import com.example.alpvp.repositories.NetworkAuthenticationRepository
import com.example.alpvp.services.AuthenticationAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val authenticationRepository: AuthenticationRepository

}


class DefaultAppContainer(
    private val userDataStore: DataStore<Preferences>
):AppContainer{
    //link baseurl
    private val baseUrl = "http://192.168.1.6/"

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



    //repository init disini
    override val authenticationRepository: AuthenticationRepository by lazy {
        NetworkAuthenticationRepository(authenticationRetrofitService)
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
