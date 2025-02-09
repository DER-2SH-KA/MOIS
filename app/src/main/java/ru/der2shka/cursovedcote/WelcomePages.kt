package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text
import kotlin.coroutines.coroutineContext

@SuppressLint("ResourceAsColor")
@Composable
fun WelcomePagesPage(
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(R.color.background_color)
            )
        ,
        contentAlignment = Alignment.Center
    ) {
        val pagerState = rememberPagerState(pageCount = { 3 })

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .background(
                    colorResource(R.color.background_color)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(5f)
            ) { page ->
                when (page) {
                    0 -> WelcomePhraseFirst()
                    1 -> WelcomePhraseSecond()
                    2 -> WelcomePhraseThird()
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .weight(1f)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                for (page in 0..pagerState.pageCount - 1) {
                    if (page == pagerState.currentPage) {
                        Box(
                            modifier = Modifier
                                .height(20.dp)
                                .width(20.dp)
                                .background(
                                    color = colorResource(R.color.primary_blue),
                                    shape = CircleShape
                                )
                        )
                    }
                    else {
                        Box(
                            modifier = Modifier
                                .height(20.dp)
                                .width(20.dp)
                                .background(
                                    color = colorResource(id = R.color.secondary_text_gray),
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }

            if (pagerState.currentPage == pagerState.pageCount - 1) {


                Button(
                    modifier = Modifier
                        .weight(0.8f)
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth(0.8f)
                    ,
                    onClick = {
                        current_page = "colors_test_page"
                        navController.navigate(current_page) {
                            popUpTo("welcome_pages_page") {
                                inclusive = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        colorResource(R.color.primary_blue),
                                        colorResource(R.color.secondary_cyan)
                                    )
                                )
                            )
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.finish),
                            maxLines = 1,
                            color = colorResource(R.color.background_color),
                            textAlign = TextAlign.Center,
                            fontSize = font_size_main_text,
                            lineHeight = line_height_main_text,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            else {
                val coroutineScope = rememberCoroutineScope()

                Button(
                    modifier = Modifier
                        .weight(0.8f)
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth(0.8f)
                    ,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(
                                page = pagerState.pageCount - 1
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        colorResource(R.color.primary_blue),
                                        colorResource(R.color.secondary_cyan)
                                    )
                                )
                            )
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.skip),
                            maxLines = 1,
                            color = colorResource(R.color.background_color),
                            textAlign = TextAlign.Center,
                            fontSize = font_size_main_text,
                            lineHeight = line_height_main_text,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}