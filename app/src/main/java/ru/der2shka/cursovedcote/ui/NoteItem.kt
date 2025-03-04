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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.der2shka.cursovedcote.Models.NoteHelper
import ru.der2shka.cursovedcote.R
import ru.der2shka.cursovedcote.Service.GetMonthStringResourceByLocalDate
import ru.der2shka.cursovedcote.current_page
import ru.der2shka.cursovedcote.db.entity.Note
import ru.der2shka.cursovedcote.ui.theme.VeryLightGrayMostlyWhite
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text
import java.time.LocalDate
import java.util.Optional

@SuppressLint("UnrememberedMutableState")
@Composable
fun NoteItem(
    navHostController: NavHostController,
    name: String = "Name",
    description: String = "Description",
    localDate: LocalDate = LocalDate.MIN,
    statusIndex: Int = -1,
    note: Note,
    modifier: Modifier = Modifier
) {
    val noteHelper = NoteHelper.getInstance()

    val dateString = "${localDate.dayOfMonth} " +
            "${
                GetMonthStringResourceByLocalDate(
                    mutableStateOf(localDate), false
                )} " +
            "${localDate.year}"

    val statusList = listOf(
        stringResource(R.string.in_processing),
        stringResource(R.string.waiting_of_verification),
        stringResource(R.string.finished),
        stringResource(R.string.canceled)
    )

    val statusColor = when (statusIndex) {
        0 -> colorResource(R.color.tertiary_orange)
        1 -> colorResource(R.color.warning_yellow)
        2 -> colorResource(R.color.successful_green)
        3 -> colorResource(R.color.error_red)
        else -> colorResource(R.color.additional_purple)
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
                noteHelper.setNoteValue(
                    Optional.ofNullable(note)
                )

                current_page = "edit_note"
                navHostController.navigate(current_page)
            }
    ) {
        // Note Card Item.
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
                // Name.
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .padding(20.dp, 0.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                ) {
                    ScrollableAnimatedText(
                        text = name,
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

                // Description.
                Text(
                    text = "\t\t" + description,
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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                    // .background( Color.Green )
                ) {
                    // Date.
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                        // .background( Color.Red )
                    ) {
                        ScrollableAnimatedText(
                            text = dateString,
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

                    // Status.
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        ScrollableAnimatedText(
                            text = "${statusList.get(statusIndex)}",
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