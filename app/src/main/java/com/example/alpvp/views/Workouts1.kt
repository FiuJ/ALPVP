package com.example.alpvp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alpvp.R

@Composable
fun Workouts1(){

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
            ){
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
                Row (
                    modifier = Modifier
                        .width(60.dp)
                        .height(32.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ){
                    Text(
                        text = "\uD83D\uDD25 2",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }


        }


    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Workouts1Preview(){
    Workouts1()
}

