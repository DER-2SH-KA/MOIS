package ru.der2shka.cursovedcote.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.der2shka.cursovedcote.R
import ru.der2shka.cursovedcote.Service.GetDayOfWeekStringResourceByLocalDate
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_middle_size_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_middle_size_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text
import java.time.LocalDate

/**
 * Day Scroll Item with LocalDate and design.
 * **/
@SuppressLint("ResourceAsColor")
@Composable
fun DaysScrollItemDay(
    localDate: LocalDate,
    selectedDay: MutableState<LocalDate>,
    daysScrollerHScroll: ScrollState
) {
    var dayOfWeek: String = GetDayOfWeekStringResourceByLocalDate(localDate, false)
    var coroutineScope = rememberCoroutineScope()

    var isSelected = selectedDay.value.equals(localDate)

    val backgroundColor: Color = if (isSelected)
        colorResource(R.color.primary_blue)
    else
        colorResource(R.color.background_color)

    var mainTextColor: Color = if (isSelected) {
        colorResource(R.color.background_color)
    } else {
        colorResource(R.color.main_text_dark_gray)
    }

    // If Today this date main text.
    if (localDate.equals(LocalDate.now())) {
        mainTextColor = colorResource(R.color.secondary_cyan)
    }

    var secondaryTextColor: Color = if (isSelected)
        colorResource(R.color.background_color)
    else
        colorResource(R.color.secondary_text_gray)

    // If Today this date secondary text.
    if (localDate.equals(LocalDate.now())) {
        secondaryTextColor = colorResource(R.color.secondary_cyan)
    }

    Box(
        modifier = Modifier
            .clickable {
                selectedDay.value = localDate

                coroutineScope.launch {
                    daysScrollerHScroll.scrollTo(daysScrollerHScroll.maxValue / 2)
                }
            }
        ,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .height(110.dp)
                .aspectRatio(1f)
                .padding(10.dp)
                .background(
                    backgroundColor,
                    shape = RoundedCornerShape(5.dp)
                )
            ,
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .padding(10.dp)
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = dayOfWeek,
                    color = secondaryTextColor,
                    textAlign = TextAlign.Center,
                    fontSize = font_size_secondary_text,
                    lineHeight = line_height_secondary_text
                )

                Text(
                    text = localDate.dayOfMonth.toString(),
                    color = mainTextColor,
                    textAlign = TextAlign.Center,
                    fontSize = font_size_middle_size_text,
                    lineHeight = line_height_middle_size_text
                )
            }
        }
    }
}
