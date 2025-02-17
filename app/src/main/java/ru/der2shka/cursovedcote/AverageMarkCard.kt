package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text

/**
 *  Average mark card for General app page function.
 * **/
@SuppressLint("ResourceAsColor")
@Composable
fun AverageMarkCard(
    subjectName: String
) {
    val config = LocalConfiguration.current

    val sizeCard = remember { mutableStateOf(IntSize.Zero) }
    val sizeTextSubjectName = remember { mutableStateOf(IntSize.Zero) }

    val isOverflowed = remember { mutableStateOf(false) }

    SubcomposeLayout(
        modifier = Modifier
            .widthIn(20.dp, (config.screenWidthDp * 0.4f).dp)
            .padding(10.dp)
    ) { constraints ->
        val textPlaceableUnconstrained = subcompose("text_unconstrained") {
            Text(
                text = subjectName,
                color = colorResource(R.color.main_text_dark_gray),
                textAlign = TextAlign.Start,
                maxLines = 1,
                fontSize = font_size_main_text,
                lineHeight = line_height_main_text,
                fontWeight = FontWeight.Bold
            )
        }[0].measure(Constraints()) // Constraints() без ограничений

        // Измеряем текст с ограничениями контейнера
        val textPlaceable = subcompose("text") {
            Text(
                text = subjectName,
                color = colorResource(R.color.main_text_dark_gray),
                textAlign = TextAlign.Start,
                maxLines = 1,
                fontSize = font_size_main_text,
                lineHeight = line_height_main_text,
                fontWeight = FontWeight.Bold
            )
        }[0].measure(constraints)

        // Проверяем, превышает ли текст ширину контейнера
        isOverflowed.value = textPlaceableUnconstrained.width > constraints.maxWidth

        // Отображаем текст
        layout(0, 0) { }
    }

    Card(
        modifier = Modifier
            .widthIn(20.dp, (config.screenWidthDp * 0.4f).dp)
            .padding(10.dp)
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.6f to colorResource(R.color.primary_blue),
                        1f to colorResource(R.color.secondary_cyan)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .onSizeChanged {
                sizeCard.value = it
            }
        ,
        colors = CardDefaults.cardColors(
            Color.Transparent,
            Color.Transparent
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .onSizeChanged {
                        sizeTextSubjectName.value = it
                    },
                text = subjectName,
                color = colorResource(R.color.background_color),
                textAlign = TextAlign.Start,
                maxLines = 1,
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
                    color = colorResource(R.color.background_color),
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

            /*
            // Size.
            Text(
                text = "w-${sizeCard.value.width} h-${sizeCard.value.height}",
                color = colorResource(R.color.main_text_dark_gray),
                textAlign = TextAlign.Start,
                fontSize = font_size_main_text,
                lineHeight = line_height_main_text,
                fontWeight = FontWeight.Bold
            )
            */

            // SizeTextCondition.
            Text(
                text = isOverflowed.value.toString(), // if (sizeTextSubjectName.value.width > sizeCard.value.width) "true" else "false",
                color = colorResource(R.color.main_text_dark_gray),
                textAlign = TextAlign.Start,
                fontSize = font_size_main_text,
                lineHeight = line_height_main_text,
                fontWeight = FontWeight.Bold
            )
        }

    }
}
