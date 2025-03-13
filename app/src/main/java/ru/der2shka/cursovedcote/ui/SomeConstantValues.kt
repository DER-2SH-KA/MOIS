package ru.der2shka.cursovedcote.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.der2shka.cursovedcote.R

class SomeConstantValues {

    @Composable
    fun getStatusList(): List<String> {
        return listOf(
            stringResource(R.string.created),
            stringResource(R.string.in_processing),
            stringResource(R.string.waiting_of_verification),
            stringResource(R.string.finished),
            stringResource(R.string.canceled)
        )
    }

    fun getGradeValueList(): List<String> {
        return listOf("5", "4", "3", "2", "1")
    }

    @Composable
    fun getIntervalList(): List<String> {
        return listOf(
            stringResource(R.string.day),
            stringResource(R.string.month),
            stringResource(R.string.year)
        )
    }

    @Composable
    fun getLanguageList(): List<String> {
        return listOf(
            stringResource(R.string.english),
            stringResource(R.string.russian_ru),
        )
    }

    @Composable
    fun getThemeList(): List<String> {
        return listOf(
            stringResource(R.string.dark),
            stringResource(R.string.light),
        )
    }
}