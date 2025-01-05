package com.example.alpvp.views

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alpvp.R
import com.example.alpvp.enums.PagesEnum
import com.example.alpvp.ui.theme.ALPVPTheme
import com.example.alpvp.uiStates.AuthenticationStatusUIState
import com.example.alpvp.viewModels.AuthenticationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(
    authenticationViewModel: AuthenticationViewModel,
    navController: NavHostController,
    context: Context
){
    val registerUIState by authenticationViewModel.authenticationUIState.collectAsState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(authenticationViewModel.dataStatus) {
        val dataStatus = authenticationViewModel.dataStatus
        if (dataStatus is AuthenticationStatusUIState.Failed) {
            Toast.makeText(context, dataStatus.errorMessage, Toast.LENGTH_SHORT).show()
            authenticationViewModel.clearErrorMessage()
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9602A)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Image(
            painter = painterResource(id = R.drawable.login_image),
            contentDescription = "Logo",
            modifier = Modifier
                .height(260.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(40.dp))

        Column (

            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ){
            Text(
                text = "Make yourself known!",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = authenticationViewModel.usernameInput,
                onValueChange = {
                    authenticationViewModel.changeUsernameInput(it)
                    authenticationViewModel.checkRegisterForm()
                },
                label = {
                    Text("Username", color = Color.Gray)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Black
                )

            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = authenticationViewModel.emailInput,
                onValueChange = {
                    authenticationViewModel.changeEmailInput(it)
                    authenticationViewModel.checkRegisterForm()
                },
                label = {
                    Text("Email Address", color = Color.Gray)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Black
                )

            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = authenticationViewModel.passwordInput,
                onValueChange = {
                    authenticationViewModel.changePasswordInput(it)
                    authenticationViewModel.checkRegisterForm()
                },
                label = {
                    Text("Password", color = Color.Gray)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Black
                )

            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    authenticationViewModel.registerUser(navController)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black,
                    containerColor = Color.White
                )
            ) {
                Text(
                    text = "Register",
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))



            Spacer(modifier = Modifier.height(40.dp))

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ){
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(
                        text = "Do you have an account? ",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                    Text(
                        text = "Login",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .clickable {
                                authenticationViewModel.resetViewModel()
                                navController.navigate(PagesEnum.Login.name){
                                    popUpTo(PagesEnum.Register.name){
                                        inclusive = true
                                    }
                                }
                            }
                    )
                }


            }



        }


    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun RegisterPreview(){
    ALPVPTheme {
        Register(
            authenticationViewModel = viewModel(),
            navController = rememberNavController(),
            context = LocalContext.current

        )
    }
}