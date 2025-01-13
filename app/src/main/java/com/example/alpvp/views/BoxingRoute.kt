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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.alpvp.enums.PagesEnum
import com.example.alpvp.viewModels.AuthenticationViewModel

import com.example.alpvp.viewModels.DetailWorkoutViewModel

import com.example.alpvp.viewModels.CommunityViewModel
import com.example.alpvp.viewModels.PostViewModel

import com.example.alpvp.viewModels.ProfileViewModel
import com.example.alpvp.viewModels.Workout1DetailViewModel
import com.example.alpvp.viewModels.Workout1ViewModel
import com.example.alpvp.viewModels.WorkoutListViewModel

@Composable
fun BoxingApp(
    navController: NavHostController = rememberNavController(),
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
    profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory),

    workout1DetailViewModel: Workout1DetailViewModel = viewModel(factory = Workout1DetailViewModel.Factory),
    workoutListViewModel: WorkoutListViewModel = viewModel (factory = WorkoutListViewModel.Factory),
    detailWorkoutViewModel: DetailWorkoutViewModel = viewModel (factory = DetailWorkoutViewModel.Factory),



    postViewModel: PostViewModel = viewModel(factory = PostViewModel.Factory),

//    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
//    todoListFormViewModel: TodoListFormViewModel = viewModel(factory = TodoListFormViewModel.Factory),
//    todoDetailViewModel: TodoDetailViewModel = viewModel(factory = TodoDetailViewModel.Factory)
){

    val localContext = LocalContext.current
    val token = profileViewModel.token.collectAsState()

    val user_id = profileViewModel.user_id.collectAsState()

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
                context = localContext,
                user_id = user_id.value,
                workoutListViewModel = viewModel(factory = WorkoutListViewModel.Factory),
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


        composable(route = PagesEnum.CourseDetail.name+"/{course_id}",
            arguments = listOf(navArgument("course_id") {type = NavType.IntType})) { backStackEntry ->

            val course_id = backStackEntry.arguments?.getInt("course_id") ?: 0

            Workout1DetailView(
                modifier = Modifier,
                navController = navController,
                token = token.value,
                context = localContext,
                workout1DetailViewModel = workout1DetailViewModel,
                course_id = course_id,
                user_id = user_id.value
            )
        }

        composable(route = PagesEnum.workoutList.name+"/{course_id}",
            arguments = listOf(navArgument("course_id") {type = NavType.IntType})) { backStackEntry ->

            val course_id = backStackEntry.arguments?.getInt("course_id") ?: 0

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
//        composable(route = PagesEnum.UpdatePost.name + "/{id}", arguments = listOf(
//            navArgument(name = "id") {
//                type = NavType.IntType
//            }
//        )){
//            val id = backStackEntry.arguments?.getInt("id")
//            UpdatePost(
//                navController = navController,
//                postViewModel = viewModel(factory = PostViewModel.Factory),
//                token = token.value,
//                postId =
//            )
//        }
        composable(route = PagesEnum.UpdatePost.name + "/{postId}",
            arguments = listOf(
                navArgument(name = "postId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getInt("postId")
            UpdatePost(
                navController = navController,
                postViewModel = postViewModel,
                token = token.value,
                postId = postId!!
            )
        }

    }


            workoutList(
                workoutListViewModel = workoutListViewModel,
                detailWorkoutViewModel = detailWorkoutViewModel,
                navController = navController,
                token = token.value,
                user_id = user_id.value,
                context = localContext,
                course_id = course_id,
            )
        }
//

        composable(route = PagesEnum.workoutDetail.name+"/{workout_id}",
            arguments = listOf(navArgument("workout_id") {type = NavType.IntType})) { backStackEntry ->

            val workout_id = backStackEntry.arguments?.getInt("workout_id") ?: 0

            DetailWorkout(
                detailWorkoutViewModel = detailWorkoutViewModel,
                modifier = Modifier,
                navController = navController,
                token = token.value,
                user_id = user_id.value,
                context = localContext,
                workout_id = workout_id,
            )
        }

    }
}