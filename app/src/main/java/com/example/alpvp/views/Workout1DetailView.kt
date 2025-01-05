package com.example.alpvp.views

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.alpvp.uiStates.CourseDetailDataStatusUIState
import com.example.alpvp.viewModels.Workout1DetailViewModel
import com.example.alpvp.viewModels.Workout1ViewModel

@Composable
fun Workout1DetailView(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    token: String,
    workout1DetailViewModel: Workout1DetailViewModel,
    context: Context,
){
    val dataStatus = workout1DetailViewModel.dataStatus

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
    Row(
        modifier = Modifier
            .width(200.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
            painter = painterResource(id = R.drawable.boxing),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop // This ensures the image covers the full area
        )
    }


    when (dataStatus) {
        is CourseDetailDataStatusUIState.Success -> Column(
            modifier = Modifier
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Text(
                    text = dataStatus.data.detail_course,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(top = 16.dp)
                )

                Column {
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
                            text = "Train everyday for " + dataStatus.data.detail_course + " days",
                            fontSize = 13.sp,
                            modifier = Modifier
                                .padding(top = 8.dp),
                            color = Color.DarkGray
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
                            text = "Community Available",
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
        context = LocalContext.current
    )
}