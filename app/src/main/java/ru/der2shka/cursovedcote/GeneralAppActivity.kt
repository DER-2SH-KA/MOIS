package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.der2shka.cursovedcote.ui.theme.CursovedCotETheme
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text

class GeneralAppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CursovedCotETheme {
                val navController = rememberNavController()
                val horizontalPager = rememberPagerState(pageCount = { 5 })

                NavHost(navController, startDestination = "general_page") {
                    composable(route = "aboba_test_page_1") {
                        AbobaTestPage()
                    }

                    composable(route = "aboba_test_page_2") {
                        AbobaTestPage()
                    }

                    composable(route = "general_page") {
                        GeneralAppPage(navController)
                    }

                    composable(route = "aboba_test_page_4") {
                        AbobaTestPage()
                    }

                    composable(route = "aboba_test_page_5") {
                        AbobaTestPage()
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f)
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    HorizontalPager(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                            .background(Color.Yellow)
                        ,
                        state = horizontalPager
                    ) { page ->
                        when(horizontalPager.currentPage) {
                            1 -> {
                                current_page = "aboba_test_page_1"
                                navController.navigate(current_page)
                            }
                            2 -> {
                                current_page = "aboba_test_page_2"
                                navController.navigate(current_page)
                            }
                            3 -> {
                                current_page = "general_page"
                                navController.navigate(current_page)
                            }
                            4 -> {
                                current_page = "aboba_test_page_4"
                                navController.navigate(current_page)
                            }
                            5 -> {
                                current_page = "aboba_test_page_5"
                                navController.navigate(current_page)
                            }

                        }
                    }
                    Text(
                        text = current_page
                    )
                    Text(
                        text = navController.currentDestination?.route.toString()
                    )
                    Text(
                        text = horizontalPager.currentPage.toString()
                    )
                }
            }
        }
    }
}

@SuppressLint("ResourceAsColor")
@Composable
fun AbobaTestPage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Aboba Test Page",
            color = colorResource(R.color.main_text_dark_gray),
            textAlign = TextAlign.Center,
            fontSize = font_size_main_text,
            lineHeight = line_height_main_text,
            fontWeight = FontWeight.Medium
        )
    }
}

