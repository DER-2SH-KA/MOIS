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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import ru.der2shka.cursovedcote.Models.GradeHelper
import ru.der2shka.cursovedcote.Models.HomeworkHelper
import ru.der2shka.cursovedcote.R
import ru.der2shka.cursovedcote.Service.GetMonthStringResourceByLocalDate
import ru.der2shka.cursovedcote.curr_page
import ru.der2shka.cursovedcote.current_page
import ru.der2shka.cursovedcote.db.entity.Grade
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
fun GradeItem(
    navHostController: NavHostController,
    grade: Grade,
    subjectName: String,
    gradeTypeName: String,
    database: AppDatabase,
    modifier: Modifier = Modifier
) {
    val gradeHelper = GradeHelper.getInstance()

    // Date of write.
    var localDate = Instant
        .ofEpochMilli( grade.date)
        .atZone( ZoneId.systemDefault() )
        .toLocalDate()

    var dateOfWriteString = "${localDate.dayOfMonth} " +
            "${
                GetMonthStringResourceByLocalDate(
                    mutableStateOf(localDate), false
                )} " +
            "${localDate.year}"

    // Grade value color.
    val gradeValueList = SomeConstantValues().getGradeValueList()

    val gradeValueColor = when (grade.gradeValue) {
        1 -> colorResource(R.color.error_red)
        2 -> colorResource(R.color.error_red)
        3 -> colorResource(R.color.warning_yellow)
        4 -> colorResource(R.color.tertiary_orange)
        5 -> colorResource(R.color.successful_green)
        else -> colorResource(R.color.black)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(0.dp, 10.dp, 0.dp, 10.dp)
            .background(
                color = gradeValueColor,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                gradeHelper.setGradeValue( Optional.ofNullable( grade ) )
                curr_page = 0

                current_page = "edit_grade"
                navHostController.navigate(current_page)
            }
    ) {
        // Grade Card Item.
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(20.dp)
            ) {
                // Study subject.
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .padding(20.dp, 0.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                ) {
                    ScrollableAnimatedText(
                        text = subjectName,
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

                // Grade info.
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(0.dp, 10.dp)
                        .fillMaxWidth()
                ) {
                    // Grade value.
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                        ) {
                            ScrollableAnimatedText(
                                text = "${stringResource(R.string.grade)}:",
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
                        ) {
                            ScrollableAnimatedText(
                                text = grade.gradeValue.toString(),
                                textColor = Color.White,
                                textAlign = TextAlign.End,
                                fontSize = font_size_secondary_text,
                                fontStyle = FontStyle.Italic,
                                lineHeight = line_height_secondary_text,
                                textModifier = Modifier
                                    .fillMaxWidth(0.9f),
                                containterModifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }

                    // Grade type.
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                        ) {
                            ScrollableAnimatedText(
                                text = "${stringResource(R.string.grade_type)}:",
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
                        ) {
                            ScrollableAnimatedText(
                                text = gradeTypeName,
                                textColor = Color.White,
                                textAlign = TextAlign.End,
                                fontSize = font_size_secondary_text,
                                fontStyle = FontStyle.Italic,
                                lineHeight = line_height_secondary_text,
                                textModifier = Modifier
                                    .fillMaxWidth(0.9f),
                                containterModifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                }

                // Dates.
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                    //.background(Color.Red)
                ) {
                    // Date.
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

                }
            }
        }
    }
}