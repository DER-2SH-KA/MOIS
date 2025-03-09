package ru.der2shka.cursovedcote.Service

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue

fun checkMultiplierTextFieldValue(
    multiplierTextField: MutableState<TextFieldValue>
): Boolean
{
    var result = false

    try {
        var m = multiplierTextField.value.text.toInt()
        result = true
    }
    catch (ex: NumberFormatException) {
        result = false
    }
    catch (ex: Exception) {
        result = false
    }

    return result
}