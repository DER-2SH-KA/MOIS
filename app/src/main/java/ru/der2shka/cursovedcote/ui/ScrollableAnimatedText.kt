package ru.der2shka.cursovedcote.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import org.w3c.dom.Text
import kotlin.math.max

@SuppressLint("ResourceAsColor")
@Composable
fun ScrollableAnimatedText(
    text: String?,
    textColor: Color,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = 1,
    fontSize: TextUnit,
    lineHeight: TextUnit,
    fontWeight: FontWeight = FontWeight.Normal,
    duration: Int = 2500,
    delay: Int = 500,
    containterModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier
) {
    val isOverflow = remember { mutableStateOf(false) }
    val fullTextWidth = remember { mutableStateOf( 0 ) }
    val containerWidth = remember { mutableStateOf( 0 ) }

    val offsetMultiplier = (fullTextWidth.value / 2f - containerWidth.value).coerceAtLeast(0f)

    SubcomposeLayout(
        modifier = containterModifier
    ) { constraints ->
        val subcomposedFullText = subcompose("text_full_length") {
            Text(
                text = StringBuilder().append(text).toString(),
                color = textColor,
                textAlign = textAlign,
                fontSize = fontSize,
                lineHeight = lineHeight,
                fontWeight = fontWeight
            )
        }[0].measure(Constraints())

        val subcomposedTextInContainter = subcompose("") {
            Text(
                text = StringBuilder().append(text).toString(),
                color = textColor,
                textAlign = textAlign,
                fontSize = fontSize,
                lineHeight = lineHeight,
                fontWeight = fontWeight,
                modifier = textModifier
            )
        }[0].measure(constraints)

        fullTextWidth.value = subcomposedFullText.width
        isOverflow.value = fullTextWidth.value > subcomposedTextInContainter.width

        layout(0, 0) {}
    }

    val infiniteTransition = rememberInfiniteTransition()
    val offsetXvalue = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -1f,
        animationSpec = infiniteRepeatable<Float>(
            animation = tween(
                durationMillis = duration,
                delayMillis = delay,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = containterModifier
            .clipToBounds()
            .background(Color.Magenta)
            .onSizeChanged {
                containerWidth.value = it.width
            },
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = StringBuilder().append(text).toString(),
            color = textColor,
            textAlign = textAlign,
            maxLines = maxLines,
            fontSize = fontSize,
            lineHeight = lineHeight,
            fontWeight = fontWeight,
            modifier = Modifier
                .width(fullTextWidth.value.dp)
                .graphicsLayer {
                    translationX = offsetXvalue.value * offsetMultiplier
                }
        )
    }

    /*
    // If text is short.
    if (!isOverflow.value) {
        Box(
            modifier = containterModifier
                .clipToBounds()
                .background(Color.Magenta)
                .onSizeChanged {
                    containerWidth.value = it.width
                }
        ) {
            Text(
                text = StringBuilder().append(text).toString(),
                color = textColor,
                textAlign = textAlign,
                maxLines = maxLines,
                fontSize = fontSize,
                lineHeight = lineHeight,
                fontWeight = fontWeight,
                modifier = textModifier
            )
        }
    }
    // If text if long.
    else {
        val infiniteTransition = rememberInfiniteTransition()
        val offsetXvalue = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = -1f,
            animationSpec = infiniteRepeatable<Float>(
                animation = tween(
                    durationMillis = duration,
                    delayMillis = delay,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )

        Box(
            modifier = containterModifier
                .clipToBounds()
                .background(Color.Magenta)
                .onSizeChanged {
                    containerWidth.value = it.width
                }
        ) {
            BasicText(
                text = StringBuilder().append(text).toString(),
                //color = textColor,
                //textAlign = textAlign,
                maxLines = maxLines,
                //fontSize = fontSize,
                //lineHeight = lineHeight,
                //fontWeight = fontWeight,
                modifier = Modifier
                    .graphicsLayer {
                        translationX = offsetXvalue.value * (fullTextWidth.value / 4)
                    }
            )
        }
    }*/
}