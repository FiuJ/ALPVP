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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Workout1DetailView(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    token: String,
    workout1DetailViewModel: Workout1DetailViewModel,
    context: Context,
    course_id: Int,
    user_id: Int
) {
    Log.d("UID Check", "Current UID early in UI: $user_id")

    val dataStatus = workout1DetailViewModel.dataStatus

    workout1DetailViewModel.getCourse1(token, course_id, false )
    Log.d("Workout1DetailView", "Current dataStatus early in UI: $dataStatus")

    LaunchedEffect(dataStatus) {
        if (dataStatus is CourseDetailDataStatusUIState.Failed) {
            Toast.makeText(context, dataStatus.errorMessage, Toast.LENGTH_SHORT).show()
            workout1DetailViewModel.clearErrorMessage()
        }
    }

    Log.d("Workout1DetailView", "Current tokn: $token")


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
                    text = "Detail",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }

        // Image Section
        Image(
            painter = painterResource(id = R.drawable.boxing),
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
            is CourseDetailDataStatusUIState.Success -> {
                // Display details from the success state
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = dataStatus.data.detail_course,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(start = 20.dp)
                    )

                    // Additional details, can be adjusted per your requirements
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Train every day for " + dataStatus.data.detail_course + " days",
                        fontSize = 15.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(8.dp))


                }
            }

            is CourseDetailDataStatusUIState.Loading -> {
                Text(
                    text = "Loading...",
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }

            is CourseDetailDataStatusUIState.Failed -> {
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
        Column(
            modifier = Modifier
                .padding(12.dp)
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

//                    onCardClick = {
//                        workout1DetailViewModel.getCourse(token, course.course_id,navController, false )
//                    }
                    Button(
                        onClick = {
                            Log.d("Workout1DetailView", "Token clicked: $token")
                            workout1DetailViewModel.createCourseUser(token, false, course_id, user_id)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE9602A))
                    ) {
                        Text(
                            text = "Enroll me",
                            color = Color.White
                        )
                    }
                }

            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun Workout1DetailViewPreview() {
    Workout1DetailView(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        navController = rememberNavController(),
        token = "",
        workout1DetailViewModel = viewModel(factory = Workout1ViewModel.Factory),
        context = LocalContext.current,
        course_id = 0,
        user_id  = 0
    )
}
