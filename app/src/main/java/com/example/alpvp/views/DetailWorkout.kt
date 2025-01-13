package com.example.alpvp.views


import android.R.attr.onClick
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.alpvp.viewModels.Workout1DetailViewModel
import com.example.alpvp.viewModels.Workout1ViewModel
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewModelScope
import com.example.alpvp.uiStates.CourseDetailDataStatusUIState
import com.example.alpvp.uiStates.WorkoutDataStatesUIState
import com.example.alpvp.viewModels.DetailWorkoutViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DetailWorkout(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    token: String,
    detailWorkoutViewModel: DetailWorkoutViewModel,
    context: Context,
    workout_id: Int,
    user_id: Int
) {
    Log.d("UID Check", "Current UID early in UI: $user_id")
    Log.d("WID Check", "Current UID early in UI: $workout_id")

    val remainingTime = detailWorkoutViewModel.remainingTime
    val isTimerRunning = detailWorkoutViewModel.isTimerRunning

    val dataStatus = detailWorkoutViewModel.dataStatusWorkout
    detailWorkoutViewModel.getWorkoutbyWorkoutIdDuplicate(token, workout_id)

    LaunchedEffect(dataStatus) {
        if (dataStatus is WorkoutDataStatesUIState.Failed) {
            Toast.makeText(context, dataStatus.errorMessage, Toast.LENGTH_SHORT).show()
            detailWorkoutViewModel.clearErrorMessage()
        }
    }


    // Header
    Column(
        modifier = modifier
            .fillMaxSize()
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
                    text = "Detail Workout",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }

        // Image Section
        Image(
            painter = painterResource(id = R.drawable.squat),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Set a fixed height to avoid stretching
            ,
            contentScale = ContentScale.Crop
        )

        // Handling different states of dataStatus
        when (dataStatus) {
            //why dataStatus is not Success?
            is WorkoutDataStatesUIState.Success -> {
                // Display details from the success state
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = dataStatus.data.name_workout,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )

                    // Additional details, can be adjusted per your requirements
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Your Duration is for " + dataStatus.data.workout_duration + " mins",
                        fontSize = 15.sp,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "Time Left: ${remainingTime / 60}:${String.format("%02d", remainingTime % 60)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Timer Control Buttons
                    Row {
                        Button(
                            onClick = {
                                detailWorkoutViewModel.startTimer(dataStatus.data.workout_duration)
                            },
                            enabled = !isTimerRunning
                        ) {
                            Text("Start Timer")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = { detailWorkoutViewModel.stopTimer() },
                            enabled = isTimerRunning
                        ) {
                            Text("Stop Timer")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
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
                                        text = dataStatus.data.detail_workout,
                                        fontSize = 15.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }

            is WorkoutDataStatesUIState.Loading -> {
                Text(
                    text = "Loading...",
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }

            is WorkoutDataStatesUIState.Failed -> {
                Log.d("Workout1DetailView", "Current dataStatus in UI: $dataStatus")
                Text(
                    text = "Error loading data.",
                    color = Color.Red,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }

            else -> {
                Log.d("Workout1DetailView", "Current dataStatus in UI: $dataStatus")
                Text(
                    text = "Waiting for data...",
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DetailWorkoutPreview() {
    DetailWorkout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        navController = rememberNavController(),
        token = "",
        detailWorkoutViewModel = viewModel(factory = DetailWorkoutViewModel.Factory),
        context = LocalContext.current,
        workout_id = 0,
        user_id  = 0
    )
}
