package com.example.alpvp.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.alpvp.models.GeneralResponseModel
import com.example.alpvp.services.UserAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Call


//interface UserRepository {
//    val currentUserToken: Flow<String>
//    val currentUsername: Flow<String>
//
////    val currentUserID: Flow<Int>
//
//    val currentUserId: Flow<Int>
//
//
//    fun logout(token: String): Call<GeneralResponseModel>
//
//    suspend fun saveUserToken(token: String)
//
//    suspend fun saveUsername(username: String)
//
//    suspend fun saveUserId(id: Int)
//
//}
//
//class NetworkUserRepository(
//    private val userDataStore: DataStore<Preferences>,
//    private val userAPIService: UserAPIService
//): UserRepository {
//    private companion object {
//        val USER_TOKEN = stringPreferencesKey("token")
//        val USERNAME = stringPreferencesKey("username")
//        val USER_ID = intPreferencesKey("id")
//
//
//
////        override val currentUserID: Flow<Int> = userDataStore.data.map { preferences ->
////            preferences[USER_ID]?.toInt() ?: 0
////
////            val USER_ID = stringPreferencesKey("id")
////
////        }
////
////        override val currentUserToken: Flow<String> = userDataStore.data.map { preferences ->
////            preferences[USER_TOKEN] ?: "Unknown"
////        }
////
////        override val currentUsername: Flow<String> = userDataStore.data.map { preferences ->
////            preferences[USERNAME] ?: "Unknown"
////        }
////
////        override val currentUserId: Flow<Int> = userDataStore.data.map { preferences ->
////            preferences[USER_ID]?.toIntOrNull() ?: 0
////        }
////
////        override suspend fun saveUserToken(token: String) {
////            userDataStore.edit { preferences ->
////                preferences[USER_TOKEN] = token
////            }
////        }
////
////        override suspend fun saveUserID(id: Int) {
////            userDataStore.edit { preferences ->
////                preferences[USER_ID] = id
////            }
////        }
////
////        override suspend fun saveUsername(username: String) {
////            userDataStore.edit { preferences ->
////                preferences[USERNAME] = username
////            }
////        }
////
////        override suspend fun saveUserId(id: Int) {
////            userDataStore.edit { preferences ->
////                preferences[USER_ID] = id.toString()
////            }
////        }
////
////        override fun logout(token: String): Call<GeneralResponseModel> {
////            return userAPIService.logout(token)
////        }
////
////        override fun emergencyLogout(token: String): Call<GeneralResponseModel> {
////            return userAPIService.emergencyLogout(token) // New method added
////        }
//
////    override fun getUserIdFromToken(token: String): Call<GeneralResponseModel> {
////        return userAPIService.getUserIdFromToken("$token") // Use Bearer format
////    }
//    }
//
//    override val currentUserToken: Flow<String>
//        get() = TODO("Not yet implemented")
//    override val currentUsername: Flow<String>
//        get() = TODO("Not yet implemented")
//    override val currentUserId: Flow<Int>
//        get() = TODO("Not yet implemented")
//
//    override fun logout(token: String): Call<GeneralResponseModel> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun saveUserToken(token: String) {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun saveUsername(username: String) {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun saveUserId(id: Int) {
//        TODO("Not yet implemented")
//    }
//}

interface UserRepository {
    val currentUserToken: Flow<String>
    val currentUsername: Flow<String>

//    val currentUserID: Flow<Int>

    val currentUserId: Flow<Int>


    fun logout(token: String): Call<GeneralResponseModel>

    suspend fun saveUserToken(token: String)

    suspend fun saveUsername(username: String)

    suspend fun saveUserId(id: Int)
}

class NetworkUserRepository(
    private val userDataStore: DataStore<Preferences>,
    private val userAPIService: UserAPIService
): UserRepository {
    private companion object {
        val USER_TOKEN = stringPreferencesKey("token")
        val USERNAME = stringPreferencesKey("username")
        val USER_ID = stringPreferencesKey("id")
    }

//        override val currentUserID: Flow<Int> = userDataStore.data.map { preferences ->
//            preferences[USER_ID]?.toInt() ?: 0
//
//            val USER_ID = stringPreferencesKey("id")
//
//        }

        override val currentUserToken: Flow<String> = userDataStore.data.map { preferences ->
            preferences[USER_TOKEN] ?: "Unknown"
        }

        override val currentUsername: Flow<String> = userDataStore.data.map { preferences ->
            preferences[USERNAME] ?: "Unknown"
        }

        override val currentUserId: Flow<Int> = userDataStore.data.map { preferences ->
            preferences[USER_ID]?.toIntOrNull() ?: 0
        }

        override suspend fun saveUserToken(token: String) {
            userDataStore.edit { preferences ->
                preferences[USER_TOKEN] = token
            }
        }

//        override suspend fun saveUserID(id: Int) {
//            userDataStore.edit { preferences ->
//                preferences[USER_ID] = id
//            }
//        }

        override suspend fun saveUsername(username: String) {
            userDataStore.edit { preferences ->
                preferences[USERNAME] = username
            }
        }

        override suspend fun saveUserId(id: Int) {
            userDataStore.edit { preferences ->
                preferences[USER_ID] = id.toString()
            }
        }

        override fun logout(token: String): Call<GeneralResponseModel> {
            return userAPIService.logout(token)
        }

//        override fun emergencyLogout(token: String): Call<GeneralResponseModel> {
//            return userAPIService.emergencyLogout(token) // New method added
//        }

//    override fun getUserIdFromToken(token: String): Call<GeneralResponseModel> {
//        return userAPIService.getUserIdFromToken("$token") // Use Bearer format
//    }

    }


//interface UserRepository {
//    val currentUserToken: Flow<String>
//    val currentUsername: Flow<String>
//    val currentUserId: Flow<Int>
//
//    fun logout(token: String): Call<GeneralResponseModel>
//
//    suspend fun saveUserToken(token: String)
//
//    suspend fun saveUsername(username: String)
//
//    suspend fun saveUserId(id: Int)
//
////    fun emergencyLogout(token: String): Call<GeneralResponseModel>
//
//
////    fun getUserIdFromToken(token: String): Call<GeneralResponseModel>
//}
//
//class NetworkUserRepository(
//    private val userDataStore: DataStore<Preferences>,
//    private val userAPIService: UserAPIService
//): UserRepository {
//    private companion object {
//        val USER_TOKEN = stringPreferencesKey("token")
//        val USERNAME = stringPreferencesKey("username")
//        val USER_ID = intPreferencesKey("user_id")
//    }
//
//    override val currentUserId: Flow<Int> = userDataStore.data.map { preferences ->
//        preferences[USER_ID]?.toInt() ?: 0
//    }
//
//    override val currentUserToken: Flow<String> = userDataStore.data.map { preferences ->
//        preferences[USER_TOKEN] ?: "Unknown"
//    }
//
//    override val currentUsername: Flow<String> = userDataStore.data.map { preferences ->
//        preferences[USERNAME] ?: "Unknown"
//    }
//
//    override suspend fun saveUserToken(token: String) {
//        userDataStore.edit { preferences ->
//            preferences[USER_TOKEN] = token
//        }
//    }
//
//    override suspend fun saveUserId(id: Int) {
//        userDataStore.edit { preferences ->
//            preferences[USER_ID] = id
//        }
//    }
//
//    override suspend fun saveUsername(username: String) {
//        userDataStore.edit { preferences ->
//            preferences[USERNAME] = username
//        }
//    }
//
//    override fun logout(token: String): Call<GeneralResponseModel> {
//        return userAPIService.logout(token)
//    }
//
////    override fun emergencyLogout(token: String): Call<GeneralResponseModel> {
////        return userAPIService.emergencyLogout(token) // New method added
////    }
//
////    override fun getUserIdFromToken(token: String): Call<GeneralResponseModel> {
////        return userAPIService.getUserIdFromToken("$token") // Use Bearer format
////    }
//
//}