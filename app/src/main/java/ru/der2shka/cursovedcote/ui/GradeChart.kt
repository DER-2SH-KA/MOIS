package ru.der2shka.cursovedcote.ui

import androidx.compose.runtime.Composable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.github.mikephil.charting.data.Entry

@Composable
fun GradeChart() {
    val profitData = listOf(
        Pair(LocalDate.of(2024, 10, 1), 100.0),
        Pair(LocalDate.of(2024, 11, 1), 150.0),
        Pair(LocalDate.of(2024, 12, 1), 200.0),
        Pair(LocalDate.of(2025, 1, 1), 180.0),
        Pair(LocalDate.of(2025, 2, 1), 250.0),
        Pair(LocalDate.of(2025, 3, 1), 300.0),
    )

    val dateFormatter = DateTimeFormatter.ofPattern("MMM yyyy")

    val entries = profitData.mapIndexed { index, (date, profit) ->
        Entry(index.toFloat(), profit.toFloat())
    }
}