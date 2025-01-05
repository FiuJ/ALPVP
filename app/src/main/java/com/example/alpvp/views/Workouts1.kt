package com.example.alpvp.views

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableDefaults
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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alpvp.R
import com.example.alpvp.enums.PagesEnum
import com.example.alpvp.uiStates.CourseDataStatusUIState
import com.example.alpvp.viewModels.Workout1DetailViewModel
import com.example.alpvp.viewModels.Workout1ViewModel
import com.example.alpvp.views.templates.CourseCardTemplate

@Composable
fun Workouts1(
    navController: NavHostController,
    workout1ViewModel: Workout1ViewModel,
    workout1DetailViewModel: Workout1DetailViewModel,
    token: String,
    context: Context,

    ){
    val username = workout1ViewModel.username.collectAsState()
    val dataStatus = workout1ViewModel.dataStatus

    LaunchedEffect(token) {
        if (token != "Unknown") {
            workout1ViewModel.getAllCourses(token)
        }
    }

    LaunchedEffect(dataStatus) {
        if (dataStatus is CourseDataStatusUIState.Failed) {
            Toast.makeText(context, "DATA ERROR: ${dataStatus.errorMessage}", Toast.LENGTH_SHORT).show()
            workout1ViewModel.clearDataErrorMessage()
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF3F1EF))
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(color = Color(0xFFB13B1A))
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
                    text = "Workout",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Row (
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Image(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Color(0xFFE9602A)),
                        modifier = Modifier.size(26.dp)
                    )
                    Text(
                        text = "Search",
                        color = Color(0xFFE9602A),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(end = 8.dp)
                    )

                }
            }
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)

        ){
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(12.dp)
            ){
                Column {
                    Text(
                        text = "Getting started",
                        fontSize = 18.sp
                    )

                    Text(
                        text = "to get the most mentally and physically,",
                        fontSize = 13.sp,
                        modifier = Modifier
                            .padding(top = 8.dp),
                        color = Color.DarkGray
                    )
                    Text(
                        text = "you need to do these:",
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )

                    Row (
                        modifier = Modifier
                            .padding(top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.baseline_search_24),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.Black),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Join the class you are interested the most",
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                    }

                    Row (
                        modifier = Modifier
                            .padding(top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.baseline_search_24),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.Black),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Join the community suits you",
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                    }

                    Row (
                        modifier = Modifier
                            .padding(top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.baseline_search_24),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.Black),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Turn on notification ",
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                    }
                }

            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Daily Streaks",
                        fontSize = 18.sp
                    )
                    Text(
                        text = "You are highly capable to do stuff!\uD83D\uDD25\uD83D\uDD25",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .width(60.dp)
                        .height(32.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ) {
                    Text(
                        text = "\uD83D\uDD25 2",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

                // TODO: Read all Courses
                when (dataStatus) {
                    is CourseDataStatusUIState.Success -> if (dataStatus.data.isNotEmpty()) {
                        LazyColumn(
                            flingBehavior = ScrollableDefaults.flingBehavior(),
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .clip(RoundedCornerShape(10.dp))
                        ) {
                            items(dataStatus.data) { course ->
                                CourseCardTemplate (
                                    title = course.course_title,
                                    course_duration = course.course_duration,
                                ){

                                }
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "No Task Found!",
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    is CourseDataStatusUIState.Failed -> Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "No Task Found!",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    else -> Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Wait",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }





//            Button(
//                onClick = {
//                    navController.navigate(PagesEnum.Profile.name){
//                        popUpTo(PagesEnum.Home.name){
//                            inclusive = true
//                        }
//                    }
//                }
//            ) {
//                Text(
//                    text = "Profile"
//                )
//            }

            // TODO: Make a navigation bar here

        }


    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Workouts1Preview(){
    Workouts1(
        navController = rememberNavController(),
        workout1ViewModel = viewModel (factory= Workout1ViewModel.Factory),
        workout1DetailViewModel = viewModel(factory= Workout1DetailViewModel.Factory),
        token = "",
        context = LocalContext.current
    )
}

