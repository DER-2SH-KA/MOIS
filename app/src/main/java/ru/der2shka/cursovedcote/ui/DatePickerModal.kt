package ru.der2shka.cursovedcote.ui

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import ru.der2shka.cursovedcote.R
import ru.der2shka.cursovedcote.ui.theme.VeryLightGray
import ru.der2shka.cursovedcote.ui.theme.VeryLightGrayMostlyWhite
import ru.der2shka.cursovedcote.ui.theme.font_size_middle_size_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_middle_size_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    datePickerState: DatePickerState,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    // val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                }
            ) {
                Text(
                    text = stringResource(R.string.ok),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = font_size_secondary_text,
                    lineHeight = line_height_secondary_text,
                    overflow = TextOverflow.Visible
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = font_size_secondary_text,
                    lineHeight = line_height_secondary_text,
                    overflow = TextOverflow.Visible
                )
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = colorResource(R.color.primary_blue),
        )
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

                )
            ),
            modifier = Modifier
        )
    }

}