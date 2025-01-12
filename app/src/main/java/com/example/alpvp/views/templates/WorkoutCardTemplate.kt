package com.example.alpvp.views.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.draw.clip
import com.example.alpvp.R

@Composable
fun WorkoutCardTemplate(
    day: Int,
    workoutName: String,
    duration: Int,
    onStartClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Image Section
        Image(
            painter = painterResource(id = R.drawable.squat),
            contentDescription = "Squatting",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Text Section
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Day $day", // Adjusted to display "Day X"
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = workoutName, fontSize = 18.sp)
            Text(text = "$duration Minutes", fontSize = 16.sp, color = Color.Gray)
        }

        // Button Section with Gray Background and Orange Text
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color(0xFFE7E7E7))  // Gray button background
                .clickable { onStartClick() }
                .padding(16.dp)
        ) {
            Text(
                text = "Start",
                color = Color(0xFFE9602A), // Orange text color
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun WorkoutCardTemplatePreview() {
    WorkoutCardTemplate(
        day = 3,
        workoutName = "Squatting",
        duration = 30,
        onStartClick = {}
    )
}
