package com.example.alpvp.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alpvp.enums.PagesEnum
import com.example.alpvp.viewModels.AuthenticationViewModel
import com.example.alpvp.viewModels.ProfileViewModel

@Composable
fun BoxingApp(
    navController: NavHostController = rememberNavController(),
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
    profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory)
//    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
//    todoListFormViewModel: TodoListFormViewModel = viewModel(factory = TodoListFormViewModel.Factory),
//    todoDetailViewModel: TodoDetailViewModel = viewModel(factory = TodoDetailViewModel.Factory)
){

    val localContext = LocalContext.current
    val token = profileViewModel.token.collectAsState()

    NavHost(navController = navController, startDestination = if(token.value != "Unknown" && token.value != "") {
        PagesEnum.Home.name
    } else {
        PagesEnum.Login.name
    }) {
        composable(route = PagesEnum.Login.name) {
            Login(
                authenticationViewModel = authenticationViewModel,
                navController = navController,
                context = localContext
            )
        }

        composable(route = PagesEnum.Register.name) {
            Register(
                authenticationViewModel = authenticationViewModel,
                navController = navController,
                context = localContext
            )
        }

        composable(route = PagesEnum.Home.name) {
            Workouts1(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.White),
//                homeViewModel = homeViewModel,
//                navController = navController,
//                token = token.value,
//                todoDetailViewModel = todoDetailViewModel,
//                context = localContext
            )
        }

        composable(route = PagesEnum.Profile.name) {
            Profile(
                profileViewModel = viewModel(),
                navController = navController,
                token = token.value,
                context = localContext
            )
        }

    }


}