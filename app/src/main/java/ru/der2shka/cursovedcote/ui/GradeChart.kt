package ru.der2shka.cursovedcote.ui

import android.graphics.Color
import android.graphics.DashPathEffect
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import ru.der2shka.cursovedcote.R
import ru.der2shka.cursovedcote.Service.CalculateAverageGradeByDay
import ru.der2shka.cursovedcote.Service.CalculateAverageGradeByMonth
import ru.der2shka.cursovedcote.Service.CalculateAverageGradeByYear
import ru.der2shka.cursovedcote.db.entity.Grade
import ru.der2shka.cursovedcote.db.entity.GradeType
import ru.der2shka.cursovedcote.db.entity.StudySubject
import ru.der2shka.cursovedcote.db.helper.AppDatabase
import ru.der2shka.cursovedcote.ui.theme.font_size_middle_size_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import java.time.Instant
import java.time.ZoneId
import java.util.Optional

@Composable
fun GradeChart(
    // database: AppDatabase,
    gradeData: List<Grade>,
    studySubject: MutableState<Optional<StudySubject>> = mutableStateOf(Optional.empty()),
    gradeTypes: List<GradeType>,
    interval: MutableState<Int> = mutableStateOf(0),
    from: MutableState<LocalDate> = mutableStateOf(Instant.ofEpochMilli( Long.MIN_VALUE )
        .atZone( ZoneId.systemDefault() ).toLocalDate()),
    to: MutableState<LocalDate> = mutableStateOf(Instant.ofEpochMilli( Long.MAX_VALUE )
        .atZone( ZoneId.systemDefault() ).toLocalDate()),
    modifier: Modifier = Modifier
) {
    Log.d("StateUpdate", "selectedSubject: ${studySubject.value.get().name}")
    Log.d("StateUpdate", "selectedInterval: ${interval.value}")
    Log.d("StateUpdate", "selectedLocalDateFrom: ${from.value}")
    Log.d("StateUpdate", "selectedLocalDateTo: ${to.value}")

    val primaryColor = colorResource(R.color.primary_blue).toArgb()
    val secondaryColor = colorResource(R.color.secondary_cyan).toArgb()
    val tetriaryColor = colorResource(R.color.tertiary_orange).toArgb()
    val mainTextColor = colorResource(R.color.main_text_dark_gray).toArgb()

    var profitData = remember(studySubject.value.get() , interval.value, from.value, to.value) { mutableStateOf(mutableListOf<Pair<LocalDate, Float>>()) }

    val dateFormatter = remember {
        mutableStateOf(
            when (interval.value) {
                0 -> { DateTimeFormatter.ofPattern("d MMM yyyy") }
                1 -> {  DateTimeFormatter.ofPattern("MMM yyyy") }
                2 -> {  DateTimeFormatter.ofPattern("yyyy") }
                else -> {  DateTimeFormatter.ofPattern("dd MMM yyyy") }
            }
        )
    }

    profitData = remember(studySubject.value.get() , interval.value, from.value, to.value) {
        mutableStateOf(
        when (interval.value) {
            0 -> {
                CalculateAverageGradeByDay(gradeData, studySubject.value, gradeTypes, from.value, to.value)
            }

            1 -> {
                CalculateAverageGradeByMonth(gradeData, studySubject.value, gradeTypes, from.value, to.value)
            }

            2 -> {
                CalculateAverageGradeByYear(gradeData, studySubject.value, gradeTypes, from.value, to.value)
            }

            else -> {
                CalculateAverageGradeByDay(gradeData, studySubject.value, gradeTypes, from.value, to.value)
            }
        }
        )
    }

    val entries =  remember(profitData.value) { mutableStateOf(profitData.value.mapIndexed { index, (date, profit) ->
                Entry(index.toFloat(), profit.toFloat())
            }
        )
    }

    if (entries.value.isNotEmpty()) {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                LineChart(context).apply {
                    // Настройка данных
                    val dataSet = LineDataSet(entries.value, "").apply {
                        color = secondaryColor
                        valueTextColor = mainTextColor
                        valueTextSize = font_size_secondary_text.value * 0.6f
                        lineWidth = 2f

                        setCircleColor(tetriaryColor)
                        circleRadius = 4f

                        setDrawValues(true)

                        setDrawFilled(true)
                        fillColor = secondaryColor
                        fillAlpha = 150
                    }
                    val lineData = LineData(listOf(dataSet))
                    this.data = lineData

                    // Настройка оси X
                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        valueFormatter = object : ValueFormatter() {
                            override fun getFormattedValue(value: Float): String {
                                try {
                                    // Преобразуем индекс в дату
                                    val date = profitData.value [value.toInt()].first
                                    return date.format(dateFormatter.value)
                                }
                                catch (ex: Exception) {}
                                return LocalDate.now().format(dateFormatter.value)
                            }
                        }
                        granularity = 1f // Минимальный шаг между значениями
                        textSize = font_size_secondary_text.value * 0.6f
                        textColor = mainTextColor

                        setDrawGridLines(true)
                        axisLineWidth = 2f
                        axisLineColor = primaryColor

                        gridColor = primaryColor
                        gridLineWidth = 1f
                    }

                    // Настройка левой оси Y.
                    axisLeft.isEnabled = true
                    axisLeft.apply {
                        setDrawGridLines(true)
                        axisMinimum = 1f
                        axisMaximum = 5f
                        axisLineWidth = 2f
                        axisLineColor = primaryColor
                        setLabelCount(5, true)

                        textColor = primaryColor
                        textSize = font_size_secondary_text.value
                        gridColor = primaryColor
                        gridLineWidth = 1f
                    }

                    // Настройка правой оси Y.
                    axisRight.isEnabled = true
                    axisRight.apply {
                        setDrawGridLines(false)
                        axisMinimum = 1f
                        axisMaximum = 5f
                        axisLineWidth = 2f
                        axisLineColor = primaryColor
                        setLabelCount(5, true)

                        textColor = primaryColor
                        textSize = font_size_secondary_text.value
                    }

                    // Дополнительные настройки
                    description.isEnabled = false // Отключаем описание

                    setTouchEnabled(true) // Включаем возможность взаимодействия с графиком
                    isDragEnabled = true // Включаем перетаскивание
                    setScaleEnabled(true) // Включаем масштабирование
                    setPinchZoom(true) // Включаем масштабирование щипком
                    animateXY(500, 500) // Анимация по оси X
                    // setVisibleXRangeMaximum(4f)
                }
            },
            update = { lineChart ->

                dateFormatter.value = when (interval.value) {
                    0 -> {
                        DateTimeFormatter.ofPattern("d MMM yyyy")
                    }

                    1 -> {
                        DateTimeFormatter.ofPattern("MMM yyyy")
                    }

                    2 -> {
                        DateTimeFormatter.ofPattern("yyyy")
                    }

                    else -> {
                        DateTimeFormatter.ofPattern("dd MMM yyyy")
                    }
                }

                profitData.value = when (interval.value) {
                    0 -> {
                        CalculateAverageGradeByDay(gradeData, studySubject.value, gradeTypes, from.value, to.value)
                    }

                    1 -> {
                        CalculateAverageGradeByMonth(gradeData, studySubject.value, gradeTypes, from.value, to.value)
                    }

                    2 -> {
                        CalculateAverageGradeByYear(gradeData, studySubject.value, gradeTypes, from.value, to.value)
                    }

                    else -> {
                        CalculateAverageGradeByDay(gradeData, studySubject.value, gradeTypes, from.value, to.value)
                    }
                }

                entries.value = profitData.value.mapIndexed { index, (date, profit) ->
                    Entry(index.toFloat(), profit.toFloat())
                }

                // Обновляем данные графика
                val dataSet = LineDataSet(entries.value, "").apply {
                    color = secondaryColor
                    valueTextColor = mainTextColor
                    valueTextSize = font_size_secondary_text.value * 0.6f
                    lineWidth = 2f

                    setCircleColor(tetriaryColor)
                    circleRadius = 4f

                    setDrawValues(true)

                    setDrawFilled(true)
                    fillColor = secondaryColor
                    fillAlpha = 150
                }
                val lineData = LineData(listOf(dataSet))
                lineChart.data = lineData

                // Обновляем формат оси X
                lineChart.xAxis.valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        try {
                            val date = profitData.value [value.toInt()].first
                            return date.format(dateFormatter.value)
                        } catch (ex: Exception) {}
                        return LocalDate.now().format(dateFormatter.value)
                    }
                }

                // Перерисовываем график
                lineChart.invalidate()
            }
        )
    }
}