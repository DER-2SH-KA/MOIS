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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text

/**
 *  Pseudo-Splash Screen method.
 * **/
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

        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxSize(0.8f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.developer_pseudoname),
                color = Color.White,
                maxLines = 3,
                textAlign = TextAlign.Center,
                fontSize = font_size_main_text,
                lineHeight = line_height_main_text
            )
        }
    }
}

private fun GoToWelcomePhrases(navController: NavHostController) {
    current_page = "choose_language_from_start_page"
    navController.navigate(current_page) {
        popUpTo("splash_screen_page") { inclusive = true }
    }
}