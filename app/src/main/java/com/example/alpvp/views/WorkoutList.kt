package com.example.alpvp.views

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alpvp.R
import com.example.alpvp.models.WorkoutModel
import com.example.alpvp.uiStates.CourseDataStatusUIState
import com.example.alpvp.uiStates.CourseDetailDataStatusUIState
import com.example.alpvp.uiStates.CourseUserDataStatesUIStates
import com.example.alpvp.uiStates.WorkoutCourseDataStatesUIState
import com.example.alpvp.uiStates.WorkoutDataStatesUIState
import com.example.alpvp.viewModels.DetailWorkoutViewModel
import com.example.alpvp.viewModels.Workout1DetailViewModel
import com.example.alpvp.viewModels.Workout1ViewModel
import com.example.alpvp.viewModels.WorkoutListViewModel
import com.example.alpvp.views.templates.CourseCardTemplate
import com.example.alpvp.views.templates.WorkoutCardTemplate

//this page is for: getting all workout_id in a course using course_workout table
// passing workout_id from course_workout to access workout_name, days, and duration
// showing the workouts in a lazy column
// adding isDone button below , to add performance level in user


@Composable
fun workoutList(
    workoutListViewModel: WorkoutListViewModel,
    detailWorkoutViewModel: DetailWorkoutViewModel,
    navController: NavHostController,
    token: String,
    course_id: Int,
    context: Context,
    user_id: Int
){
    val dataStatus = workoutListViewModel.dataStatus
    val dataStatusWorkout = workoutListViewModel.dataStatusWorkout
    var workoutList = mutableListOf<WorkoutModel>()

    LaunchedEffect(token) {
        if (token != "Unknown") {
            Log.d("Check Token", "Current token: $token")
            Log.d("Check CID", "Current token: $course_id")
            Log.d("Check UID", "Current UID: $user_id")

        }
    }
    LaunchedEffect(dataStatus) {
        if (dataStatus is WorkoutCourseDataStatesUIState.Failed) {
            Toast.makeText(context, "DATA ERROR: ${dataStatus.errorMessage}", Toast.LENGTH_SHORT)
                .show()
            workoutListViewModel.clearDataErrorMessage()
        }
    }

    workoutListViewModel.getWorkoutbyCourseIdDuplicate(token, course_id)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF3F1EF))
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(color = Color(0xFFB13B1A))
        ){}
        Row (
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
                    text = "Workouts.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.boxing),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Set a fixed height to avoid stretching
            ,
            contentScale = ContentScale.Crop
        )
        Text(
            text = " Your Workout Daily Dose ", // Added emojis for a motivational touch
            fontSize = 24.sp,                     // Increased font size for better visibility
            fontWeight = FontWeight.Bold,         // Made the text bold for emphasis
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally), // Centered the text horizontally
            textAlign = TextAlign.Center           // Centered the text alignment inside the Text composable
        )

//        relasi dulu yg di define
        when (dataStatus) {
            is WorkoutCourseDataStatesUIState.Success -> {
                if (dataStatus.data.isNotEmpty()) {

                    dataStatus.data.forEach { courseWorkout ->
                        Log.d("Check CW", "Current CW: $courseWorkout")
                        workoutListViewModel.getWorkoutbyWorkoutId(token, courseWorkout.workout_id)
                    }

                    when (dataStatusWorkout) {
                        is WorkoutDataStatesUIState.Success -> {
                            workoutList.add(dataStatusWorkout.data)

                            if (workoutList.isNotEmpty()) {
                                LazyColumn(
                                    flingBehavior = ScrollableDefaults.flingBehavior(),
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                ) {
                                    items(workoutList) { workout ->
                                        WorkoutCardTemplate(
                                            day = 3,
                                            workoutName = workout.name_workout,
                                            duration = workout.workout_duration,
                                            onStartClick = {
                                                detailWorkoutViewModel.getWorkoutbyWorkoutId(token, navController,workout.workout_id)
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
                            text = "Enroll class now",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            is WorkoutCourseDataStatesUIState.Failed -> Column(
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

//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(20.dp)
//        ) {
//            Button(
//                onClick = {
//                    // Button click logic here
//                },
//                modifier = Modifier
//                    .fillMaxWidth(),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color(0xFFB13B1A) // Applying the custom color here
//                )
//            ) {
//                Text(
//                    text = "Finish Workout"
//                )
//            }
//        }
    }
}



@Composable
@Preview(showBackground = true, showSystemUi = true)
fun WorkoutListPreview() {
    workoutList(
        workoutListViewModel = viewModel(factory = WorkoutListViewModel.Factory),
        detailWorkoutViewModel = viewModel (factory = DetailWorkoutViewModel.Factory),
        navController = rememberNavController(),
        token = "",
        user_id = 1,
        course_id = 1,
        context = LocalContext.current
    )
}

