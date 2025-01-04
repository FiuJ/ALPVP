package com.example.alpvp.View

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alpvp.viewModels.AuthenticationViewModel

@Composable
fun BoxingApp(
    navController: NavHostController = rememberNavController(),
//    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
//    todoListFormViewModel: TodoListFormViewModel = viewModel(factory = TodoListFormViewModel.Factory),
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
//    todoDetailViewModel: TodoDetailViewModel = viewModel(factory = TodoDetailViewModel.Factory)
){

}