package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.der2shka.cursovedcote.ui.theme.main_text_size
import ru.der2shka.cursovedcote.ui.theme.secondary_text_size

@SuppressLint("ResourceAsColor")
@Composable
fun WelcomePhraseFirst() {
    val config = LocalConfiguration.current
    val widthForPicture = config.screenWidthDp * 0.6f

    Box(
        modifier = Modifier
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
            ,
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            //Spacer(modifier = Modifier.height(100.dp))
            Image(
                modifier = Modifier
                    .width( widthForPicture.dp )
                    .height( widthForPicture.dp )
                 ,
                painter = painterResource(id = R.drawable.pen_and_note),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                ,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.welcome_phrase_1_main_text),
                    color = colorResource(R.color.main_text_dark_gray),
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    fontSize = main_text_size,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier,
                    text=stringResource(R.string.welcome_phrase_1_secondary_text),
                    color = colorResource(R.color.secondary_text_gray),
                    maxLines = 3,
                    textAlign = TextAlign.Center,
                    fontSize = secondary_text_size
                )
            }
        }
    }
}