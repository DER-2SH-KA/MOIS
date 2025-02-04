package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@SuppressLint("ResourceAsColor")
@Composable
fun SplashScreenPage(
    navController: NavHostController
) {
    val config = LocalConfiguration.current;
    val width = (config.screenWidthDp * 0.5f).dp

    // Background of splash screen with icon.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        colorResource(id = R.color.primary_blue),
                        colorResource(id = R.color.secondary_cyan)
                    ),
               )
            )
        ,
        contentAlignment = Alignment.Center
    ) {
        val visibleIconState = remember { mutableStateOf(false) }
        val animationDuration: Int = 1000

        // Animation of show the app white icon.
        AnimatedVisibility(
            visible = visibleIconState.value,
            enter = fadeIn(
                animationSpec = tween(animationDuration)
            ) + scaleIn(
                initialScale = 0.8f,
                animationSpec = tween(animationDuration)
            ) + slideInVertically(
                initialOffsetY = {height -> height/3},
                animationSpec = tween(animationDuration)
            ) ,
            exit = fadeOut(
                animationSpec = tween(animationDuration)
            ) + scaleOut(
                targetScale = 0.8f,
                animationSpec = tween(animationDuration)
            ) + slideOutVertically(
                animationSpec = tween(animationDuration)
            )
        ) {
            Image(
                modifier = Modifier
                    .width( width )
                    .height( width )
                ,
                painter = painterResource(id = R.drawable.app_icon_white),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
        }

        // Animation visible state manipulation.
        LaunchedEffect(key1 = Unit) {
            delay(500)
            visibleIconState.value = true
            delay(animationDuration.toLong() + 700)
            visibleIconState.value = false
            delay(400)

            GoToWelcomePhrases(navController)
        }
    }
}

fun GoToWelcomePhrases(navController: NavHostController) {
    current_page = "welcome_pages"
    navController.navigate(current_page)
}