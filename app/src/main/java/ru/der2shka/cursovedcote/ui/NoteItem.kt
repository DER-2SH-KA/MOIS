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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import ru.der2shka.cursovedcote.curr_page
import ru.der2shka.cursovedcote.current_page
import ru.der2shka.cursovedcote.db.entity.Note
import ru.der2shka.cursovedcote.ui.theme.VeryLightGrayMostlyWhite
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_middle_size_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_middle_size_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Optional

@SuppressLint("UnrememberedMutableState")
@Composable
fun NoteItem(
    navHostController: NavHostController,
    note: Note,
    modifier: Modifier = Modifier
) {
    val noteHelper = NoteHelper.getInstance()
    val showFullCard = remember { mutableStateOf(false) }

    var localDate = Instant
        .ofEpochMilli( note.date)
        .atZone( ZoneId.systemDefault() )
        .toLocalDate()

    val dateString = "${localDate.dayOfMonth} " +
            "${
                GetMonthStringResourceByLocalDate(
                    mutableStateOf(localDate), false
                )} " +
            "${localDate.year}"

    val statusList = SomeConstantValues().getStatusList()

    val statusColor = when (note.status) {
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
                noteHelper.setNoteValue(
                    Optional.ofNullable(note)
                )

                curr_page = 3

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
                                text = note.name,
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

                    // Description.
                    Text(
                        text = "\t\t" + note.description,
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
                                text = "${statusList.get(note.status)}",
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
                                text = note.name,
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
                        // Status.
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            ScrollableAnimatedText(
                                text = "${statusList.get(note.status)}",
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