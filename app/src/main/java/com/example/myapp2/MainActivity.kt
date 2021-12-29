package com.example.myapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapp2.ui.theme.MyApp2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MyAppTheme(content: @Composable () -> Unit) {
    val colors = lightColors(
        surface = Color(0xff121212),
        background = Color(0xff242424),
        onPrimary = Color(0xffffffff),
        secondary = Color(0xff737373),
        primary = Color(0xff6200EE)
    )
    MaterialTheme(colors = colors, content = content)
}


val BackgroundColor = Color(0xff121212)
val ItemBackgroundColor = Color(0xff202124)
val LineGray = Color(0xff242424)
val TextSecondary = Color(0xff737373)
val TextPrimary = Color(0xffffffff)