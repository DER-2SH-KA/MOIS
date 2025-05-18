package ru.der2shka.cursovedcote.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.der2shka.cursovedcote.Models.HomeworkHelper
import ru.der2shka.cursovedcote.R
import ru.der2shka.cursovedcote.Service.GetMonthStringResourceByLocalDate
import ru.der2shka.cursovedcote.curr_page
import ru.der2shka.cursovedcote.current_page
import ru.der2shka.cursovedcote.db.entity.Homework
import ru.der2shka.cursovedcote.db.entity.StudySubject
import ru.der2shka.cursovedcote.db.helper.AppDatabase
import ru.der2shka.cursovedcote.ui.theme.VeryLightGrayMostlyWhite
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_middle_size_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_middle_size_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text
import ru.der2shka.cursovedcote.userId
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Optional

@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeworkItem(
    navHostController: NavHostController,
    homework: Homework,
    subjectName: String,
    database: AppDatabase,
    modifier: Modifier = Modifier
) {
    val homeworkHelper = HomeworkHelper.getInstance()
    val showFullCard = remember { mutableStateOf(false) }

    // Date of write.
    var localDateOfWrite = Instant
        .ofEpochMilli( homework.dateOfWrite)
        .atZone( ZoneId.systemDefault() )
        .toLocalDate()

    var dateOfWriteString = "${localDateOfWrite.dayOfMonth} " +
            "${
                GetMonthStringResourceByLocalDate(
                    mutableStateOf(localDateOfWrite), false
                )} " +
            "${localDateOfWrite.year}"

    // Date Begin.
    var localDateBegin = Instant
        .ofEpochMilli( homework.dateBegin)
        .atZone( ZoneId.systemDefault() )
        .toLocalDate()

    var dateBeginString = "${localDateBegin.dayOfMonth} " +
            "${
                GetMonthStringResourceByLocalDate(
                    mutableStateOf(localDateBegin), false
                )} " +
            "${localDateBegin.year}"

    // Date End.
    var localDateEnd = Instant
        .ofEpochMilli( homework.dateEnd)
        .atZone( ZoneId.systemDefault() )
        .toLocalDate()

    val dateEndString = "${localDateEnd.dayOfMonth} " +
            "${
                GetMonthStringResourceByLocalDate(
                    mutableStateOf(localDateEnd), false
                )} " +
            "${localDateEnd.year}"

    // Status.
    val statusList = SomeConstantValues().getStatusList()

    val statusColor = when (homework.status) {
        0 -> colorResource(R.color.additional_purple)
        1 -> colorResource(R.color.tertiary_orange)
        2 -> colorResource(R.color.warning_yellow)
        3 -> colorResource(R.color.successful_green)
        4 -> colorResource(R.color.error_red)
        else -> colorResource(R.color.black)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(0.dp, 10.dp, 0.dp, 10.dp)
            .background(
                color = statusColor,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                homeworkHelper.setHomeworkValue( Optional.ofNullable( homework ) )

                curr_page = 1

                current_page = "edit_homework"
                navHostController.navigate(current_page)
            }
    ) {
        // Homework Card Item.
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {

            if (showFullCard.value) {

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    // Name and button.
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        // Name.
                        Box(
                            contentAlignment = Alignment.TopStart,
                            modifier = Modifier
                                .padding(20.dp, 0.dp, 0.dp, 0.dp)
                                .fillMaxWidth(0.8f)
                        ) {
                            ScrollableAnimatedText(
                                text = homework.name,
                                textColor = Color.White,
                                textAlign = TextAlign.Start,
                                fontSize = font_size_main_text,
                                fontWeight = FontWeight.Bold,
                                lineHeight = line_height_main_text,
                                maxLines = 1,
                                containterModifier = Modifier.fillMaxWidth(),
                                textModifier = Modifier.fillMaxWidth()
                            )
                        }

                        // Button.
                        Icon(
                            imageVector = if (showFullCard.value)
                                    Icons.Default.KeyboardArrowUp
                                else
                                    Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    showFullCard.value = !showFullCard.value
                                }
                        )
                    }

                    // Study subject.
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        ScrollableAnimatedText(
                            text = subjectName,
                            textColor = Color.White,
                            textAlign = TextAlign.Start,
                            fontSize = font_size_middle_size_text,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Medium,
                            lineHeight = line_height_middle_size_text,
                            containterModifier = Modifier
                                .padding(10.dp, 0.dp, 0.dp, 0.dp)
                                .fillMaxWidth(),
                            textModifier = Modifier
                                .fillMaxWidth()
                        )
                    }

                    if ( homework.description != null &&  !homework.description.equals("") ) {
                    // Description.
                    Text(
                        text = homework.description,
                        color = Color.White,
                        textAlign = TextAlign.Start,
                        fontSize = font_size_secondary_text,
                        lineHeight = line_height_secondary_text,
                        maxLines = 3,
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(20.dp, 20.dp, 0.dp, 20.dp)
                            .fillMaxWidth()
                    )
                        }

                    // Dates.
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                        //.background(Color.Red)
                    ) {
                        // Date of Write.
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Box(
                                contentAlignment = Alignment.CenterStart,
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                            ) {
                                ScrollableAnimatedText(
                                    text = "${stringResource(R.string.date_of_write)}:",
                                    textColor = Color.White,
                                    textAlign = TextAlign.Start,
                                    fontSize = font_size_secondary_text,
                                    fontStyle = FontStyle.Italic,
                                    lineHeight = line_height_secondary_text,
                                    textModifier = Modifier
                                        .fillMaxWidth(0.9f),
                                    containterModifier = Modifier
                                        .fillMaxWidth()
                                )
                            }

                            Box(
                                contentAlignment = Alignment.CenterStart,
                                modifier = Modifier
                                    .fillMaxWidth()
                                // .background( Color.Red )
                            ) {
                                ScrollableAnimatedText(
                                    text = dateOfWriteString,
                                    textColor = VeryLightGrayMostlyWhite,
                                    textAlign = TextAlign.End,
                                    fontSize = font_size_secondary_text,
                                    fontStyle = FontStyle.Italic,
                                    lineHeight = line_height_secondary_text,
                                    maxLines = 1,
                                    containterModifier = Modifier.fillMaxWidth(),
                                    textModifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        // Date Begin.
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.CenterStart,
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                            ) {
                                ScrollableAnimatedText(
                                    text = "${stringResource(R.string.date_begin)}:",
                                    textColor = Color.White,
                                    textAlign = TextAlign.Start,
                                    fontSize = font_size_secondary_text,
                                    fontStyle = FontStyle.Italic,
                                    lineHeight = line_height_secondary_text,
                                    textModifier = Modifier
                                        .fillMaxWidth(0.9f),
                                    containterModifier = Modifier
                                        .fillMaxWidth()
                                )
                            }

                            Box(
                                contentAlignment = Alignment.CenterStart,
                                modifier = Modifier
                                    .fillMaxWidth()
                                // .background( Color.Red )
                            ) {
                                ScrollableAnimatedText(
                                    text = dateBeginString,
                                    textColor = VeryLightGrayMostlyWhite,
                                    textAlign = TextAlign.End,
                                    fontSize = font_size_secondary_text,
                                    fontStyle = FontStyle.Italic,
                                    lineHeight = line_height_secondary_text,
                                    maxLines = 1,
                                    containterModifier = Modifier.fillMaxWidth(),
                                    textModifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        // Date End.
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Box(
                                contentAlignment = Alignment.CenterStart,
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                // .background( Color.Red )
                            ) {
                                ScrollableAnimatedText(
                                    text = "${stringResource(R.string.date_end)}:",
                                    textColor = VeryLightGrayMostlyWhite,
                                    textAlign = TextAlign.Start,
                                    fontSize = font_size_secondary_text,
                                    fontStyle = FontStyle.Italic,
                                    lineHeight = line_height_secondary_text,
                                    maxLines = 1,
                                    containterModifier = Modifier.fillMaxWidth(),
                                    textModifier = Modifier.fillMaxWidth()
                                )
                            }

                            Box(
                                contentAlignment = Alignment.CenterStart,
                                modifier = Modifier
                                    .fillMaxWidth()
                                // .background( Color.Red )
                            ) {
                                ScrollableAnimatedText(
                                    text = dateEndString,
                                    textColor = VeryLightGrayMostlyWhite,
                                    textAlign = TextAlign.End,
                                    fontSize = font_size_secondary_text,
                                    fontStyle = FontStyle.Italic,
                                    lineHeight = line_height_secondary_text,
                                    maxLines = 1,
                                    containterModifier = Modifier.fillMaxWidth(),
                                    textModifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        // Status.
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            ScrollableAnimatedText(
                                text = "${statusList.get(homework.status)}",
                                textColor = Color.White,
                                textAlign = TextAlign.End,
                                fontSize = font_size_secondary_text,
                                lineHeight = line_height_secondary_text,
                                maxLines = 1,
                                containterModifier = Modifier.fillMaxWidth(),
                                textModifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                            )
                        }
                    }
                }

            }
            else {

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    // Name and button.
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        // Name.
                        Box(
                            contentAlignment = Alignment.TopStart,
                            modifier = Modifier
                                .padding(20.dp, 0.dp, 0.dp, 0.dp)
                                .fillMaxWidth(0.8f)
                        ) {
                            ScrollableAnimatedText(
                                text = homework.name,
                                textColor = Color.White,
                                textAlign = TextAlign.Start,
                                fontSize = font_size_main_text,
                                fontWeight = FontWeight.Bold,
                                lineHeight = line_height_main_text,
                                maxLines = 1,
                                containterModifier = Modifier.fillMaxWidth(),
                                textModifier = Modifier.fillMaxWidth()
                            )
                        }

                        // Button.
                        Icon(
                            imageVector = if (showFullCard.value)
                                Icons.Default.KeyboardArrowUp
                            else
                                Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    showFullCard.value = !showFullCard.value
                                }
                        )
                    }

                    // Study subject.
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                        ) {
                            ScrollableAnimatedText(
                                text = subjectName,
                                textColor = Color.White,
                                textAlign = TextAlign.Start,
                                fontSize = font_size_middle_size_text,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Medium,
                                lineHeight = line_height_middle_size_text,
                                containterModifier = Modifier
                                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
                                    .fillMaxWidth(),
                                textModifier = Modifier
                                    .fillMaxWidth()
                            )
                        }

                        // Status.
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            ScrollableAnimatedText(
                                text = "${statusList.get(homework.status)}",
                                textColor = Color.White,
                                textAlign = TextAlign.End,
                                fontSize = font_size_secondary_text,
                                lineHeight = line_height_secondary_text,
                                maxLines = 1,
                                containterModifier = Modifier.fillMaxWidth(),
                                textModifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                            )
                        }
                    }
                }

            }

        }
    }
}