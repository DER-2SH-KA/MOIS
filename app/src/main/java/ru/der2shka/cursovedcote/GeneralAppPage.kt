package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import java.time.Instant
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

@Composable
fun GeneralAppPage(
    navController: NavHostController
) {
    val context = LocalContext.current
    val config = LocalConfiguration.current

    Box(
        modifier = Modifier
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {
            val mainContentVScroll = rememberScrollState(0)
            val daysScrollerHScroll = rememberScrollState(0)
            val subjectsScrollerHScroll = rememberScrollState(0)

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
                        text = "September 2025",
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
                        .horizontalScroll(daysScrollerHScroll)
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        DaysScrollItem(LocalDate.now().minusDays(2))

                        DaysScrollItem(LocalDate.now().minusDays(1))

                        DaysScrollItem(LocalDate.now())

                        DaysScrollItem(LocalDate.now().plusDays(1))

                        DaysScrollItem(LocalDate.now().plusDays(2))
                    }
                }
            }
    }
}

/*@SuppressLint("ResourceAsColor")
@Composable
fun getStringMonth(): String {
    return stringResource(R.string.february_short_name)
}
*/

@SuppressLint("ResourceAsColor")
@Composable
fun DaysScrollItem(
    localDate: LocalDate
) {
    val calendar: Calendar = Calendar.getInstance()
    calendar.set(localDate.year, localDate.month.value, localDate.dayOfYear)

    Box() {
        Column(
            modifier = Modifier
                .fillMaxSize(0.8f)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = calendar.get(Calendar.DAY_OF_WEEK).toString(),
                color = colorResource(R.color.secondary_text_gray),
                textAlign = TextAlign.Center,
                fontSize = font_size_main_text
            )

            Text(
                text = calendar.get(Calendar.DAY_OF_MONTH).toString(),
                color = colorResource(R.color.main_text_dark_gray),
                textAlign = TextAlign.Center,
                fontSize = font_size_main_text
            )
        }
    }
}
