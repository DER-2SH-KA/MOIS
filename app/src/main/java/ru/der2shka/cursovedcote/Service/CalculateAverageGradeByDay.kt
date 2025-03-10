package ru.der2shka.cursovedcote.Service

import ru.der2shka.cursovedcote.db.entity.Grade
import ru.der2shka.cursovedcote.db.entity.GradeType
import ru.der2shka.cursovedcote.db.entity.StudySubject
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Optional

// TODO: Сделать учёт множителей.
fun CalculateAverageGradeByDay(
    gradeData: List<Grade>,
    studySubject: Optional<StudySubject> = Optional.empty(),
    gradeTypes: List<GradeType>,
    localDataFrom: LocalDate,
    localDateTo: LocalDate
) : MutableList<Pair<LocalDate, Float>> {

    var result = mutableListOf<Pair<LocalDate, Float>>()

    var gradeDateFromToAndSubject = when ( studySubject.isPresent ) {
        true -> {
            gradeData.stream().filter { x ->
                var xDate = Instant.ofEpochMilli(x.date )
                    .atZone( ZoneId.systemDefault() )
                    .toLocalDate()
                (localDataFrom <= xDate) && (xDate <= localDateTo)
                        && (x.subjectStudyId == studySubject.get().id)
            }
        }
        false -> {
            gradeData.stream().filter { x ->
                var xDate = Instant.ofEpochMilli(x.date )
                    .atZone( ZoneId.systemDefault() )
                    .toLocalDate()
                (localDataFrom <= xDate) && (xDate <= localDateTo)
            }
        }
    }

    var gradeTypesMap = mutableMapOf<Long, GradeType>()
    gradeTypes.forEach {
        gradeTypesMap.put( it.id, it )
    }

    var wasMonthYearList = mutableListOf<LocalDate>()

    val scale = Math.pow(10.0, 3.0)

    gradeDateFromToAndSubject.forEach { grade ->

        var localDate = Instant.ofEpochMilli( grade.date )
            .atZone( ZoneId.systemDefault() )
            .toLocalDate()

        if ( !wasMonthYearList
                .contains(
                    localDate
                )
        ) {
            var sum = 0f
            var count = 0
            var average = 0f

            gradeData.stream().filter { x ->
                var xDate = Instant.ofEpochMilli(x.date )
                    .atZone( ZoneId.systemDefault() )
                    .toLocalDate()
                // By full local date.
                xDate == localDate

            }.forEach { x ->
                sum += x.gradeValue.toFloat() * gradeTypesMap.get(x.gradeTypeId)!!.mulltiplier
                count += gradeTypesMap.get(x.gradeTypeId)!!.mulltiplier
            }

            average = (Math.round( (sum / count) * scale ) / scale)
                .toFloat()

            result.add( Pair(localDate, average) )
            wasMonthYearList.add(
                localDate
            )
        }
    }

    result.reverse()
    return result
}