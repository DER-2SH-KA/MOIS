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
import ru.der2shka.cursovedcote.Models.GradeTypeHelper
import ru.der2shka.cursovedcote.Models.NoteHelper
import ru.der2shka.cursovedcote.R
import ru.der2shka.cursovedcote.Service.GetMonthStringResourceByLocalDate
import ru.der2shka.cursovedcote.curr_page
import ru.der2shka.cursovedcote.current_page
import ru.der2shka.cursovedcote.db.entity.GradeType
import ru.der2shka.cursovedcote.db.entity.Note
import ru.der2shka.cursovedcote.ui.theme.VeryLightGrayMostlyWhite
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_middle_size_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_middle_size_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text
import java.time.LocalDate
import java.util.Optional

@SuppressLint("UnrememberedMutableState")
@Composable
fun GradeTypeItem(
    navHostController: NavHostController,
    gradeType: GradeType,
    modifier: Modifier = Modifier
) {
    val gradeTypeHelper = GradeTypeHelper.getInstance()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(0.dp, 10.dp, 0.dp, 10.dp)
            .background(
                color = colorResource(R.color.primary_blue),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                gradeTypeHelper.setGradeTypeValue(
                    Optional.ofNullable( gradeType )
                )

                curr_page = -1

                current_page = "edit_grade_type"
                navHostController.navigate(current_page)
            }
    ) {
        // GradeType Card Item.
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
                        text = gradeType.name,
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
                // Multiplier
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Multiplier Text.
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                    ) {
                        ScrollableAnimatedText(
                            text = "${stringResource(R.string.multiplier)}:",
                            textColor = Color.White,
                            textAlign = TextAlign.Start,
                            fontSize = font_size_middle_size_text,
                            fontStyle = FontStyle.Italic,
                            lineHeight = line_height_middle_size_text,
                            textModifier = Modifier
                                .fillMaxWidth(0.9f),
                            containterModifier = Modifier
                                .fillMaxWidth()
                        )
                    }

                    // Multiplier Value.
                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                    ) {
                        ScrollableAnimatedText(
                            text = gradeType.mulltiplier.toString(),
                            textColor = Color.White,
                            textAlign = TextAlign.End,
                            fontSize = font_size_middle_size_text,
                            fontStyle = FontStyle.Italic,
                            lineHeight = line_height_middle_size_text,
                            textModifier = Modifier
                                .fillMaxWidth(0.9f),
                            containterModifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }

            }
        }
    }
}