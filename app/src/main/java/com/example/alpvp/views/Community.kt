package com.example.alpvp.views

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alpvp.R
import com.example.alpvp.models.CommunityModel
import com.example.alpvp.uiStates.CommunityStatusUIState
import com.example.alpvp.uiStates.CourseDataStatusUIState
import com.example.alpvp.viewModels.CommunityViewModel
import com.example.alpvp.views.templates.AllCommunityListCard

@Composable
fun Community(
    navController: NavHostController,
    communityViewModel: CommunityViewModel,
    id: Int,
    token: String,
    context: Context
){

    val dataStatus = communityViewModel.dataStatus
    var selectedTab by remember { mutableStateOf("Communities") }
    val allCommunities = communityViewModel.allCommunities.collectAsState()
    val communityById = communityViewModel.communitiesById.collectAsState()

    LaunchedEffect(Unit) {
        communityViewModel.getAllCommunities()
        communityViewModel.getAllCommunitiesByUserId(id)
    }

    LaunchedEffect(dataStatus) {
        if (dataStatus is CommunityStatusUIState.Failed) {
            Toast.makeText(context, "DATA ERROR: ${dataStatus.errorMessage}", Toast.LENGTH_SHORT).show()
            communityViewModel.clearDataErrorMessage()
        }
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
            "Communities" -> CommunitiesContent(
                dataStatus,
                allCommunities = allCommunities.value,
                communityById = communityById.value
            )
        }


    }
}

@Composable
fun MyJournalContent(){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),

        ){
        Row {

        }

    }
}

@Composable
fun CommunitiesContent(
    dataStatus: CommunityStatusUIState,
    allCommunities: List<CommunityModel>,
    communityById: List<CommunityModel>
){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),

    ){
        Text(
            text = "Community List",
            fontSize = 18.sp
        )

        if(allCommunities.isNotEmpty()){
            LazyColumn(
                flingBehavior = ScrollableDefaults.flingBehavior(),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                items(allCommunities) { community ->
                    AllCommunityListCard(
                        CommunityName = community.name,
                        onCardClick = {} // Route ke Community
                    )
                }
            }
        }
        else{
            Text(
                text = "No Community Found!",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
        }





        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "My Community",
            fontSize = 18.sp
        )
        if(communityById.isNotEmpty()){
            LazyColumn(
                flingBehavior = ScrollableDefaults.flingBehavior(),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                items(communityById) { community ->
                    AllCommunityListCard(
                        CommunityName = community.name,
                        onCardClick = {} // Route ke Community
                    )
                }
            }
        }
        else{
            Text(
                text = "You need join a Community!",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
        }



    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CommunityPreview(){
    Community(
        navController = rememberNavController(),
        communityViewModel = viewModel(factory = CommunityViewModel.Factory),
        id = 0,
        token = "",
        context = LocalContext.current
    )
}