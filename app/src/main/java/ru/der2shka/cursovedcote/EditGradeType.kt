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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.der2shka.cursovedcote.Models.AddNewMarkHelper
import ru.der2shka.cursovedcote.Models.AddNewMarkTypeHelper
import ru.der2shka.cursovedcote.Models.AddNewNoteHelper
import ru.der2shka.cursovedcote.Models.AddNewStudySubjectHelper
import ru.der2shka.cursovedcote.Models.GradeTypeHelper
import ru.der2shka.cursovedcote.Models.NoteHelper
import ru.der2shka.cursovedcote.Models.StudySubjectHelper
import ru.der2shka.cursovedcote.Service.ClearTextField
import ru.der2shka.cursovedcote.Service.GetMonthStringResourceByLocalDate
import ru.der2shka.cursovedcote.Service.checkMultiplierTextFieldValue
import ru.der2shka.cursovedcote.db.entity.GradeType
import ru.der2shka.cursovedcote.db.entity.Note
import ru.der2shka.cursovedcote.db.entity.StudySubject
import ru.der2shka.cursovedcote.db.helper.AppDatabase
import ru.der2shka.cursovedcote.ui.ComboBoxPseudo
import ru.der2shka.cursovedcote.ui.DatePickerBox
import ru.der2shka.cursovedcote.ui.ScrollableAnimatedText
import ru.der2shka.cursovedcote.ui.TextFieldCustom
import ru.der2shka.cursovedcote.ui.theme.VeryLightGrayMostlyWhite
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Optional

/**
 * Page for editing grade type in system.
 * **/
@SuppressLint("ResourceAsColor", "UnrememberedMutableState")
@Composable
fun EditGradeType(
    navHostController: NavHostController,
    database: AppDatabase
) {
    val config = LocalConfiguration.current
    val oneBlockHeight = (config.screenHeightDp * 0.2).dp
    val verticalMainScroll = rememberScrollState(0)
    val coroutineScope = rememberCoroutineScope()

    val addNewMarkTypeHelper = AddNewMarkTypeHelper.getInstance()
    val gradeTypeHelper: GradeTypeHelper = GradeTypeHelper.getInstance()
    val gradeTypeFromHelpert = remember { mutableStateOf( gradeTypeHelper.gradeTypeValue ) }

    // TODO: Create Singleton for it.
    // val addNewMarkHelper: AddNewMarkHelper = AddNewMarkHelper.getInstance()

    val nameTextField = remember {
        mutableStateOf(
            TextFieldValue(
                gradeTypeFromHelpert.value.name
            )
        )
    }

    val multiplierTextField = remember {
        mutableStateOf(
            TextFieldValue(
                gradeTypeFromHelpert.value.mulltiplier.toString()
            )
        )
    }

    val isDeleted = remember { mutableStateOf(false) }

    // Transaction status
    val transactionStatusString = remember { mutableStateOf("") }

    val succText = stringResource(R.string.successfully)
    val errText = stringResource(R.string.fail)
    var succColor = colorResource (R.color.successful_green)
    var errColor = colorResource (R.color.error_red)

    // Transaction status text.
    val transactionText = when( transactionStatusString.value ) {
        "s" -> succText
        "f" -> errText
        else -> transactionStatusString.value
    }

    // Transaction status color.
    val transactionColor = when( transactionStatusString.value ) {
        "s" -> succColor
        "f" -> errColor
        else -> Color.Black
    }

    var existsGradeTypeList = remember { mutableStateOf(listOf<GradeType>()) }

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            existsGradeTypeList.value = database.gradeTypeDao().findGradeTypes()
        }
    }

    // Validation.
    var isNameValid = (nameTextField.value.text.trim() != "" &&
            existsGradeTypeList.value.stream().filter { x ->
                x.name.equals(nameTextField.value.text.trim()) && x.id != gradeTypeFromHelpert.value.id
            }.toArray().isEmpty())
    var isMultiplierValid = checkMultiplierTextFieldValue( multiplierTextField )
    var isValid = isNameValid && isMultiplierValid

    Box(
        modifier = Modifier
            .fillMaxSize()
        //.verticalScroll( verticalMainScroll )
        ,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.85f)
                .fillMaxWidth(0.9f)
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
                    text = stringResource(R.string.edit_grade_type),
                    textColor = Color.White,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    fontSize = font_size_main_text,
                    lineHeight = line_height_main_text,
                    fontWeight = FontWeight.Bold,
                    containterModifier = Modifier.fillMaxWidth(0.9f)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .verticalScroll( verticalMainScroll )
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Name.
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
                                value = nameTextField.value.text,
                                onValueChange = {
                                    nameTextField.value = TextFieldValue(it)
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
                                                ClearTextField(nameTextField)
                                            }
                                    )
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text
                                ),
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth()
                                    .border(
                                        width = 2.dp,
                                        color = colorResource(R.color.primary_blue),
                                        shape = RoundedCornerShape(5.dp)
                                    )
                            )

                        }
                    }

                    // Multiplier.
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
                                text = "${stringResource(R.string.multiplier)}:",
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
                                value = multiplierTextField.value.text,
                                onValueChange = {
                                    multiplierTextField.value = TextFieldValue(it.trim())
                                },
                                shape = RoundedCornerShape(5.dp),
                                placeholder = {
                                    Text(
                                        text = "1",
                                        color = colorResource(R.color.secondary_text_gray),
                                        textAlign = TextAlign.Start,
                                        fontSize = font_size_secondary_text,
                                        fontStyle = FontStyle.Italic,
                                        lineHeight = line_height_secondary_text
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = null,
                                        tint = colorResource(R.color.primary_blue),
                                        modifier = Modifier
                                            .clickable {
                                                ClearTextField(multiplierTextField)
                                            }
                                    )
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                singleLine = true,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth()
                                    .border(
                                        width = 2.dp,
                                        color = colorResource(R.color.primary_blue),
                                        shape = RoundedCornerShape(5.dp)
                                    )
                            )

                        }
                    }

                    /*
                    // Only for testing.
                    Text(text = "Name: ${nameTextField.value.text}")
                    Text(text = "NameH: ${addNewMarkTypeHelper.nameValue}")
                    Text(text = "Multiplier: ${multiplierTextField.value.text}")
                    Text(text = "MultiplierH: ${addNewMarkTypeHelper.multiplierValue}")
                     */

                    // Transaction status.
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        ScrollableAnimatedText(
                            text = transactionText,
                            textColor = transactionColor,
                            textAlign = TextAlign.Center,
                            fontSize = font_size_main_text,
                            fontWeight = FontWeight.Medium,
                            lineHeight = line_height_main_text,
                            textModifier = Modifier
                                .fillMaxWidth()
                            ,
                            containterModifier = Modifier
                                .fillMaxWidth(0.9f)
                        )
                    }
                }
            }

            // Buttons.
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Update and delete buttons.
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Button to Update.
                    Button(
                        onClick = {
                            if (isValid) {
                                coroutineScope.launch(Dispatchers.IO) {
                                    // Add data into Helper object.
                                    addNewMarkTypeHelper.setNameValue(
                                        Optional.ofNullable(nameTextField.value.text.trim())
                                    )
                                    addNewMarkTypeHelper.setMultiplierValue(
                                        Optional.ofNullable(multiplierTextField.value.text.toInt())
                                    )

                                    var uGradeType = GradeType(
                                        id = gradeTypeFromHelpert.value.id,
                                        name = addNewMarkTypeHelper.nameValue,
                                        mulltiplier = addNewMarkTypeHelper.multiplierValue,
                                        userLocalId = userId
                                    )

                                    val updatedId =
                                        database.gradeTypeDao().updateGradeType(uGradeType)

                                    // If note was updated.
                                    if (database.gradeTypeDao()
                                            .findGradeTypeById(gradeTypeFromHelpert.value.id)
                                            .equals(uGradeType)
                                    ) {
                                        existsGradeTypeList.value = database.gradeTypeDao().findGradeTypes()

                                        // Show status of transaction.
                                        transactionStatusString.value = "s"
                                        delay(4000L)
                                        transactionStatusString.value = ""
                                    } else {
                                        // Show status of transaction.
                                        transactionStatusString.value = "f"
                                        delay(4000L)
                                        transactionStatusString.value = ""
                                    }
                                }
                            }
                            else {
                                coroutineScope.launch {
                                    transactionStatusString.value = "f"
                                    delay(4000L)
                                    transactionStatusString.value = ""
                                }
                            }
                        },

                        enabled = !isDeleted.value,
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .width( (config.screenWidthDp * 0.45f).dp )
                            .height( (oneBlockHeight * 0.5f) )
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
                                text = stringResource(R.string.update),
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
                        modifier = Modifier.width(10.dp)
                    )

                    // Button to Delete.
                    Button(
                        onClick = {
                            coroutineScope.launch(Dispatchers.IO) {
                                database.gradeTypeDao().deleteGradeType( gradeTypeFromHelpert.value )
                                isDeleted.value = true

                                addNewMarkTypeHelper.setNameValue(Optional.ofNullable(""))
                                addNewMarkTypeHelper.setMultiplierValue(Optional.ofNullable(1))

                                transactionStatusString.value = "s"
                                delay(4000L)
                                transactionStatusString.value = ""
                            }
                        },

                        enabled = !isDeleted.value,
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .width( (config.screenWidthDp * 0.45f).dp )
                            .height( (oneBlockHeight * 0.5f) )
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
                                text = stringResource(R.string.delete),
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

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // Button to Back.
                Button(
                    onClick = {
                        clearAddNewGradeTypeHelperValues(addNewMarkTypeHelper)

                        nameTextField.value = TextFieldValue( "" )
                        multiplierTextField.value = TextFieldValue("1")

                        current_page = "view_grade_type"
                        navHostController.navigate(current_page) {
                            popUpTo("edit_grade_type") { inclusive = true }
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
