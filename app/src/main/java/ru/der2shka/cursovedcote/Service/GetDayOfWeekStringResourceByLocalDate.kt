package ru.der2shka.cursovedcote.Service

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.der2shka.cursovedcote.R
import java.time.LocalDate


/**
 * Return day of weekend string value by LocalDate.
 *  @param localDate localDate from which must get day of weekend name.
 *  @param isFullName if  true return full day of weekend name
 *  else return short day of weekend name;
 * **/
@SuppressLint("ResourceAsString")
@Composable
fun GetDayOfWeekStringResourceByLocalDate(
    localDate: LocalDate,
    isFullName: Boolean
): String {
    var dayOfWeekString = "None"

    if (isFullName) {
        when (localDate.dayOfWeek.value) {
            1 ->
                dayOfWeekString = stringResource(R.string.monday_full_name)

            2 ->
                dayOfWeekString = stringResource(R.string.thusday_full_name)

            3 ->
                dayOfWeekString = stringResource(R.string.wednesday_full_name)

            4 ->
                dayOfWeekString = stringResource(R.string.tuesday_full_name)

            5 ->
                dayOfWeekString = stringResource(R.string.friday_full_name)

            6 ->
                dayOfWeekString = stringResource(R.string.saturday_full_name)

            7 ->
                dayOfWeekString = stringResource(R.string.sunday_full_name)
        }
    }
    else {
        when (localDate.dayOfWeek.value) {
            1 ->
                dayOfWeekString = stringResource(R.string.monday_short_name)

            2 ->
                dayOfWeekString = stringResource(R.string.thusday_short_name)

            3 ->
                dayOfWeekString = stringResource(R.string.wednesday_short_name)

            4 ->
                dayOfWeekString = stringResource(R.string.tuesday_short_name)

            5 ->
                dayOfWeekString = stringResource(R.string.friday_short_name)

            6 ->
                dayOfWeekString = stringResource(R.string.saturday_short_name)

            7 ->
                dayOfWeekString = stringResource(R.string.sunday_short_name)
        }
    }

    return dayOfWeekString
}