package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text

@SuppressLint("ResourceAsColor")
@Composable
fun WelcomePhraseThird() {
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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val spacer_height = (config.screenHeightDp * 1/10).dp

            Spacer(
                modifier = Modifier
                    .height( spacer_height )
            )

            Image(
                modifier = Modifier
                    .size(widthForPicture.dp)
                ,
                painter = painterResource(id = R.drawable.graph),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )

            Spacer(
                modifier = Modifier
                    .height( spacer_height )
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
                    text = stringResource(R.string.welcome_phrase_3_main_text),
                    color = colorResource(R.color.main_text_dark_gray),
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    fontSize = font_size_main_text,
                    fontWeight = FontWeight.Bold,
                    lineHeight = TextUnit(
                        line_height_main_text,
                        TextUnitType.Sp
                    )
                )
                Text(
                    modifier = Modifier,
                    text= stringResource(R.string.welcome_phrase_3_secondary_text),
                    color = colorResource(R.color.secondary_text_gray),
                    maxLines = 3,
                    textAlign = TextAlign.Center,
                    fontSize = font_size_secondary_text,
                    lineHeight = TextUnit(
                        line_height_secondary_text,
                        TextUnitType.Sp
                    )
                )
            }
        }
    }
}