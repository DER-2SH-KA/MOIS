package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ru.der2shka.cursovedcote.Models.GeneralPageContentHelper
import ru.der2shka.cursovedcote.Service.GetMonthStringResourceByLocalDate
import ru.der2shka.cursovedcote.ui.AverageMarkCard
import ru.der2shka.cursovedcote.ui.DaysScrollItemDay
import ru.der2shka.cursovedcote.ui.HorizontalGeneralPageItem
import ru.der2shka.cursovedcote.ui.ScrollableAnimatedText
import ru.der2shka.cursovedcote.ui.theme.VeryLightGrayMostlyWhite
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import java.time.LocalDate
import java.util.Optional

/**
 * General app page main function.
 * **/
@SuppressLint("UnrememberedMutableState")
@Composable
fun GeneralAppPage(
    navHostController: NavHostController,
    pager: PagerState
) {
    val genPageContentHelper: GeneralPageContentHelper = GeneralPageContentHelper.getInstance()

    val context = LocalContext.current
    val config = LocalConfiguration.current

    val oneBlockHeight = (config.screenHeightDp * 0.2).dp
    val dayItemHeightPercent = 0.6f

    val coroutineScope = rememberCoroutineScope()

    val selectedDate = remember { mutableStateOf(genPageContentHelper.currentLocalDate) }

    genPageContentHelper.setNewCurrentLocalDate( Optional.ofNullable(selectedDate.value) )

    val selectedDateString = "${genPageContentHelper.currentLocalDate.dayOfMonth} " +
            "${GetMonthStringResourceByLocalDate(mutableStateOf( genPageContentHelper.currentLocalDate ), false)} " +
            "${genPageContentHelper.currentLocalDate.year}"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(R.color.background_color)
            )
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
                .verticalScroll(
                    mainContentVScroll
                )
            ,
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Current Month and Year. Top Side of Monitor.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = colorResource(R.color.background_color)
                    )
                    .height(
                        (oneBlockHeight)
                    )
                ,
                contentAlignment = Alignment.Center
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .padding(10.dp)
                        .background(
                            color = colorResource(R.color.primary_blue),
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    ScrollableAnimatedText(
                        text = selectedDateString,
                        textColor = VeryLightGrayMostlyWhite,
                        textAlign = TextAlign.Center,
                        fontSize = font_size_main_text,
                        lineHeight = line_height_main_text,
                        fontWeight = FontWeight.Bold,
                        containterModifier = Modifier
                            .fillMaxWidth(0.9f)
                        ,
                        textModifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            // Days Horizontal Scroller.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height( oneBlockHeight )
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
                        daysScrollerHScroll,
                        modifier = Modifier
                            .fillMaxHeight( dayItemHeightPercent )
                            .aspectRatio(1f),
                        onSelect = { localDate ->
                            genPageContentHelper.setNewCurrentLocalDate( Optional.ofNullable(localDate) )
                            selectedDate.value = genPageContentHelper.currentLocalDate
                        }
                    )

                    DaysScrollItemDay(
                        selectedDate.value.minusDays(6),
                        selectedDate,
                        daysScrollerHScroll,
                        modifier = Modifier
                            .fillMaxHeight( dayItemHeightPercent )
                            .aspectRatio(1f),
                        onSelect = { localDate ->
                            genPageContentHelper.setNewCurrentLocalDate( Optional.ofNullable(localDate) )
                            selectedDate.value = genPageContentHelper.currentLocalDate
                        }
                    )

                    DaysScrollItemDay(
                        selectedDate.value.minusDays(5),
                        selectedDate,
                        daysScrollerHScroll,
                        modifier = Modifier
                            .fillMaxHeight( dayItemHeightPercent )
                            .aspectRatio(1f),
                        onSelect = { localDate ->
                            genPageContentHelper.setNewCurrentLocalDate( Optional.ofNullable(localDate) )
                            selectedDate.value = genPageContentHelper.currentLocalDate
                        }
                    )

                    DaysScrollItemDay(
                        selectedDate.value.minusDays(4),
                        selectedDate,
                        daysScrollerHScroll,
                        modifier = Modifier
                            .fillMaxHeight( dayItemHeightPercent )
                            .aspectRatio(1f),
                        onSelect = { localDate ->
                            genPageContentHelper.setNewCurrentLocalDate( Optional.ofNullable(localDate) )
                            selectedDate.value = genPageContentHelper.currentLocalDate
                        }
                    )

                    DaysScrollItemDay(
                        selectedDate.value.minusDays(3),
                        selectedDate,
                        daysScrollerHScroll,
                        modifier = Modifier
                            .fillMaxHeight( dayItemHeightPercent )
                            .aspectRatio(1f),
                        onSelect = { localDate ->
                            genPageContentHelper.setNewCurrentLocalDate( Optional.ofNullable(localDate) )
                            selectedDate.value = genPageContentHelper.currentLocalDate
                        }
                    )

                    DaysScrollItemDay(
                        selectedDate.value.minusDays(2),
                        selectedDate,
                        daysScrollerHScroll,
                        modifier = Modifier
                            .fillMaxHeight( dayItemHeightPercent )
                            .aspectRatio(1f),
                        onSelect = { localDate ->
                            genPageContentHelper.setNewCurrentLocalDate( Optional.ofNullable(localDate) )
                            selectedDate.value = genPageContentHelper.currentLocalDate
                        }
                    )

                    DaysScrollItemDay(
                        selectedDate.value.minusDays(1),
                        selectedDate,
                        daysScrollerHScroll,
                        modifier = Modifier
                            .fillMaxHeight( dayItemHeightPercent )
                            .aspectRatio(1f),
                        onSelect = { localDate ->
                            genPageContentHelper.setNewCurrentLocalDate( Optional.ofNullable(localDate) )
                            selectedDate.value = genPageContentHelper.currentLocalDate
                        }
                    )

                    DaysScrollItemDay(
                        selectedDate.value,
                        selectedDate,
                        daysScrollerHScroll,
                        modifier = Modifier
                            .fillMaxHeight( dayItemHeightPercent )
                            .aspectRatio(1f),
                        onSelect = { localDate ->
                            genPageContentHelper.setNewCurrentLocalDate( Optional.ofNullable(localDate) )
                            selectedDate.value = genPageContentHelper.currentLocalDate
                        }
                    )

                    DaysScrollItemDay(
                        selectedDate.value.plusDays(1),
                        selectedDate,
                        daysScrollerHScroll,
                        modifier = Modifier
                            .fillMaxHeight( dayItemHeightPercent )
                            .aspectRatio(1f),
                        onSelect = { localDate ->
                            genPageContentHelper.setNewCurrentLocalDate( Optional.ofNullable(localDate) )
                            selectedDate.value = genPageContentHelper.currentLocalDate
                        }
                    )

                    DaysScrollItemDay(
                        selectedDate.value.plusDays(2),
                        selectedDate,
                        daysScrollerHScroll,
                        modifier = Modifier
                            .fillMaxHeight( dayItemHeightPercent )
                            .aspectRatio(1f),
                        onSelect = { localDate ->
                            genPageContentHelper.setNewCurrentLocalDate( Optional.ofNullable(localDate) )
                            selectedDate.value = genPageContentHelper.currentLocalDate
                        }
                    )

                    DaysScrollItemDay(
                        selectedDate.value.plusDays(3),
                        selectedDate,
                        daysScrollerHScroll,
                        modifier = Modifier
                            .fillMaxHeight( dayItemHeightPercent )
                            .aspectRatio(1f),
                        onSelect = { localDate ->
                            genPageContentHelper.setNewCurrentLocalDate( Optional.ofNullable(localDate) )
                            selectedDate.value = genPageContentHelper.currentLocalDate
                        }
                    )

                    DaysScrollItemDay(
                        selectedDate.value.plusDays(4),
                        selectedDate,
                        daysScrollerHScroll,
                        modifier = Modifier
                            .fillMaxHeight( dayItemHeightPercent )
                            .aspectRatio(1f),
                        onSelect = { localDate ->
                            genPageContentHelper.setNewCurrentLocalDate( Optional.ofNullable(localDate) )
                            selectedDate.value = genPageContentHelper.currentLocalDate
                        }
                    )

                    DaysScrollItemDay(
                        selectedDate.value.plusDays(5),
                        selectedDate,
                        daysScrollerHScroll,
                        modifier = Modifier
                            .fillMaxHeight( dayItemHeightPercent )
                            .aspectRatio(1f),
                        onSelect = { localDate ->
                            genPageContentHelper.setNewCurrentLocalDate( Optional.ofNullable(localDate) )
                            selectedDate.value = genPageContentHelper.currentLocalDate
                        }
                    )

                    DaysScrollItemDay(
                        selectedDate.value.plusDays(6),
                        selectedDate,
                        daysScrollerHScroll,
                        modifier = Modifier
                            .fillMaxHeight( dayItemHeightPercent )
                            .aspectRatio(1f),
                        onSelect = { localDate ->
                            genPageContentHelper.setNewCurrentLocalDate( Optional.ofNullable(localDate) )
                            selectedDate.value = genPageContentHelper.currentLocalDate
                        }
                    )

                    DaysScrollItemDay(
                        selectedDate.value.plusDays(7),
                        selectedDate,
                        daysScrollerHScroll,
                        modifier = Modifier
                            .fillMaxHeight( dayItemHeightPercent )
                            .aspectRatio(1f),
                        onSelect = { localDate ->
                            genPageContentHelper.setNewCurrentLocalDate( Optional.ofNullable(localDate) )
                            selectedDate.value = genPageContentHelper.currentLocalDate
                        }
                    )
                }
            }

            /*
            // Average marks block.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height( oneBlockHeight )
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
                    AverageMarkCard(
                        "Математика",
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
                    )

                    AverageMarkCard(
                        "ОАиП",
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
                    )

                    AverageMarkCard(
                        "ТРиЗБД",
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
                    )
                }
            }
            */

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    //.weight(2f)
                ,
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Only for testing ScrollableAnimatedText.
                /*Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.fillMaxWidth(0.3f)
                ) {
                    ScrollableAnimatedText(
                        text = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ",
                        textColor = Color.Black,
                        maxLines = 1,
                        fontSize = font_size_main_text,
                        lineHeight = line_height_main_text,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Visible,
                        softWrap = false,
                        // duration = 10000
                    )
                }
                */

                // Homework.
                HorizontalGeneralPageItem(
                    headerText = stringResource(R.string.homeworks),
                    maxLines = 1,
                    onItemClick = {
                        coroutineScope.launch {
                            pager.scrollToPage(1)
                        }
                    },
                    onPlusClick = {
                        current_page = "add_new_homework"
                        navHostController.navigate(current_page)
                    },
                    onDotsClick = { },
                    modifier = Modifier
                        .height( oneBlockHeight * 0.75f )
                        .fillMaxWidth(0.9f)
                        .background(
                            brush = Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.6f to colorResource(R.color.primary_blue),
                                    1f to colorResource(R.color.secondary_cyan)
                                )
                            )
                            ,
                            shape = RoundedCornerShape(20.0f)
                        )
                )

                // Notes.
                HorizontalGeneralPageItem(
                    headerText = stringResource(R.string.notes),
                    maxLines = 1,
                    onItemClick = {
                        coroutineScope.launch {
                            pager.scrollToPage(3)
                        }
                    },
                    onPlusClick = {
                        current_page = "add_new_note"
                        navHostController.navigate(current_page)
                    },
                    onDotsClick = { },
                    modifier = Modifier
                        .height( oneBlockHeight * 0.75f )
                        .fillMaxWidth(0.9f)
                        .background(
                            brush = Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.6f to colorResource(R.color.primary_blue),
                                    1f to colorResource(R.color.secondary_cyan)
                                )
                            )
                            ,
                            shape = RoundedCornerShape(20.0f)
                        )
                )

                // Grades.
                HorizontalGeneralPageItem(
                    headerText = stringResource(R.string.grades),
                    maxLines = 1,
                    onItemClick = {
                        coroutineScope.launch {
                            pager.scrollToPage(0)
                        }
                    },
                    onPlusClick = {
                        current_page = "add_new_grade"
                        navHostController.navigate(current_page)
                    },
                    onDotsClick = { },
                    modifier = Modifier
                        .height( oneBlockHeight * 0.75f )
                        .fillMaxWidth(0.9f)
                        .background(
                            brush = Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.6f to colorResource(R.color.primary_blue),
                                    1f to colorResource(R.color.secondary_cyan)
                                )
                            )
                            ,
                            shape = RoundedCornerShape(20.0f)
                        )
                )

                // Study Subjects.
                HorizontalGeneralPageItem(
                    headerText = stringResource(R.string.study_subjects),
                    maxLines = 1,
                    onItemClick = {
                        coroutineScope.launch {
                            pager.scrollToPage(0)
                        }
                    },
                    onPlusClick = {
                        current_page = "add_new_study_subject"
                        navHostController.navigate(current_page)
                    },
                    onDotsClick = { },
                    modifier = Modifier
                        .height( oneBlockHeight * 0.75f )
                        .fillMaxWidth(0.9f)
                        .background(
                            brush = Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.6f to colorResource(R.color.primary_blue),
                                    1f to colorResource(R.color.secondary_cyan)
                                )
                            )
                            ,
                            shape = RoundedCornerShape(20.0f)
                        )
                )

                // Grade types.
                HorizontalGeneralPageItem(
                    headerText = stringResource(R.string.grade_types),
                    maxLines = 1,
                    onItemClick = {
                        coroutineScope.launch {
                            pager.scrollToPage(0)
                        }
                    },
                    onPlusClick = {
                        current_page = "add_new_mark_type"
                        navHostController.navigate(current_page)
                    },
                    onDotsClick = { },
                    modifier = Modifier
                        .height( oneBlockHeight * 0.75f )
                        .fillMaxWidth(0.9f)
                        .background(
                            brush = Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.6f to colorResource(R.color.primary_blue),
                                    1f to colorResource(R.color.secondary_cyan)
                                )
                            )
                            ,
                            shape = RoundedCornerShape(20.0f)
                        )
                )
            }
        }
    }
}
