package com.example.alpvp.views.templates

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import com.example.alpvp.R

@Composable
fun CourseCardTemplate(
    title: String,
    course_duration: Int,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit
) {
    Card(
        onClick = onCardClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {



        Row(
            modifier = Modifier
                .width(200.dp)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(4f)
                    .height(250.dp)
            ) {
                // Background image using Image composable
                Image(
                    painter = painterResource(id = R.drawable.boxing),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop // This ensures the image covers the full area
                )

                // Column to hold text (title, duration) on top of the image
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.BottomStart)
                ) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$course_duration hours",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
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
fun CourseCardTemplatePreview() {
    CourseCardTemplate(
        title = "Cardio Workout",
        course_duration = 12,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onCardClick = {}
    )
}
