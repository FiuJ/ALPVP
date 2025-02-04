package com.example.alpvp.views

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alpvp.R
import com.example.alpvp.uiStates.StringDataStatusUIState
import com.example.alpvp.viewModels.ProfileViewModel
import android.util.Log
import androidx.compose.material3.Scaffold
import com.example.alpvp.views.templates.navBar

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = viewModel(),
    navController: NavHostController,
    token: String,
    context: Context
){
    Scaffold(
        topBar = {},
        // Use the bottomBar slot to include your navBar
        bottomBar = {
            navBar(navController = navController)
        }
    ) { innerPadding ->
        // Apply the innerPadding to avoid overlapping the navBar
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            Profile(
                profileViewModel = profileViewModel,
                navController = navController,
                token = token,
                context = context
            )
        }
    }

}

@Composable
fun Profile(
    profileViewModel: ProfileViewModel = viewModel(),
    navController: NavHostController,
    token: String,
    context: Context
){
    val username = profileViewModel.username.collectAsState()
    Log.d("token-profile", "TOKEN: ${token}")
    val logoutStatus = profileViewModel.logoutStatus

    LaunchedEffect(logoutStatus) {
        if (logoutStatus is StringDataStatusUIState.Failed) {
            Toast.makeText(context, "LOGOUT ERROR: ${logoutStatus.errorMessage}", Toast.LENGTH_SHORT).show()
            profileViewModel.clearLogoutErrorMessage()
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF3F1EF))
    ){
        Row (

        ){

        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(color = Color(0xFFE9602A)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Detail",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )

            }
        }


        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ){

            Text(
                text = username.value,
//                text = "Nama Username",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Button(
                onClick = {
                    profileViewModel.logoutUser(token, navController)
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Log Out"
                )
            }

        }




    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfilePreview(){
    Profile(
        profileViewModel = viewModel(factory = ProfileViewModel.Factory),
        navController = rememberNavController(),
        token = "",
        context = LocalContext.current
    )
}