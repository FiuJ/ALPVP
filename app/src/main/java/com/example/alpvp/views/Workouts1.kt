package com.example.alpvp.views

import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
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
import com.example.alpvp.models.CourseModel
import com.example.alpvp.uiStates.CourseDataStatusUIState
import com.example.alpvp.uiStates.CourseDetailDataStatusUIState
import com.example.alpvp.uiStates.CourseUserDataStatesUIStates
import com.example.alpvp.viewModels.Workout1DetailViewModel
import com.example.alpvp.viewModels.Workout1ViewModel
import com.example.alpvp.viewModels.WorkoutListViewModel
import com.example.alpvp.views.templates.CourseCardTemplate
import com.example.alpvp.views.templates.CourseCardTemplate2
import com.example.alpvp.views.templates.navBar

@Composable
fun WorkoutsScreen(
    navController: NavHostController,
    workout1ViewModel: Workout1ViewModel,
    workoutListViewModel: WorkoutListViewModel,
    workout1DetailViewModel: Workout1DetailViewModel,
    token: String,
    context: Context,
    user_id: Int
) {
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
            Workouts1(
                navController = navController,
                workout1ViewModel = workout1ViewModel,
                workoutListViewModel = workoutListViewModel,
                workout1DetailViewModel = workout1DetailViewModel,
                token = token,
                context = context,
                user_id = user_id
            )
        }
    }
}


@Composable
fun Workouts1(
    navController: NavHostController,
    workout1ViewModel: Workout1ViewModel,
    workoutListViewModel: WorkoutListViewModel,
    workout1DetailViewModel: Workout1DetailViewModel,
    token: String,
    context: Context,
    user_id: Int
) {
    val username = workout1ViewModel.username.collectAsState()

    val dataStatus = workout1ViewModel.dataStatus
    val courseDetailList = mutableListOf<CourseModel>()
    val dataStatusDetailCourse = workout1ViewModel.dataStatusDetailCourse
    val dataStatusCourseUser = workout1ViewModel.dataStatusCourseUser

    LaunchedEffect(token) {
        if (token != "Unknown") {
            workout1ViewModel.getAllCourses(token)
            workout1ViewModel.getAllCourseUserByUserID(token, user_id)
            Log.d("Check UID", "Current UID: $user_id")
        }
    }

    LaunchedEffect(dataStatus) {
        if (dataStatus is CourseDataStatusUIState.Failed) {
            Toast.makeText(context, "DATA ERROR: ${dataStatus.errorMessage}", Toast.LENGTH_SHORT)
                .show()
            workout1ViewModel.clearDataErrorMessage()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF3F1EF))
    ) {
        Row(

        ) {

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(color = Color(0xFFE9602A)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Workout",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)

        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(12.dp)
            ) {
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

                    Row(
                        modifier = Modifier
                            .padding(top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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

                    Row(
                        modifier = Modifier
                            .padding(top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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

                    Row(
                        modifier = Modifier
                            .padding(top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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

            Row(
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

//            Button(
//                onClick = {
//                    navController.navigate(PagesEnum.Profile.name) {
//                        popUpTo(PagesEnum.Home.name) {
//                            inclusive = true
//                        }
//                    }
//                }
//            ) {
//                Text(
//                    text = "Profile"
//                )
//            }

            // TODO: Read all Courses
            when (dataStatus) {
                is CourseDataStatusUIState.Success -> if (dataStatus.data.isNotEmpty()) {
                    LazyRow(
                        flingBehavior = ScrollableDefaults.flingBehavior(),
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        items(dataStatus.data) { course ->
                            CourseCardTemplate(
                                title = course.detail_course,
                                course_duration = course.course_duration,
                                modifier = Modifier
                                    .padding(bottom = 12.dp, start = 4.dp, end = 4.dp),
                                onCardClick = {
                                    workout1DetailViewModel.getCourse(
                                        token,
                                        course.course_id,
                                        navController,
                                        false
                                    )
                                }
                            )

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
                            text = "No Course Found!",
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
                        text = "Fail!",
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
//                    navController.navigate(PagesEnum.Community.name){
//                        popUpTo(PagesEnum.Home.name){
//                            inclusive = true
//                        }
//                    }
//                }
//            ) {
//                Text(
//                    text = "Community"
//                )
//            }
                

            Text(
                text = "My Courses"
            )
            when (dataStatusCourseUser) {
                is CourseUserDataStatesUIStates.Success -> {
                    if (dataStatusCourseUser.data.isNotEmpty()) {

                        dataStatusCourseUser.data.forEach { courseUser ->
                            workout1ViewModel.getCourse(token, courseUser.course_id)
                        }

                        when (dataStatusDetailCourse) {
                            is CourseDetailDataStatusUIState.Success -> {
                                courseDetailList.add(dataStatusDetailCourse.data)

                                if (courseDetailList.isNotEmpty()) {
                                    LazyRow(
                                        flingBehavior = ScrollableDefaults.flingBehavior(),
                                        modifier = Modifier
                                            .padding(vertical = 8.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                    ) {
                                        items(courseDetailList) { course ->
                                            CourseCardTemplate2 (
                                                title = course.detail_course,
                                                course_duration = course.course_duration,
                                                modifier = Modifier
                                                    .padding(bottom = 12.dp),
                                                onCardClick = {
                                                    //Todo: Workout List VM
                                                    workoutListViewModel.getWorkoutbyCourseId(token, course.course_id, navController)
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                            else -> {
                                // Show loading or error UI here if the data is not available yet
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Loading course details...",
                                        fontSize = 21.sp,
                                        fontWeight = FontWeight.Bold
                                    )
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
                                text = "Error not found",
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                is CourseUserDataStatesUIStates.Failed -> Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Fail!",
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


            // TODO: Make a navigation bar here

        }


    }
}



@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Workouts1Preview() {
    Workouts1(
        navController = rememberNavController(),
        workout1ViewModel = viewModel(factory = Workout1ViewModel.Factory),
        workoutListViewModel = viewModel (factory = WorkoutListViewModel.Factory),
        workout1DetailViewModel = viewModel(factory = Workout1DetailViewModel.Factory),
        token = "",
        context = LocalContext.current,
        user_id = 0
    )
}

