package com.example.alpvp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.alpvp.ui.theme.ALPVPTheme
import com.example.alpvp.viewModels.ProfileViewModel
import com.example.alpvp.views.BoxingApp

class MainActivity : ComponentActivity() {
    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModel.Factory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ALPVPTheme {
                BoxingApp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        profileViewModel.saveUsernameToken("Unknown", "Unknown")
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ALPVPTheme {
        BoxingApp()
    }
}