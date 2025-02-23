package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.der2shka.cursovedcote.Models.AddNewMarkHelper
import ru.der2shka.cursovedcote.Service.GetMonthStringResourceByLocalDate
import ru.der2shka.cursovedcote.ui.ComboBoxPseudo
import ru.der2shka.cursovedcote.ui.DatePickerBox
import ru.der2shka.cursovedcote.ui.ScrollableAnimatedText
import ru.der2shka.cursovedcote.ui.theme.VeryLightGrayMostlyWhite
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text
import java.time.LocalDate
import java.util.Date
import java.util.Optional

// TODO: Подобрать цвета и оформить!
/**
 * Page for adding new mark in system.
 * **/
@SuppressLint("ResourceAsColor", "UnrememberedMutableState")
@Composable
fun AddNewStudySubject(
    navHostController: NavHostController
) {
    val config = LocalConfiguration.current
    val oneBlockHeight = (config.screenHeightDp * 0.2).dp
    val verticalMainScroll = rememberScrollState(0)

    // TODO: Create Singleton for it.
    // val addNewMarkHelper: AddNewMarkHelper = AddNewMarkHelper.getInstance()

    val textFieldName = remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            //.verticalScroll( verticalMainScroll )
        ,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.85f)
                .fillMaxWidth(0.9f)
            ,
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column() {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(oneBlockHeight.value.dp)
                        .padding(10.dp)
                        .background(
                            color = colorResource(R.color.primary_blue),
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    // Header Text.
                    ScrollableAnimatedText(
                        text = stringResource(R.string.add_subject),
                        textColor = Color.White,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        fontSize = font_size_main_text,
                        lineHeight = line_height_main_text,
                        fontWeight = FontWeight.Bold,
                        textModifier = Modifier.fillMaxWidth()
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Choice of mark value.
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Text(text = "Markuo Value")

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                        ) {
                            ScrollableAnimatedText(
                                text = "${stringResource(R.string.name)}:",
                                textColor = colorResource(R.color.secondary_text_gray),
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                fontSize = font_size_secondary_text,
                                lineHeight = line_height_secondary_text,
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            TextField(
                                value = textFieldName.value.text,
                                onValueChange = {
                                    textFieldName.value = TextFieldValue(it)
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    focusedIndicatorColor = Color.White,
                                    focusedTextColor = colorResource(R.color.primary_blue),
                                    focusedLabelColor = Color.White,
                                    focusedPrefixColor = Color.White,
                                    focusedSuffixColor = Color.White,
                                    focusedPlaceholderColor = colorResource(R.color.primary_blue),
                                    focusedTrailingIconColor = Color.Black,
                                    focusedLeadingIconColor = Color.Black,
                                    focusedSupportingTextColor = Color.Black,

                                    unfocusedContainerColor = VeryLightGrayMostlyWhite,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    unfocusedTextColor = Color.Black,
                                    unfocusedLabelColor = Color.Black,
                                    unfocusedPrefixColor = Color.White,
                                    unfocusedSuffixColor = Color.White,
                                    unfocusedPlaceholderColor = Color.Black,
                                    unfocusedTrailingIconColor = Color.LightGray,
                                    unfocusedLeadingIconColor = Color.LightGray,
                                    unfocusedSupportingTextColor = Color.LightGray,

                                    errorContainerColor = Color.White,
                                    errorIndicatorColor = Color.Red,
                                    errorTextColor = colorResource(R.color.error_red),
                                    errorLabelColor = Color.White,
                                    errorPrefixColor = Color.White,
                                    errorSuffixColor = Color.White,
                                    errorPlaceholderColor = colorResource(R.color.error_red),
                                    errorTrailingIconColor = colorResource(R.color.error_red),
                                    errorLeadingIconColor = colorResource(R.color.error_red),
                                    errorSupportingTextColor = colorResource(R.color.error_red)

                                ),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 2.dp,
                                        color = colorResource(R.color.primary_blue),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                            )
                        }
                    }

                    /*
                // Only for testing.
                Text(text = "Mark value: ${selectedMarkValue.value}")
                Text(text = "Mark type: ${selectedMarkType.value}")
                Text(text = "Subject Value: ${selectedSubjectValue.value}")
                Text(
                    text = "Date: ${selectedLocalDate.value.dayOfMonth} " +
                            "${
                                GetMonthStringResourceByLocalDate(
                                    selectedLocalDate, true
                                )
                            } " +
                            "${selectedLocalDate.value.year}"
                )
                 */
                }
            }

            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Button to Add.
                Button(
                    onClick = { },

                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            (oneBlockHeight * 0.5f)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colorStops = arrayOf(
                                        0.6f to colorResource(R.color.primary_blue),
                                        1f to colorResource(R.color.secondary_cyan)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        ScrollableAnimatedText(
                            text = stringResource(R.string.add),
                            textColor = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = font_size_main_text,
                            fontWeight = FontWeight.Bold,
                            lineHeight = line_height_main_text,
                            containterModifier = Modifier
                                .fillMaxWidth(0.9f),
                            textModifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // Button to Back.
                Button(
                    onClick = {
                        current_page = "general_app"
                        navHostController.navigate(current_page)
                    },

                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            (oneBlockHeight * 0.5f)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colorStops = arrayOf(
                                        0.6f to colorResource(R.color.primary_blue),
                                        1f to colorResource(R.color.secondary_cyan)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        ScrollableAnimatedText(
                            text = stringResource(R.string.back),
                            textColor = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = font_size_main_text,
                            fontWeight = FontWeight.Bold,
                            lineHeight = line_height_main_text,
                            containterModifier = Modifier
                                .fillMaxWidth(0.9f),
                            textModifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}