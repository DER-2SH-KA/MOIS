package ru.der2shka.cursovedcote.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import ru.der2shka.cursovedcote.R
import ru.der2shka.cursovedcote.Service.GetMonthStringResourceByLocalDate
import ru.der2shka.cursovedcote.ui.theme.VeryLightGray
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlin.math.exp

// TODO: Подобрать цвета и оформить!
/**
 *  Analog of DatePicker from Xamarin.
 *  @param selectedLocalDate Mutable selected LocalDate variable
 * **/
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ResourceAsColor")
@Composable
fun DatePickerBox(
    selectedLocalDate: MutableState<LocalDate>,
    outlinedTextFieldModifier: Modifier = Modifier,
    fullMonthName: Boolean = false,
    modifier: Modifier = Modifier,
    onSelect: (LocalDate) -> Unit = {  }
) {
    val config = LocalConfiguration.current

    val expanded = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    // Here date value updates by DatePickerState,
    // but can be null.
    val selectedDate= remember { mutableStateOf(LocalDate.now()) }

    // Update selectedLocalDate by new date.
    if (selectedDate != null) {
        onSelect( selectedDate.value )
    }

    // Building string value by LocalDate for OutputTextField.
    var selectedLocalDateString = "${selectedLocalDate.value.dayOfMonth} " +
                    "${GetMonthStringResourceByLocalDate(selectedLocalDate, fullMonthName)} " +
                    "${selectedLocalDate.value.year}"

    Box(
        modifier = modifier
            .clickable {
                expanded.value = !expanded.value
            }
    ) {
        TextFieldCustom(
            value = selectedLocalDateString,
            onValueChange = { },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "datepicker icon",
                    tint = colorResource(R.color.primary_blue),
                    // Unfortunately, we can tap only on Icon
                    // to open the datepicker popup
                    modifier = Modifier.clickable {
                        expanded.value = !expanded.value
                    }
                )
            },
            modifier = outlinedTextFieldModifier
        )

        /*
        OutlinedTextField(
            value = selectedLocalDateString,
            onValueChange = { },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "datepicker icon",
                    tint = colorResource(R.color.primary_blue),
                    // Unfortunately, we can tap only on Icon
                    // to open the datepicker popup
                    modifier = Modifier.clickable {
                        expanded.value = !expanded.value
                    }
                )
            },
            modifier = outlinedTextFieldModifier
        )*/

        if (expanded.value) {
            DatePickerModal(
                datePickerState = datePickerState,
                onDateSelected = {dateInMills ->
                    selectedDate.value = dateInMills?.let { dateMills ->
                    Instant.ofEpochMilli(dateMills)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                } },
                onDismiss = { expanded.value = !expanded.value }
            )
            /*
            // Expanded window with datepicker.
            Popup(
                onDismissRequest = { expanded.value = false },
                alignment = Alignment.TopStart,
            ) {
                // Datepicker and button
                // to close the expanded window.
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 4.dp)
                        .background(
                            color = colorResource(R.color.primary_blue)
                        )
                        .padding(16.dp)
                    ,
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = true,
                        colors = DatePickerDefaults.colors(
                            containerColor = colorResource(R.color.primary_blue),
                            titleContentColor = VeryLightGray,
                            subheadContentColor = Color.Red,
                            navigationContentColor = Color.White,
                            headlineContentColor = Color.White,
                            dividerColor = Color.White,
                            dayContentColor = Color.White,
                            dayInSelectionRangeContainerColor = colorResource(R.color.tertiary_orange),
                            dayInSelectionRangeContentColor = colorResource(R.color.tertiary_orange),
                            todayDateBorderColor = colorResource(R.color.tertiary_orange),
                            todayContentColor = colorResource(R.color.tertiary_orange),
                            selectedDayContentColor = Color.White,
                            selectedDayContainerColor = colorResource(R.color.secondary_cyan),
                            selectedYearContentColor = Color.White,
                            selectedYearContainerColor = colorResource(R.color.secondary_cyan),
                            weekdayContentColor = VeryLightGray,
                            yearContentColor = Color.White,
                            currentYearContentColor = colorResource(R.color.tertiary_orange),

                            dateTextFieldColors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.White,
                                focusedTextColor = Color.White,
                                focusedLabelColor = Color.White,
                                focusedPrefixColor = Color.White,
                                focusedSuffixColor = Color.White,
                                focusedPlaceholderColor = Color.LightGray,
                                focusedTrailingIconColor = Color.Magenta,
                                focusedLeadingIconColor = Color.Red,
                                focusedSupportingTextColor = Color.Yellow
                            )
                        )
                        ,
                        modifier = Modifier
                    )

                    // Choose and close date picker.
                    Button(
                        onClick = {
                            expanded.value = false
                        }
                        ,
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent,
                            Color.Transparent,
                            Color.Transparent,
                            Color.Transparent
                        ),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(
                                (config.screenHeightDp * 0.1f).dp
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = colorResource(R.color.secondary_cyan)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            ScrollableAnimatedText(
                                text = stringResource(R.string.choose),
                                textColor = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = font_size_main_text,
                                fontWeight = FontWeight.Bold,
                                lineHeight = line_height_main_text,
                                maxLines = 1,
                                textModifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
            */
        }
    }
}