package com.example.alpvp.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePost(
    navController: NavHostController,
    postViewModel: PostViewModel,
    token: String,
    postId: Int
){
    var isPublic by remember { mutableStateOf(false) }

    Log.d("UpdatePost", "postId: $postId")

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
                    text = "Update Post",
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


        //BUTTON SEMENTARA BUAT CEK HALAMAN
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

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ){
            OutlinedTextField(
                value = postViewModel.post_name_input,
                onValueChange = {
                    postViewModel.setName(it)
                },
                label = {
                    Text("Post Name", color = Color.Black)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Gray
                )
            )

            OutlinedTextField(
                value = postViewModel.post_description_input,
                onValueChange = {
                    postViewModel.setDescription(it)
                },
                label = {
                    Text("Description", color = Color.Black)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Gray
                ),
                maxLines = 3,
                singleLine = false
            )


            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row (
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    RadioButton(
                        selected = postViewModel.isPublicInput,
                        onClick = { postViewModel.setIsPublic(true) },
                        colors = RadioButtonDefaults.colors(selectedColor = Color.Blue)
                    )
                    Text(
                        text = "True",
                        fontSize = 16.sp,
                        modifier = Modifier
//                            .clickable { postViewModel.setIsPublic(true) }
                    )
                }

                Row (
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    RadioButton(
                        selected = !postViewModel.isPublicInput,
                        onClick = { postViewModel.setIsPublic(false) },
                        colors = RadioButtonDefaults.colors(selectedColor = Color.Blue)
                    )
                    Text(
                        text = "False",
                        fontSize = 16.sp,
                        modifier = Modifier
//                            .clickable { postViewModel.setIsPublic(false) }
                    )
                }

            }

            Button(
                onClick = {
                    postViewModel.updatePost(token, postId, navController)
                },
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE9602A),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Create",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                )
            }



        }




    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun UpdatePostPreview(){
    UpdatePost(
        navController = rememberNavController(),
        postViewModel = viewModel(factory = PostViewModel.Factory),
        token = "",
        postId = 0
    )

}