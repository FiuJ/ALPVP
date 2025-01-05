package com.example.alpvp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun Community(){

    var selectedTab by remember { mutableStateOf("My Journal") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF3F1EF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(color = Color(0xFFB13B1A))
        ) {

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
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
                    text = "Community",
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

                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .background(color = Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Button(
                    modifier = Modifier
                        .padding(start = 20.dp),
                    onClick = { selectedTab = "My Journal"},
                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xFFE9602A))
                        containerColor = if (selectedTab == "My Journal") Color(0xFFE9602A) else Color(0xFFF3F1EF),
                        contentColor = if (selectedTab == "My Journal") Color.White else Color.Black
                    )

                ) {
                    Text(
                        text = "My Journal"
                    )
                }
                Button(
                    modifier = Modifier
                        .padding(start = 12.dp),
                    onClick = { selectedTab = "Communities"},
                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xFFF3F1EF), contentColor = Color.Black
                        containerColor = if (selectedTab == "Communities") Color(0xFFE9602A) else Color(0xFFF3F1EF),
                        contentColor = if (selectedTab == "Communities") Color.White else Color.Black
                    )
                ) {
                    Text(
                        text = "Communities"
                    )
                }
            }
        }


        when(selectedTab){
            "My Journal" -> MyJournalContent()
            "Communities" -> CommunitiesContent()
        }


    }
}

@Composable
fun MyJournalContent(){
    Column (
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxSize()
            .background(color = Color(0xFFF3F1EF))
    ){
        Row {

        }
    }
}

@Composable
fun CommunitiesContent(){

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CommunityPreview(){
    Community()
}