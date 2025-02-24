package ru.der2shka.cursovedcote.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import ru.der2shka.cursovedcote.R
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text

// TODO: Подобрать цвета и оформить!
/**
 *  Analog of ComboBox from HTML or WindowsForms.
 *  @param items List of items of combobox
 *  @param selectedItem Mutable state of variable for selected item
 * **/
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ResourcesAsColor")
@Composable
fun ComboBoxPseudo(
    items: List<String>,
    selectedItem: MutableState<String>,
    onSelect: (String) -> Unit = {  },
    outlinedTextFieldModifier: Modifier = Modifier,
    modifier: Modifier = Modifier
) {
    val expanded = remember { mutableStateOf(false) }
    val verticalScrollState = rememberScrollState(0)

    Box(
        modifier = modifier
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded.value,
            onExpandedChange = {
                expanded.value = !expanded.value
            }
        ) {
            TextFieldCustom(
                value = selectedItem.value,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "dropdown icon",
                        tint = colorResource(R.color.primary_blue),
                        modifier = Modifier.clickable { expanded.value = !expanded.value }
                    )
                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = colorResource(R.color.primary_blue),
                        shape = RoundedCornerShape(5.dp)
                    )
                    .menuAnchor()
            )
            /*
            OutlinedTextField(
                value = selectedItem.value,
                onValueChange = {},
                readOnly = true,
                maxLines = 1,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "dropdown icon",
                        tint = colorResource(R.color.primary_blue),
                        modifier = Modifier.clickable { expanded.value = !expanded.value }
                    )
                },
                modifier = Modifier.menuAnchor()
            )*/

            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = {
                    expanded.value = false
                },
                modifier = Modifier
                    .background(
                        color =  colorResource(R.color.primary_blue) //Color.White
                    )
                    .verticalScroll( verticalScrollState )
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item,
                                color = Color.White,
                                textAlign = TextAlign.Start,
                                fontSize = font_size_secondary_text,
                                lineHeight = line_height_secondary_text,
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                            )
                               },
                        onClick = {
                            onSelect(item)
                            expanded.value = false
                        }
                    )
                }
            }
        }
    }
}