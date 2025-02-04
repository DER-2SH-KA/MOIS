package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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
                .fillMaxSize(0.9f)
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
                    .fillMaxSize(0.9f)
                    .weight(5f)
            ) { page ->
                when (page) {
                    0 -> Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Color.Red
                            )
                    )

                    1 -> Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Color.Yellow
                            )
                    )

                    2 -> Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Color.Green
                            )
                    )
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