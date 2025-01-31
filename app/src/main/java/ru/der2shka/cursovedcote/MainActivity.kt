package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.der2shka.cursovedcote.ui.theme.CursovedCotETheme

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyAppMainWindow()
        }
    }
}

@Composable
fun MyAppMainWindow() {
    CursovedCotETheme {
        Box(modifier = Modifier.fillMaxSize()) {
            AppContentMainWindow()
        }
    }
}

@SuppressLint("ResourceAsColor")
@Composable
fun AppContentMainWindow() {
    val height = 50.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(R.color.background_color)
            )
        ,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(height)
                .background(
                    color = colorResource(R.color.primary_blue)
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(height)
                .background(
                    color = colorResource(R.color.secondary_cyan)
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(height)
                .background(
                    color = colorResource(R.color.tertiary_orange)
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(height)
                .background(
                    color = colorResource(R.color.successful_green)
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(height)
                .background(
                    color = colorResource(R.color.error_red)
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(height)
                .background(
                    color = colorResource(R.color.warning_yellow)
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(height)
                .background(
                    color = colorResource(R.color.main_text_dark_gray)
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(height)
                .background(
                    color = colorResource(R.color.secondary_text_gray)
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(height)
                .background(
                    color = colorResource(R.color.additional_purple)
                )
        )

        Text(
            text = isSystemInDarkTheme().toString(),
            color = Color.White
        )
    }
}