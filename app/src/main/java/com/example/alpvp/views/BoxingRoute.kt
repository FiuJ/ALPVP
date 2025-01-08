package com.example.alpvp.views

import android.graphics.pdf.PdfDocument.Page
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
import com.example.alpvp.viewModels.CommunityViewModel
import com.example.alpvp.viewModels.PostViewModel
import com.example.alpvp.viewModels.ProfileViewModel
import com.example.alpvp.viewModels.Workout1DetailViewModel
import com.example.alpvp.viewModels.Workout1ViewModel

@Composable
fun BoxingApp(
    navController: NavHostController = rememberNavController(),
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
    profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory),
//    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
//    todoListFormViewModel: TodoListFormViewModel = viewModel(factory = TodoListFormViewModel.Factory),
//    todoDetailViewModel: TodoDetailViewModel = viewModel(factory = TodoDetailViewModel.Factory)
){

    val localContext = LocalContext.current
    val token = profileViewModel.token.collectAsState()
    val username = profileViewModel.username.collectAsState()
    val id = profileViewModel.id.collectAsState()


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
                navController = navController, // Use the shared instance here
                workout1ViewModel = viewModel(factory = Workout1ViewModel.Factory),
                workout1DetailViewModel = viewModel(factory = Workout1DetailViewModel.Factory),
                token = token.value, // Use the collected token value here
                context = localContext
            )
        }

        composable(route = PagesEnum.Profile.name) {
            Profile(
                profileViewModel = viewModel(factory = ProfileViewModel.Factory),
                navController = navController,
                token = token.value,
                context = localContext
            )
        }

        composable(route = PagesEnum.Community.name) {
            Community(
                navController = navController,
                communityViewModel = viewModel(factory = CommunityViewModel.Factory),
                postViewModel = viewModel(factory = PostViewModel.Factory),
                id = id.value,
                token = token.value,
                context = localContext
            )
        }

        composable(route = PagesEnum.CommunityPost.name){
            PostPublic(
                navController = navController,
                postViewModel = viewModel(factory = PostViewModel.Factory),
                token = token.value,
                id = id.value
            )
        }
        composable(route = PagesEnum.CreatePost.name){
            CreatePost(
                navController = navController,
                postViewModel = viewModel(factory = PostViewModel.Factory),
                token = token.value,
                id = id.value
            )
        }
        composable(route = PagesEnum.UpdatePost.name){
            UpdatePost(
                navController = navController,
                postViewModel = viewModel(factory = PostViewModel.Factory),
                token = token.value,
                postId =
            )
        }

    }


}