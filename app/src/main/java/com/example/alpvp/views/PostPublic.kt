package com.example.alpvp.views

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alpvp.enums.PagesEnum
import com.example.alpvp.viewModels.PostViewModel
import com.example.alpvp.views.templates.AllCommunityListCard
import com.example.alpvp.views.templates.PostListCard

@Composable
fun PostPublic(
    navController: NavHostController,
    postViewModel: PostViewModel,
    token: String,
    id: Int
){
    val publics = postViewModel.publicPost.collectAsState()

    LaunchedEffect(Unit) {
        postViewModel.getAllPublicPosts(token = token)
    }

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
                    text = "Community Post",
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
        Button(
            onClick = {
                navController.navigate(PagesEnum.Profile.name){

                }
            }
        ) {
            Text(
                text = "Profile"
            )
        }
        Column {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Public Post",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            if (publics.value.isNotEmpty()){
                LazyColumn(
                    flingBehavior = ScrollableDefaults.flingBehavior(),
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    items(publics.value) { post ->
                        PostListCard(
                            Name = post.post_name,
                            Description = post.post_description,
                            Date = post.post_date,
                            onCardClick = {},
//                            commentViewModel = viewModel(factory = PostViewModel.Factory),
//                            post_id = post.post_id
                        )
                    }
                }
            }
            else{
                Text(
                    text = "Theres no post yet",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PostPublicPreview(){
    PostPublic(
        navController = rememberNavController(),
        postViewModel = viewModel(factory = PostViewModel.Factory),
        token = "",
        id = 0
    )
}