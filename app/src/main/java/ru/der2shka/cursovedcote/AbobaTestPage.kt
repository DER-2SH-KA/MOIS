package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text

@SuppressLint("ResourceAsColor")
@Composable
fun AbobaTestPage(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Aboba Test Page $text",
            color = colorResource(R.color.main_text_dark_gray),
            textAlign = TextAlign.Center,
            fontSize = font_size_main_text,
            lineHeight = line_height_main_text,
            fontWeight = FontWeight.Medium
        )
    }
}