package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.der2shka.cursovedcote.Service.GetMonthStringResourceByLocalDate
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import java.time.Instant
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

@Composable
fun GeneralAppPage(
) {
    val context = LocalContext.current
    val config = LocalConfiguration.current

    val selectedDate = remember { mutableStateOf(LocalDate.now()) }
    val selectedDateString = "${selectedDate.value.dayOfMonth} " +
            "${GetMonthStringResourceByLocalDate(selectedDate, false)} " +
            "${selectedDate.value.year}"

    Box(
        modifier = Modifier
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {
        val mainContentVScroll = rememberScrollState(0)
        val daysScrollerHScroll = rememberScrollState(0)
        val subjectsScrollerHScroll = rememberScrollState(0)

        LaunchedEffect(Unit) {
            // Scroll to the middle after the content is laid out.
            daysScrollerHScroll.scrollTo(daysScrollerHScroll.maxValue / 2)
        }

        // Main Content.
        Column(
            modifier = Modifier
                .fillMaxSize()
            ,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Current Month and Year. Top Side of Monitor.
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.Magenta),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = selectedDateString,
                    color = colorResource(R.color.main_text_dark_gray),
                    textAlign = TextAlign.Center,
                    fontSize = font_size_main_text,
                    lineHeight = line_height_main_text,
                    fontWeight = FontWeight.Bold
                )
            }

            // Days Horizontal Scroller.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(daysScrollerHScroll)
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    DaysScrollItemDay(
                        selectedDate.value.minusDays(7),
                        selectedDate,
                        daysScrollerHScroll
                    )

                    DaysScrollItemDay(
                        selectedDate.value.minusDays(6),
                        selectedDate,
                        daysScrollerHScroll
                    )

                    DaysScrollItemDay(
                        selectedDate.value.minusDays(5),
                        selectedDate,
                        daysScrollerHScroll
                    )

                    DaysScrollItemDay(
                        selectedDate.value.minusDays(4),
                        selectedDate,
                        daysScrollerHScroll
                    )

                    DaysScrollItemDay(
                        selectedDate.value.minusDays(3),
                        selectedDate,
                        daysScrollerHScroll
                    )

                    DaysScrollItemDay(
                        selectedDate.value.minusDays(2),
                        selectedDate,
                        daysScrollerHScroll
                    )

                    DaysScrollItemDay(
                        selectedDate.value.minusDays(1),
                        selectedDate,
                        daysScrollerHScroll
                    )

                    DaysScrollItemDay(
                        selectedDate.value,
                        selectedDate,
                        daysScrollerHScroll
                    )

                    DaysScrollItemDay(
                        selectedDate.value.plusDays(1),
                        selectedDate,
                        daysScrollerHScroll
                    )

                    DaysScrollItemDay(
                        selectedDate.value.plusDays(2),
                        selectedDate,
                        daysScrollerHScroll
                    )

                    DaysScrollItemDay(
                        selectedDate.value.plusDays(3),
                        selectedDate,
                        daysScrollerHScroll
                    )

                    DaysScrollItemDay(
                        selectedDate.value.plusDays(4),
                        selectedDate,
                        daysScrollerHScroll
                    )

                    DaysScrollItemDay(
                        selectedDate.value.plusDays(5),
                        selectedDate,
                        daysScrollerHScroll
                    )

                    DaysScrollItemDay(
                        selectedDate.value.plusDays(6),
                        selectedDate,
                        daysScrollerHScroll
                    )

                    DaysScrollItemDay(
                        selectedDate.value.plusDays(7),
                        selectedDate,
                        daysScrollerHScroll
                    )
                }
            }

            // Selected Date value.
            Text(
                text = "${selectedDate.value.year}-${selectedDate.value.month}-${selectedDate.value.dayOfMonth}",
                color = colorResource(R.color.main_text_dark_gray),
                textAlign = TextAlign.Center,
                fontSize = font_size_main_text,
                lineHeight = line_height_main_text,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(subjectsScrollerHScroll)
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {

                    Box(
                        modifier = Modifier.background(Color.Blue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Biba")
                    }

                    Box(
                        modifier = Modifier.background(Color.Blue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Biba")
                    }

                    Box(
                        modifier = Modifier.background(Color.Blue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Biba")
                    }
                }
            }
        }
    }
}
