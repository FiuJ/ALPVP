package com.example.alpvp.views.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alpvp.R

@Composable
fun PostListCard(
    Name: String,
    Description: String,
    Date: String,
    onCardClick: () -> Unit

){
    val isLike = remember { mutableStateOf(false) }
    Card(
        onClick = onCardClick,
        modifier = Modifier.padding(bottom = 4.dp)
    ) {

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
        ){
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Row (
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 4.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Image(
                            painter = painterResource(R.drawable.post_profile),
                            contentDescription = "",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                        Column (
                            modifier = Modifier
                                .padding(start = 12.dp)
                        ){
                            Text(
                                text = "$Name",
                                fontSize = 18.sp,
                            )
                            Text(
                                text = "$Date",
                                fontWeight = FontWeight.Light,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }



                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFFFFF),
                            contentColor = Color(0xFFE9602A)
                        )
                    ) {

                    }
                }

            }

            Column (
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ){
                Text(
                    text = "$Description",
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                )

                Image(
                    painter = painterResource(R.drawable.postimg),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.FillWidth
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Start
                ){
                    Image(
                        painter = painterResource(R.drawable.baseline_thumb_up_24),
                        contentDescription = "",
                        modifier = Modifier
                            .clickable { isLike.value = !isLike.value },
                        colorFilter = ColorFilter.tint(
                            if (isLike.value) Color.Red else Color.Gray
                        )
                    )
                }
            }
            Column {
                //Buat Lazy Comment, bawahnya lazy ada form add Comment
            }

        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PostListCardPreview(){
    PostListCard(
        Name = "Gabriella",
        Description = "I feel great trying boxing for the first time! Such a thrilling experience",
        Date = "28 December 2024",
        onCardClick = {}
    )
}