package com.example.alpvp.views.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alpvp.R
import com.example.alpvp.enums.PagesEnum
import com.example.alpvp.viewModels.PostViewModel

@Composable
fun MyPostListCard(
    Name: String,
    Description: String,
    Date: String,
    isPublic: Boolean,
    onCardClick: () -> Unit,
    postViewModel: PostViewModel,
    token: String,
    postId: Int,
    id: Int,
    navController: NavHostController

){

    LaunchedEffect(Unit) {
        postViewModel.getAllPostsByUser(token, id)
    }

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



                    if(isPublic) {
                        Text(
                            text = "Public",
                            fontSize = 15.sp,
                            color = Color(0xFF1FBB4B)
                        )
                    }else{
                        Text(
                            text = "Private",
                            fontSize = 15.sp,
                            color = Color.Red
                        )
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
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Image(
                        painter = painterResource(R.drawable.baseline_thumb_up_24),
                        contentDescription = "",
                        modifier = Modifier
                            .clickable {  },
                        colorFilter = ColorFilter.tint(
                            Color.Gray
                        )
                    )
                    Row {
                        Text(
                            text = "Edit",
                            color = Color(0xFFE9602A),
                            fontSize = 17.sp,
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(PagesEnum.UpdatePost.name+"/$postId")
                                }
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "Delete",
                            color = Color.Red,
                            fontWeight = FontWeight.Medium,
                            fontSize = 17.sp,
                            modifier = Modifier
                                .clickable {
                                    postViewModel.deletePost(token, postId)
                                } // delete
                        )
                    }

                }
            }

            Column {
                //Buat Lazy Comment (SHOW ONLY)
            }

        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyPostListCardPreview(){
    MyPostListCard(
        Name = "Gabriella",
        Description = "I feel great trying boxing for the first time! Such a thrilling experience",
        Date = "28 December 2024",
        isPublic = true,
        onCardClick = {},
        postViewModel = viewModel(factory = PostViewModel.Factory),
        token = "",
        postId = 0,
        id = 0,
        navController = rememberNavController()
    )
}