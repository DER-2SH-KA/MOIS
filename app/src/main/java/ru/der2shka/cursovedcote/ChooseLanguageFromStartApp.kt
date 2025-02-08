package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@SuppressLint("ResourceAsColor")
@Composable
fun ChooseLanguageFromStartAppPage() {
    val config = LocalConfiguration.current
    val widthForPicture = config.screenWidthDp * 0.6f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(R.color.background_color)
            )
        ,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth(0.8f)
            ,
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(widthForPicture.dp)
                ,
                painter = painterResource(R.drawable.background_gradient_white_app_icon_256),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
        }
    }
}