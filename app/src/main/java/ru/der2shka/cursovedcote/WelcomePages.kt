package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@SuppressLint("ResourceAsColor")
@Composable
fun WelcomePagesPage(
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .background(
                    colorResource(R.color.background_color)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val pagerState = rememberPagerState(pageCount = { 3 })

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

            Button(
                modifier = Modifier
                    .fillMaxSize(0.8f)
                    .weight(1f),
                onClick = {
                    current_page = "colors_test_page"
                    navController.navigate(current_page)
                }
            ) {
                Text(
                    text = "Кнопка"
                )
            }
        }
    }
}