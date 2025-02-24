package ru.der2shka.cursovedcote.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.der2shka.cursovedcote.R

@SuppressLint("ResourceAsColor")
@Composable
fun TextFieldCustom(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable() (() -> Unit)? = null,
    placeholder: @Composable() (() -> Unit)? = null,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    prefix: @Composable() (() -> Unit)? = null,
    suffix: @Composable() (() -> Unit)? = null,
    supportingText: @Composable() (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember {
        MutableInteractionSource()
    },
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        focusedIndicatorColor = Color.Transparent,
        focusedTextColor = colorResource(R.color.primary_blue),
        focusedLabelColor = Color.White,
        focusedPrefixColor = Color.White,
        focusedSuffixColor = Color.White,
        focusedPlaceholderColor = colorResource(R.color.primary_blue),
        focusedTrailingIconColor = Color.Black,
        focusedLeadingIconColor = Color.Black,
        focusedSupportingTextColor = Color.Black,

        unfocusedContainerColor = colorResource(R.color.background_color),
        unfocusedIndicatorColor = Color.Transparent,
        unfocusedTextColor = Color.Black,
        unfocusedLabelColor = Color.Black,
        unfocusedPrefixColor = Color.White,
        unfocusedSuffixColor = Color.White,
        unfocusedPlaceholderColor = Color.Black,
        unfocusedTrailingIconColor = Color.LightGray,
        unfocusedLeadingIconColor = Color.LightGray,
        unfocusedSupportingTextColor = Color.LightGray,

        errorContainerColor = Color.White,
        errorIndicatorColor = Color.Red,
        errorTextColor = colorResource(R.color.error_red),
        errorLabelColor = Color.White,
        errorPrefixColor = Color.White,
        errorSuffixColor = Color.White,
        errorPlaceholderColor = colorResource(R.color.error_red),
        errorTrailingIconColor = colorResource(R.color.error_red),
        errorLeadingIconColor = colorResource(R.color.error_red),
        errorSupportingTextColor = colorResource(R.color.error_red)

    )
    ) {
    TextField(
        value = value,
        onValueChange = { it ->
            onValueChange( it )
        },
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )
}