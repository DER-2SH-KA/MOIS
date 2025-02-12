package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text


@SuppressLint("ResourceAsColor")
@Composable
fun AverageMarkCard(
    subjectName: String
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = subjectName,
                color = colorResource(R.color.main_text_dark_gray),
                textAlign = TextAlign.Start,
                fontSize = font_size_main_text,
                lineHeight = line_height_main_text,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "4.9",
                    color = colorResource(R.color.main_text_dark_gray),
                    textAlign = TextAlign.Center,
                    fontSize = font_size_main_text,
                    lineHeight = line_height_main_text,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "-0.1",
                    color = colorResource(R.color.error_red),
                    textAlign = TextAlign.Center,
                    fontSize = font_size_secondary_text,
                    lineHeight = line_height_secondary_text
                )
            }
        }

    }
}
