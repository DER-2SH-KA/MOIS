package ru.der2shka.cursovedcote.Service

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import ru.der2shka.cursovedcote.R
import java.time.LocalDate

// TODO: finish month when block.
/**
 * Return month string value by LocalDate.
 *  @param localDate localDate from which must get month name.
 *  @param isFullName if  true return full month name
 *  else return short month name;
 * **/
@SuppressLint("ResourceAsString")
@Composable
fun GetMonthStringResourceByLocalDate(
    localDate: MutableState<LocalDate>,
    isFullName: Boolean
): String {
    var monthString = "None"

    if (isFullName) {
        when (localDate.value.month.value) {
            1 ->
                monthString = stringResource(R.string.january_full_name)

            2 ->
                monthString = stringResource(R.string.february_full_name)

            3 ->
                monthString = stringResource(R.string.march_full_name)

            4 ->
                monthString = stringResource(R.string.april_full_name)

            5 ->
                monthString = stringResource(R.string.may_full_name)

            6 ->
                monthString = stringResource(R.string.june_full_name)

            7 ->
                monthString = stringResource(R.string.july_full_name)

            8 ->
                monthString = stringResource(R.string.august_full_name)

            9 ->
                monthString = stringResource(R.string.september_full_name)

            10 ->
                monthString = stringResource(R.string.october_full_name)

            11 ->
                monthString = stringResource(R.string.november_full_name)

            12 ->
                monthString = stringResource(R.string.december_full_name)
        }
    }
    else {
        when (localDate.value.month.value) {
            1 ->
                monthString = stringResource(R.string.january_short_name)

            2 ->
                monthString = stringResource(R.string.february_short_name)

            3 ->
                monthString = stringResource(R.string.march_short_name)

            4 ->
                monthString = stringResource(R.string.april_short_name)

            5 ->
                monthString = stringResource(R.string.may_short_name)

            6 ->
                monthString = stringResource(R.string.june_short_name)

            7 ->
                monthString = stringResource(R.string.july_short_name)

            8 ->
                monthString = stringResource(R.string.august_short_name)

            9 ->
                monthString = stringResource(R.string.september_short_name)

            10 ->
                monthString = stringResource(R.string.october_short_name)

            11 ->
                monthString = stringResource(R.string.november_short_name)

            12 ->
                monthString = stringResource(R.string.december_short_name)
        }
    }

    return monthString
}