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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import org.w3c.dom.Text
import kotlin.math.max
import kotlin.math.min

@SuppressLint("ResourceAsColor")
@Composable
fun ScrollableAnimatedText(
    text: String?,
    textModifier: Modifier = Modifier,
    textColor: Color,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Visible,
    softWrap: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ( (TextLayoutResult) -> Unit )? = null,
    style: TextStyle = LocalTextStyle.current,
    duration: Int = 0,
    delay: Int = 500,
    containterModifier: Modifier = Modifier,
) {
    val isOverflow = remember { mutableStateOf(false) }
    val fullTextWidth = remember { mutableStateOf( 0 ) }
    val containerWidth = remember { mutableStateOf( 0 ) }

    val offsetMultiplier = (fullTextWidth.value - containerWidth.value).coerceAtLeast(0)

    /*val textMeasurer = rememberTextMeasurer()
    val measuredText = textMeasurer.measure(
        text = StringBuilder().append(text).toString(),
        maxLines = maxLines,
        overflow = overflow,
        softWrap = softWrap
    )*/

    SubcomposeLayout(
        modifier = containterModifier
    ) { constraints ->
        // Not cliped text.
        val subcomposedFullText = subcompose("text_full_length") {
            Text(
                text = StringBuilder().append(text).toString(),
                color = textColor,
                fontSize = fontSize,
                fontStyle = fontStyle,
                fontWeight = fontWeight,
                fontFamily = fontFamily,
                letterSpacing = letterSpacing,
                textDecoration = textDecoration,
                textAlign = textAlign,
                lineHeight = lineHeight,
                overflow = overflow,
                softWrap = softWrap,
                maxLines = maxLines,
                minLines = minLines,
                onTextLayout = onTextLayout,
                style = style
            )
        }[0].measure(Constraints())

        // Cliped text.
        val subcomposedTextInContainter = subcompose("") {
            Text(
                text = StringBuilder().append(text).toString(),
                color = textColor,
                fontSize = fontSize,
                fontStyle = fontStyle,
                fontWeight = fontWeight,
                fontFamily = fontFamily,
                letterSpacing = letterSpacing,
                textDecoration = textDecoration,
                textAlign = textAlign,
                lineHeight = lineHeight,
                overflow = TextOverflow.Clip,
                softWrap = softWrap,
                maxLines = maxLines,
                minLines = minLines,
                onTextLayout = onTextLayout,
                style = style
            )
        }[0].measure(constraints)

        fullTextWidth.value = subcomposedFullText.width
        isOverflow.value = fullTextWidth.value > subcomposedTextInContainter.width

        layout(0, 0) {}
    }

    // If text is short.
    if (!isOverflow.value) {
        Box(
            modifier = containterModifier
                .clipToBounds()
                //.background(Color.Magenta)
                .onSizeChanged {
                    containerWidth.value = it.width
                    isOverflow.value = fullTextWidth.value > it.width
                },
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = StringBuilder().append(text).toString(),
                color = textColor,
                fontSize = fontSize,
                fontStyle = fontStyle,
                fontWeight = fontWeight,
                fontFamily = fontFamily,
                letterSpacing = letterSpacing,
                textDecoration = textDecoration,
                textAlign = textAlign,
                lineHeight = lineHeight,
                overflow = overflow,
                softWrap = softWrap,
                maxLines = maxLines,
                minLines = minLines,
                onTextLayout = onTextLayout,
                style = style,
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
                    durationMillis = if (duration < 1) text!!.length * 150 else duration,
                    delayMillis = delay,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )

        // Scrollable Text.
        Box(
            modifier = containterModifier
                .fillMaxWidth()
                .clipToBounds()
                // .background(Color.Magenta)
                .onSizeChanged {
                    containerWidth.value = it.width
                    isOverflow.value = fullTextWidth.value > it.width
                },
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = StringBuilder().append(text).toString(),
                color = textColor,
                fontSize = fontSize,
                fontStyle = fontStyle,
                fontWeight = fontWeight,
                fontFamily = fontFamily,
                letterSpacing = letterSpacing,
                textDecoration = textDecoration,
                textAlign = textAlign,
                lineHeight = lineHeight,
                overflow = overflow,
                softWrap = softWrap,
                maxLines = maxLines,
                minLines = minLines,
                onTextLayout = onTextLayout,
                style = style,
                modifier = textModifier
                    .offset {
                        IntOffset( (offsetXvalue.value * offsetMultiplier).toInt(), 0 )
                    }
            )
        }
    }

    /*val infiniteTransition = rememberInfiniteTransition()
    val offsetXvalue = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -1f,
        animationSpec = infiniteRepeatable<Float>(
            animation = tween(
                durationMillis = text!!.length * 150,
                delayMillis = delay,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )*/

    /*
    Column() {
        Box(
            modifier = containterModifier
                .clipToBounds()
                .background(Color.Magenta)
                .onSizeChanged {
                    containerWidth.value = it.width
                    isOverflow.value = fullTextWidth.value > it.width
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
                overflow = overflow,
                softWrap = softWrap,
                modifier = Modifier
                    /*.graphicsLayer {
                        translationX = offsetXvalue.value * offsetMultiplier
                    }*/
                    .offset {
                        IntOffset( (offsetXvalue.value * offsetMultiplier).toInt(), 0 )
                    }
            )
        }
        Text(
            text = fullTextWidth.value.toString()
        )
        /*Text(
            text = measuredText.size.width.toString()
        )*/
        Text(
            text = containerWidth.value.toString()
        )
    }*/
}