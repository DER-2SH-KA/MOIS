package ru.der2shka.cursovedcote.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.der2shka.cursovedcote.R
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.font_size_secondary_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_secondary_text


/**
 *  Horizontal Item for General Page.
 *  For example: Homeworks, Notes and Marks.
 * **/
@SuppressLint("ResourceAsColor")
@Composable
fun HorizontalGeneralPageItem(
    headerText: String,
    onItemClick: () -> Unit,
    onPlusClick: @Composable () -> Unit,
    onDotsClick: () -> Unit
) {
    val config = LocalConfiguration.current

    val buttonSize = remember { mutableStateOf(0.dp) }
    val cardSize = remember { mutableStateOf(0.dp) }

    // Calculate box's height for buttons size.
    SubcomposeLayout(
    ) { constraints ->
        val boxHeight = subcompose("aboba") {
            Box(
                modifier = Modifier
                    .height(
                        (config.screenHeightDp * 0.1f).dp
                    )
                    .fillMaxWidth(0.9f)

            )
        }[0].measure(constraints)

        buttonSize.value = (boxHeight.height.toDp() * 0.4f)
        cardSize.value = boxHeight.height.toDp()

        layout(0, 0) {  }
    }

    // Main content.
    Box(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .height(
                    (config.screenHeightDp * 0.11f).dp
                )
                .fillMaxWidth(0.9f)
                .background(
                    brush = Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.6f to colorResource(R.color.primary_blue),
                            1f to colorResource(R.color.secondary_cyan)
                        )
                    ),
                    shape = RoundedCornerShape(20.0f)
                )
                .onSizeChanged {
                    // buttonSize.value = (it.height * 0.5f).dp
                    // cardSize.value = (it.height).dp
                }
                .clickable {
                    onItemClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(0.9f),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Header Text.
                Text(
                    text = headerText,
                    color = colorResource(R.color.background_color),
                    textAlign = TextAlign.Start,
                    fontSize = font_size_main_text,
                    lineHeight = line_height_main_text,
                    fontWeight = FontWeight.Bold
                )

                // Buttons.
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(10.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.End
                ) {
                    // Button with plus.
                    Button(
                        modifier = Modifier
                            .height(buttonSize.value)
                            .aspectRatio(1f),
                        onClick = {
                            onPlusClick
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent,
                            Color.Transparent,
                            Color.Transparent,
                            Color.Transparent
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = colorResource(R.color.secondary_cyan)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+",
                                color = colorResource(R.color.background_color),
                                textAlign = TextAlign.Center,
                                fontSize = font_size_secondary_text,
                                lineHeight = line_height_secondary_text,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Space between buttons.
                    Spacer(Modifier.widthIn(10.dp))

                    // Button with dots.
                    Button(
                        modifier = Modifier
                            .height(buttonSize.value)
                            .aspectRatio(1f),
                        onClick = {
                            onDotsClick()
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent,
                            Color.Transparent,
                            Color.Transparent,
                            Color.Transparent
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = colorResource(R.color.secondary_cyan)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = ". . .",
                                color = colorResource(R.color.background_color),
                                textAlign = TextAlign.Center,
                                fontSize = font_size_secondary_text,
                                lineHeight = line_height_secondary_text,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                /*Text(
                text = buttonSize.value.toString()
            )

            Text(
                text = cardSize.value.toString()
            )*/
            }
        }
    }
}