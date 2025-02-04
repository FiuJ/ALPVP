package com.example.alpvp.views.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alpvp.R
import com.example.alpvp.enums.PagesEnum

@Composable
fun navBar(navController: NavHostController) {
    Column {
        Divider(
            color = Color.LightGray,
            thickness = 1.dp
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Example nav item: Calendar
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        // Navigate to Workout page, for example
                        navController.navigate(PagesEnum.Home.name)
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "Workout",
                        colorFilter = ColorFilter.tint(Color(0xFFE9602A)),
                        modifier = Modifier.size(26.dp)
                    )
                    Text(
                        text = "Workout",
                        color = Color(0xFFE9602A),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                // Add additional nav items similarly:
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        navController.navigate(PagesEnum.Community.name)
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = "Search",
                        colorFilter = ColorFilter.tint(Color(0xFFE9602A)),
                        modifier = Modifier.size(26.dp)
                    )
                    Text(
                        text = "Community",
                        color = Color(0xFFE9602A),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        navController.navigate(PagesEnum.Profile.name)
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_tag_faces_24),
                        contentDescription = "Profile",
                        colorFilter = ColorFilter.tint(Color(0xFFE9602A)),
                        modifier = Modifier.size(26.dp)
                    )
                    Text(
                        text = "Profile",
                        color = Color(0xFFE9602A),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}


//@Composable
//fun navBar(){
//
//    Column {
//        Divider(
//            color =Color.LightGray,
//            thickness = 2.dp
//        )
//        Column (
//            modifier = Modifier
//                .padding(top = 100.dp)
//                .fillMaxWidth()
//                .height(80.dp)
//                .background(color = Color.White),
//            verticalArrangement = Arrangement.Center
//        ){
//            Row (
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 20.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ){
//                Column (
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ){
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_calendar),
//                        contentDescription = "",
//                        colorFilter = ColorFilter.tint(Color(0xFFE9602A)),
//                        modifier = Modifier.size(26.dp)
//                    )
//                    Text(
//                        text = "Workout",
//                        color = Color(0xFFE9602A),
//                        modifier = Modifier
//                            .padding(top = 4.dp)
//
//                    )
//                }
//                Column (
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ){
//                    Image(
//                        painter = painterResource(id = R.drawable.baseline_search_24),
//                        contentDescription = "",
//                        colorFilter = ColorFilter.tint(Color(0xFFE9602A)),
//                        modifier = Modifier.size(26.dp)
//                    )
//                    Text(
//                        text = "Workout",
//                        color = Color(0xFFE9602A),
//                        modifier = Modifier
//                            .padding(top = 4.dp)
//
//                    )
//                }
//                Column (
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ){
//                    Image(
//                        painter = painterResource(id = R.drawable.baseline_tag_faces_24),
//                        contentDescription = "",
//                        colorFilter = ColorFilter.tint(Color(0xFFE9602A)),
//                        modifier = Modifier.size(26.dp)
//                    )
//                    Text(
//                        text = "Workout",
//                        color = Color(0xFFE9602A),
//                        modifier = Modifier
//                            .padding(top = 4.dp)
//
//                    )
//                }
//            }
//        }
//    }
//
//
//
//}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun navBarPreview(){
    navBar(
        navController = rememberNavController()
    )
}