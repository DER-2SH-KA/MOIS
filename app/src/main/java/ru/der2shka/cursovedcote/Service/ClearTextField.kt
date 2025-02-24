package ru.der2shka.cursovedcote.Service

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue

fun ClearTextField(
    textFieldValue: MutableState<TextFieldValue>
) {
    textFieldValue.value = TextFieldValue("")
}