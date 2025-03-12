package ru.der2shka.cursovedcote.Service

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.der2shka.cursovedcote.db.entity.Grade
import ru.der2shka.cursovedcote.db.entity.GradeType
import ru.der2shka.cursovedcote.db.entity.StudySubject
import ru.der2shka.cursovedcote.db.helper.AppDatabase
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Optional

// TODO: Сделать учёт множителей.
// @Composable
fun CalculateAverageGradeByMonth(
    gradeData: List<Grade>,
    studySubject: Optional<StudySubject> = Optional.empty(),
    gradeTypes: List<GradeType>,
    localDataFrom: LocalDate,
    localDateTo: LocalDate
) : MutableList<Pair<LocalDate, Float>> {
    var result = mutableListOf<Pair<LocalDate, Float>>()

    var gradeDateFromToAndSubject = when ( studySubject.isPresent ) {
        true -> {
            gradeData.filter { x ->
                var xDate = Instant.ofEpochMilli(x.date )
                    .atZone( ZoneId.systemDefault() )
                    .toLocalDate()
                (localDataFrom <= xDate) && (xDate <= localDateTo)
                        && (x.subjectStudyId == studySubject.get().id)
            }.toList()
        }
        false -> {
            gradeData.filter { x ->
                var xDate = Instant.ofEpochMilli(x.date )
                    .atZone( ZoneId.systemDefault() )
                    .toLocalDate()
                (localDataFrom <= xDate) && (xDate <= localDateTo)
            }.toList()
        }
    }

    var gradeTypesMap = mutableMapOf<Long, GradeType>()
    gradeTypes.forEach {
        gradeTypesMap.put( it.id, it )
    }

    var wasMonthYearList = mutableListOf<Pair<Int, Int>>()

    val scale = Math.pow(10.0, 3.0)

    gradeDateFromToAndSubject.forEach { grade ->
        var localDate = Instant.ofEpochMilli( grade.date )
            .atZone( ZoneId.systemDefault() )
            .toLocalDate()

        if ( !wasMonthYearList
                .contains(
                    Pair(localDate.month.value, localDate.year)
                )
        ) {
            var sum = 0f
            var count = 0
            var average = 0f

            gradeDateFromToAndSubject.stream().filter { x ->
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
                sum += x.gradeValue.toFloat() * gradeTypesMap.get(x.gradeTypeId)!!.mulltiplier
                count += gradeTypesMap.get(x.gradeTypeId)!!.mulltiplier
            }

            average = (Math.round( (sum / count) * scale ) / scale)
                .toFloat()

            result.add( Pair(localDate, average) )
            wasMonthYearList.add(
                Pair(localDate.month.value, localDate.year)
            )
        }
    }

    result.reverse()
    return result
}