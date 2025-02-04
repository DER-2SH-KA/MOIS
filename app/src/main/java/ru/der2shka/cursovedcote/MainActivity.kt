package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInExpo
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.der2shka.cursovedcote.ui.theme.CursovedCotETheme

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

var current_page = "splash_screen"

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

/**
 * Main procedure which manipulate MainActivity Content.
 * **/
@Composable
fun MyAppMainWindow() {
    CursovedCotETheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "splash_screen"
        ) {
            composable(route = "splash_screen") {
                SplashScreenPage(navController)
            }
            composable(route = "welcome_pages") {
                WelcomePagesPage(navController)
            }
            composable(route = "colors_test_page") {
                ColorTestPage(navController)
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            // AppContentMainWindow()

        }
    }
}


/**
 * This is a procedure which show
 * colors test page.
 * @param navController App Navigation Controller
 * **/
@SuppressLint("ResourceAsColor")
@Composable
fun ColorTestPage(
    navController: NavHostController
) {
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

        /*var visible = remember { mutableStateOf(false) }

        // Кнопка для переключения видимости
        Button(onClick = { visible.value = !visible.value }) {
            Text(if (visible.value) "Скрыть" else "Показать")
        }

        // Анимированное появление/исчезновение
        AnimatedVisibility(visible = visible.value,
            /*enter = slideInHorizontally() + expandHorizontally(expandFrom = Alignment.Start)
                    + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth })
                    + shrinkHorizontally() + fadeOut(),*/
            enter = slideInHorizontally(),
            exit = slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth / 2 }
            )
            ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Blue)
            )
        }*/
    }
}