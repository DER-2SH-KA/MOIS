package ru.der2shka.cursovedcote.Service

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.der2shka.cursovedcote.db.entity.Grade
import ru.der2shka.cursovedcote.db.entity.GradeType
import ru.der2shka.cursovedcote.db.helper.AppDatabase
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

// TODO: Сделать учёт множителей.
// @Composable
fun CalculateAverageMarkByMonth(
    gradeData: List<Grade>,
    // database: AppDatabase
) : MutableList<Pair<LocalDate, Float>> {
    var result = mutableListOf<Pair<LocalDate, Float>>()

    // val coroutineScope = rememberCoroutineScope()

    var wasMonthYearList = mutableListOf<Pair<Int, Int>>()

    val scale = Math.pow(10.0, 3.0)

    gradeData.forEach { grade ->
        var localDate = Instant.ofEpochMilli( grade.date )
            .atZone( ZoneId.systemDefault() )
            .toLocalDate()
        // var gradeType: GradeType

        if ( !wasMonthYearList
                .contains(
                    Pair(localDate.month.value, localDate.year)
                )
        ) {
            /*LaunchedEffect(key1 = Unit) {
                coroutineScope.launch(Dispatchers.IO) {
                    gradeType = database.gradeTypeDao()
                        .findGradeTypeById( grade.gradeTypeId )
                }
            }*/

            var sum = 0f
            var count = 0
            var average = 0f

            gradeData.stream().filter { x ->
                // By month.
                (Instant.ofEpochMilli(x.date )
                    .atZone( ZoneId.systemDefault() )
                    .toLocalDate().monthValue ==
                        localDate.monthValue) &&
                        // By year.
                        (Instant.ofEpochMilli(x.date )
                            .atZone( ZoneId.systemDefault() )
                            .toLocalDate().year == localDate.year)
            }.forEach { x ->
                sum += x.gradeValue.toFloat() // * multiplier
                count++
            }

            average = (Math.round( (sum / count) * scale ) / scale)
                .toFloat()

            result.add( Pair(localDate, average) )
            wasMonthYearList.add(
                Pair(localDate.month.value, localDate.year)
            )
        }
    }

    return result
}