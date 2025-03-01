package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.der2shka.cursovedcote.Models.AddNewNoteHelper
import ru.der2shka.cursovedcote.Service.ClearTextField
import ru.der2shka.cursovedcote.Service.GetMonthStringResourceByLocalDate
import ru.der2shka.cursovedcote.db.entity.Note
import ru.der2shka.cursovedcote.db.helper.AppDatabase
import ru.der2shka.cursovedcote.ui.ComboBoxPseudo
import ru.der2shka.cursovedcote.ui.DatePickerBox
import ru.der2shka.cursovedcote.ui.ScrollableAnimatedText
import ru.der2shka.cursovedcote.ui.TextFieldCustom
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Optional

// TODO: Подобрать цвета и оформить!
/**
 * Page for adding new mark in system.
 * **/
@SuppressLint("ResourceAsColor", "UnrememberedMutableState")
@Composable
fun AddNewNote(
    navHostController: NavHostController,
    database: AppDatabase
) {
    val config = LocalConfiguration.current
    val oneBlockHeight = (config.screenHeightDp * 0.2).dp
    val verticalMainScroll = rememberScrollState(0)

    val coroutineScope = rememberCoroutineScope()
    val addNewNoteHelper: AddNewNoteHelper = AddNewNoteHelper.getInstance()

    val statusList = listOf(
        stringResource(R.string.in_processing),
        stringResource(R.string.waiting_of_verification),
        stringResource(R.string.finished),
        stringResource(R.string.canceled)
    )

    // Name TextField.
    val nameTextFieldValue = remember {
        mutableStateOf(
            TextFieldValue( addNewNoteHelper.nameValue )
        )
    }

    // Description TextField.
    val descriptionTextFieldValue = remember {
        mutableStateOf(
            TextFieldValue( addNewNoteHelper.descriptionValue )
        )
    }

    // Day of write.
    val selectedDateOfWrite = remember {
        mutableStateOf( addNewNoteHelper.dateOfWrite )
    }

    val statusCodeMutable = remember {
        mutableStateOf(addNewNoteHelper.statusCodeValue)
    }

    // Status TextField.
    val selectedStatusItem = remember {
        when (statusCodeMutable.value) {
            0 -> mutableStateOf( TextFieldValue(statusList.get(0)) )
            1 -> mutableStateOf( TextFieldValue(statusList.get(1)) )
            2 -> mutableStateOf( TextFieldValue(statusList.get(2)) )
            3 -> mutableStateOf( TextFieldValue(statusList.get(3)) )
            else -> { mutableStateOf(TextFieldValue("None Value")) }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.85f)
            ,
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Text.
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(oneBlockHeight.value.dp)
                    .padding(10.dp)
                    .background(
                        color = colorResource(R.color.primary_blue),
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                ScrollableAnimatedText(
                    text = stringResource(R.string.add_note),
                    textColor = Color.White,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    fontSize = font_size_main_text,
                    lineHeight = line_height_main_text,
                    fontWeight = FontWeight.Bold,
                    textModifier = Modifier.fillMaxWidth()
                )
            }

            // Fields.
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .verticalScroll( verticalMainScroll )
            ){
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Set of name.
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                        ) {
                            ScrollableAnimatedText(
                                text = "${stringResource(R.string.name)}:",
                                textColor = colorResource(R.color.main_text_dark_gray),
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                fontSize = font_size_secondary_text,
                                lineHeight = line_height_secondary_text,
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            TextFieldCustom(
                                value = nameTextFieldValue.value.text,
                                onValueChange = {
                                    nameTextFieldValue.value = TextFieldValue(it)
                                },
                                singleLine = true,
                                shape = RoundedCornerShape(5.dp),
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = null,
                                        tint = colorResource(R.color.primary_blue),
                                        modifier = Modifier
                                            .clickable {
                                                ClearTextField(nameTextFieldValue)
                                            }
                                    )
                                },

                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth()
                                    .border(
                                        width = 2.dp,
                                        color = colorResource(R.color.primary_blue),
                                        shape = RoundedCornerShape(5.dp)
                                    ),
                            )
                        }
                    }

                    // Set of description.
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                        ) {
                            ScrollableAnimatedText(
                                text = "${stringResource(R.string.description)}:",
                                textColor = colorResource(R.color.main_text_dark_gray),
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                fontSize = font_size_secondary_text,
                                lineHeight = line_height_secondary_text,
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            TextFieldCustom(
                                value = descriptionTextFieldValue.value.text,
                                onValueChange = {
                                    descriptionTextFieldValue.value = TextFieldValue(it)
                                },
                                shape = RoundedCornerShape(5.dp),
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = null,
                                        tint = colorResource(R.color.primary_blue),
                                        modifier = Modifier
                                            .clickable {
                                                ClearTextField(descriptionTextFieldValue)
                                            }
                                    )
                                               },
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth()
                                    .border(
                                        width = 2.dp,
                                        color = colorResource(R.color.primary_blue),
                                        shape = RoundedCornerShape(5.dp)
                                    ),
                            )
                        }
                    }

                    // Choice of date of write.
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Text(text = "Dateo")

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                        ) {
                            ScrollableAnimatedText(
                                text = "${stringResource(R.string.date)}:",
                                textColor = colorResource(R.color.main_text_dark_gray),
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                fontSize = font_size_secondary_text,
                                lineHeight = line_height_secondary_text,
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            DatePickerBox(
                                selectedLocalDate = selectedDateOfWrite,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth()
                                    .border(
                                        width = 2.dp,
                                        color = colorResource(R.color.primary_blue),
                                        shape = RoundedCornerShape(5.dp)
                                    )
                                ,
                                onSelect = { localDate ->
                                    // addNewNoteHelper.setDateOfWrite( Optional.ofNullable(localDate) )
                                    selectedDateOfWrite.value = localDate
                                }
                            )
                        }
                    }

                    // Set of status.
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                        ) {
                            ScrollableAnimatedText(
                                text = "${stringResource(R.string.status)}:",
                                textColor = colorResource(R.color.main_text_dark_gray),
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                fontSize = font_size_secondary_text,
                                lineHeight = line_height_secondary_text,
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            ComboBoxPseudo(
                                items = statusList,
                                selectedItem = mutableStateOf(
                                    selectedStatusItem.value.text
                                ),
                                onSelect = { value ->
                                    when (value) {
                                        // In processed.
                                        statusList.get(0) -> {
                                            addNewNoteHelper.setStatusCodeValue(
                                                Optional.ofNullable(0)
                                            )
                                        }
                                        // Waiting of verification.
                                        statusList.get(1) -> {
                                            addNewNoteHelper.setStatusCodeValue(
                                                Optional.ofNullable(1)
                                            )
                                        }
                                        // Finished.
                                        statusList.get(2) -> {
                                            addNewNoteHelper.setStatusCodeValue(
                                                Optional.ofNullable(2)
                                            )
                                        }
                                        // Canceled.
                                        statusList.get(3) -> {
                                            addNewNoteHelper.setStatusCodeValue(
                                                Optional.ofNullable(3)
                                            )
                                        }
                                        else -> {
                                            addNewNoteHelper.setStatusCodeValue(
                                                Optional.ofNullable(-1)
                                            )
                                        }
                                    }

                                    selectedStatusItem.value = TextFieldValue(value)
                                },

                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth()
                            )
                        }

                    }

                    /*
                    // Only for testing.
                    Text(text = "Name: ${nameTextFieldValue.value.text}")
                    Text(text = "Description: ${descriptionTextFieldValue.value.text}")
                    Text(
                        text = "Date: ${selectedDateOfWrite.value.dayOfMonth} " +
                                "${
                                    GetMonthStringResourceByLocalDate(
                                        selectedDateOfWrite, true
                                    )
                                } " +
                                "${selectedDateOfWrite.value.year}"
                    )
                    Text(text = "Status: ${selectedStatusItem.value.text}")
                    Text(
                        text = "Date: ${addNewNoteHelper.dateOfWrite.dayOfMonth} " +
                                "${
                                    GetMonthStringResourceByLocalDate(
                                        mutableStateOf(addNewNoteHelper.dateOfWrite), true
                                    )
                                } " +
                                "${selectedDateOfWrite.value.year}"
                    )
                    */
                }
            }

            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Button to Add.
                Button(
                    onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            // Add data into Helper object.
                            addNewNoteHelper.setNameValue(
                                Optional.ofNullable(nameTextFieldValue.value.text)
                            )
                            addNewNoteHelper.setDescriptionValue(
                                Optional.ofNullable(descriptionTextFieldValue.value.text)
                            )
                            addNewNoteHelper.setDateOfWrite(
                                Optional.ofNullable(selectedDateOfWrite.value)
                            )
                            addNewNoteHelper.setStatusCodeValue(
                                Optional.ofNullable( statusList.indexOf( selectedStatusItem.value.text ) )
                            )

                            // Add data into DataBase.
                            var dateInMills: Long = selectedDateOfWrite.value
                                .atStartOfDay()
                                .atZone( ZoneId.systemDefault() )
                                .toInstant()
                                .toEpochMilli()

                            var newNote = Note(
                                name = addNewNoteHelper.nameValue,
                                description = addNewNoteHelper.descriptionValue,
                                date = dateInMills,
                                status = addNewNoteHelper.statusCodeValue,
                                userId = userId
                            )

                            val addedId = database.noteDao().insertNote( newNote )

                            if ( database.noteDao().findNotes().last().id == addedId ) {
                                addNewNoteHelper.setNameValue( Optional.ofNullable("") )
                                addNewNoteHelper.setDescriptionValue( Optional.ofNullable("") )
                                addNewNoteHelper.setDateOfWrite( Optional.ofNullable(LocalDate.now()) )
                                addNewNoteHelper.setStatusCodeValue( Optional.ofNullable(0) )

                                nameTextFieldValue.value = TextFieldValue( addNewNoteHelper.nameValue )
                                descriptionTextFieldValue.value = TextFieldValue( addNewNoteHelper.descriptionValue )
                                statusCodeMutable.value = addNewNoteHelper.statusCodeValue
                                selectedDateOfWrite.value = addNewNoteHelper.dateOfWrite

                                // nameTextFieldValue.value = TextFieldValue("Successfully")
                            } else {
                                nameTextFieldValue.value = TextFieldValue("Failed")
                                descriptionTextFieldValue.value = TextFieldValue(addedId.toString())
                            }
                        }
                    },

                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            (oneBlockHeight * 0.5f)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colorStops = arrayOf(
                                        0.6f to colorResource(R.color.primary_blue),
                                        1f to colorResource(R.color.secondary_cyan)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        ScrollableAnimatedText(
                            text = stringResource(R.string.add),
                            textColor = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = font_size_main_text,
                            fontWeight = FontWeight.Bold,
                            lineHeight = line_height_main_text,
                            containterModifier = Modifier
                                .fillMaxWidth(0.9f),
                            textModifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // Button to Back.
                Button(
                    onClick = {
                        current_page = "general_app"
                        navHostController.navigate(current_page) {
                            popUpTo("add_new_note") { inclusive = true }
                        }
                    },

                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            (oneBlockHeight * 0.5f)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colorStops = arrayOf(
                                        0.6f to colorResource(R.color.primary_blue),
                                        1f to colorResource(R.color.secondary_cyan)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        ScrollableAnimatedText(
                            text = stringResource(R.string.back),
                            textColor = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = font_size_main_text,
                            fontWeight = FontWeight.Bold,
                            lineHeight = line_height_main_text,
                            containterModifier = Modifier
                                .fillMaxWidth(0.9f),
                            textModifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}