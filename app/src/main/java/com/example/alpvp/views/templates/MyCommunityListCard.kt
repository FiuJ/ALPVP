package com.example.alpvp.views.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alpvp.R
import com.example.alpvp.enums.PagesEnum

@Composable
fun MyCommunityListCard(
    CommunityName: String,
    Description: String,
    onCardClick: () -> Unit,
    navController: NavHostController
){
    Card(
        modifier = Modifier
            .padding(bottom = 4.dp),
        onClick = onCardClick,
    ) {
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
                        painter = painterResource(R.drawable.profile),
                        contentDescription = "",
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                    )
                    Column (
                        modifier = Modifier
                            .padding(start = 4.dp)
                    ){
                        Text(
                            text = "$CommunityName",
                            fontSize = 18.sp,
                        )
                        Text(
                            text = "$Description",
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }



                Button(
                    onClick = {
                        navController.navigate(PagesEnum.CommunityPost.name){

                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFFFFF),
                        contentColor = Color(0xFFE9602A)
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_chevron_right_24),
                        contentDescription = ""
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyCommunityListCardPreview(){
    MyCommunityListCard(
        CommunityName = "Boxing Hit",
        Description = "Boxing training to motivated people",
        onCardClick = {},
        navController = rememberNavController()
    )
}