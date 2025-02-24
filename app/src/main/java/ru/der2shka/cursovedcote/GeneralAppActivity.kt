package ru.der2shka.cursovedcote

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.der2shka.cursovedcote.ui.BottomMenu
import ru.der2shka.cursovedcote.ui.theme.CursovedCotETheme
import ru.der2shka.cursovedcote.ui.theme.font_size_main_text
import ru.der2shka.cursovedcote.ui.theme.line_height_main_text

/**
 * General Activity for App with actions.
 * **/
class GeneralAppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        current_page = "general_app"

        // Fix portait orientation for activity.
        this?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            CursovedCotETheme {
                val navHostController = rememberNavController()

                NavHost(
                    navController = navHostController,
                    startDestination = "general_app",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            colorResource(R.color.background_color)
                        )
                ) {
                    composable(route = "add_new_grade") { AddNewMarkPage(navHostController) }
                    composable(route = "add_new_homework") { AbobaNavHostTest(navHostController, "homework") }
                    composable(route = "general_app") { GeneralAppActivityMainPage(navHostController) }
                    composable(route = "add_new_note") { AddNewNote(navHostController) }
                    composable(route = "add_new_study_subject") { AddNewStudySubject(navHostController) }
                }

            }
        }
    }
}

@Composable
fun GeneralAppActivityMainPage(
    navHostController: NavHostController
) {
    val horizontalPager = rememberPagerState(initialPage = 2, pageCount = { 5 })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(R.color.background_color)
            )
        ,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.9f)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(9f)
                    .background(Color.Yellow),
                state = horizontalPager
            ) { page ->
                when (page) {
                    0 -> {
                        AbobaTestPage("0")
                    }

                    1 -> {
                        AbobaTestPage("1")
                    }

                    2 -> {
                        GeneralAppPage(navHostController, horizontalPager)
                    }

                    3 -> {
                        AbobaTestPage("3")
                    }

                    4 -> {
                        AbobaTestPage("4")
                    }
                }
            }

            // Bottom Menu.
            /*Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color.Red)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
            }*/
            BottomMenu(
                containerModifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(
                        color = colorResource(R.color.primary_blue)
                    ),
                pager = horizontalPager
            )
        }
    }
}

@Composable
fun AbobaNavHostTest(
    navHostController: NavHostController,
    text: String = ""
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Nav Host Aboba Test $text")
    }
}

