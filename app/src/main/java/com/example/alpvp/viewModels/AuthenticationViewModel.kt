package com.example.alpvp.viewModels


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.example.alpvp.BoxingApplication
import com.example.alpvp.R
import com.example.alpvp.enums.PagesEnum
import com.example.alpvp.models.ErrorModel
import com.example.alpvp.models.UserResponse
import com.example.alpvp.repositories.AuthenticationRepository
import com.example.alpvp.repositories.UserRepository
import com.example.alpvp.uiStates.AuthenticationStatusUIState
import com.example.alpvp.uiStates.AuthenticationUIState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class AuthenticationViewModel (
    private val authenticationRepository: AuthenticationRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val _authenticationUIState = MutableStateFlow(AuthenticationUIState())

    val authenticationUIState: StateFlow<AuthenticationUIState>
        get() {
            return _authenticationUIState.asStateFlow()
        }

    var dataStatus: AuthenticationStatusUIState by mutableStateOf(AuthenticationStatusUIState.Start)
        private set

    var usernameInput by mutableStateOf("")
        private set

    var passwordInput by mutableStateOf("")
        private set

    var confirmPasswordInput by mutableStateOf("")
        private set

    var emailInput by mutableStateOf("")
        private set

    fun changeEmailInput(emailInput: String) {
        this.emailInput = emailInput
    }

    fun changeConfirmPasswordInput(confirmPasswordInput: String) {
        this.confirmPasswordInput = confirmPasswordInput
    }

    fun changeUsernameInput(usernameInput: String) {
        this.usernameInput = usernameInput
    }

    fun changePasswordInput(passwordInput: String) {
        this.passwordInput = passwordInput
    }



    fun checkLoginForm() {
        if (emailInput.isNotEmpty() && passwordInput.isNotEmpty()) {
            _authenticationUIState.update { currentState ->
                currentState.copy(
                    buttonEnabled = true
                )
            }
        } else {
            _authenticationUIState.update { currentState ->
                currentState.copy(
                    buttonEnabled = false
                )
            }
        }
    }

    fun checkRegisterForm() {
        if (emailInput.isNotEmpty() && passwordInput.isNotEmpty() && usernameInput.isNotEmpty() && confirmPasswordInput.isNotEmpty() && passwordInput == confirmPasswordInput) {
            _authenticationUIState.update { currentState ->
                currentState.copy(
                    buttonEnabled = true
                )
            }
        } else {
            _authenticationUIState.update { currentState ->
                currentState.copy(
                    buttonEnabled = false
                )
            }
        }
    }

    fun checkButtonEnabled(isEnabled: Boolean): Color {
        if (isEnabled) {
            return Color.Blue
        }

        return Color.LightGray
    }

    fun saveUsernameTokenId(token: String, username: String, id: Int) {
        viewModelScope.launch {
            userRepository.saveUserToken(token)
            userRepository.saveUsername(username)
            userRepository.saveUserId(id)
        }
    }




    fun resetViewModel() {
        changeEmailInput("")
        changePasswordInput("")
        changeUsernameInput("")
        changeConfirmPasswordInput("")
        _authenticationUIState.update { currentState ->
            currentState.copy(
                showConfirmPassword = false,
                showPassword = false,
                passwordVisibility = PasswordVisualTransformation(),
                confirmPasswordVisibility = PasswordVisualTransformation(),
                passwordVisibilityIcon = R.drawable.ic_password_visible,
                confirmPasswordVisibilityIcon = R.drawable.ic_password_visible,
                buttonEnabled = false
            )
        }
        dataStatus = AuthenticationStatusUIState.Start
    }

    //registerUser disini
    fun registerUser(navController: NavHostController) {
        viewModelScope.launch {
            dataStatus = AuthenticationStatusUIState.Loading

            try {
                val call = authenticationRepository.register(usernameInput, emailInput, passwordInput)
//                dataStatus = UserDataStatusUIState.Success(registerResult)

                call.enqueue(object: Callback<UserResponse>{
                    override fun onResponse(call: Call<UserResponse>, res: Response<UserResponse>) {
                        if (res.isSuccessful) {
                            Log.d("response-data", "RESPONSE DATA: ${res.body()}")

                            saveUsernameTokenId(res.body()!!.data.token!!, res.body()!!.data.username, res.body()!!.data.id)

                            dataStatus = AuthenticationStatusUIState.Success(res.body()!!.data)

                            resetViewModel()

                            navController.navigate(PagesEnum.Home.name) {
                                popUpTo(PagesEnum.Register.name) {
                                    inclusive = true
                                }
                            }
                        } else {
                            // get error message
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            Log.d("error-data", "ERROR DATA: ${errorMessage}")
                            dataStatus = AuthenticationStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.d("error-data", "ERROR DATA: ${t.localizedMessage}")
                        dataStatus = AuthenticationStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dataStatus = AuthenticationStatusUIState.Failed(error.localizedMessage)
                Log.d("register-error", "REGISTER ERROR: ${error.localizedMessage}")
            }
        }
    }

    fun loginUser(
        navController: NavHostController
    ) {
        viewModelScope.launch {
            dataStatus = AuthenticationStatusUIState.Loading
            try {
                val call = authenticationRepository.login(emailInput, passwordInput)
                call.enqueue(object: Callback<UserResponse> {
                    override fun onResponse(call: Call<UserResponse>, res: Response<UserResponse>) {
                        if (res.isSuccessful) {
                            saveUsernameTokenId(res.body()!!.data.token!!, res.body()!!.data.username, res.body()!!.data.id)

                            dataStatus = AuthenticationStatusUIState.Success(res.body()!!.data)

                            resetViewModel()

                            navController.navigate(PagesEnum.Home.name) {
                                popUpTo(PagesEnum.Login.name) {
                                    inclusive = true
                                }
                            }
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            Log.d("error-data", "ERROR DATA: ${errorMessage.errors}")
                            dataStatus = AuthenticationStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        dataStatus = AuthenticationStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dataStatus = AuthenticationStatusUIState.Failed(error.localizedMessage)
                Log.d("register-error", "LOGIN ERROR: ${error.toString()}")
            }
        }
    }



    fun clearErrorMessage() {
        dataStatus = AuthenticationStatusUIState.Start
    }




    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BoxingApplication)
                val authenticationRepository =  application.container.authenticationRepository
                val userRepository = application.container.userRepository
                AuthenticationViewModel(authenticationRepository, userRepository)
            }
        }
    }



}