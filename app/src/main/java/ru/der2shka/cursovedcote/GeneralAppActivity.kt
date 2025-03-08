package ru.der2shka.cursovedcote

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.der2shka.cursovedcote.Service.SettingsDataStore
import ru.der2shka.cursovedcote.db.entity.User
import ru.der2shka.cursovedcote.db.helper.AppDatabase
import ru.der2shka.cursovedcote.ui.BottomMenu
import ru.der2shka.cursovedcote.ui.theme.CursovedCotETheme

var userId: Long = 0

/**
 * General Activity for App with actions.
 * **/
class GeneralAppActivity : ComponentActivity() {

    private lateinit var settingsDataStore: SettingsDataStore

    @RequiresApi(35)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        current_page = "general_app"

        settingsDataStore = SettingsDataStore(applicationContext)

        val database: AppDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app-database"
        ).build()

        // Fix portait orientation for activity.
        this?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            CursovedCotETheme {
                val navHostController = rememberNavController()
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(key1 = Unit) {
                    coroutineScope.launch(Dispatchers.IO) {
                        if ( database.userDao().findUsers().isEmpty() ) {
                            database.userDao().insertUser(User())
                        }

                        userId = database.userDao().findUsers().last().id
                    }
                }

                NavHost(
                    navController = navHostController,
                    startDestination = "general_app",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            colorResource(R.color.background_color)
                        )
                ) {
                    composable(route = "general_app") { GeneralAppActivityMainPage(navHostController, settingsDataStore, database) }

                    // Add pages.
                    composable(route = "add_new_grade") { AddNewMarkPage(navHostController, database) }
                    composable(route = "add_new_homework") { AddNewHomework(navHostController, database) }
                    composable(route = "add_new_note") { AddNewNote(navHostController, database) }
                    composable(route = "add_new_study_subject") { AddNewStudySubject(navHostController, database) }
                    composable(route = "add_new_grade_type") { AddNewMarkType(navHostController, database) }

                    // Edit pages.
                    composable(route = "edit_note") { EditNotePage(navHostController, database) }
                    composable(route = "edit_grade_type") { EditGradeType(navHostController, database) }
                    composable(route = "edit_study_subject") { EditStudySubject(navHostController, database) }
                    composable(route = "edit_homework") { EditHomework(navHostController, database) }

                    // View pages.
                    composable(route = "view_grade_type") { ViewListOfGradeTypes(navHostController, database) }
                    composable(route = "view_study_subject") { ViewListOfStudySubjects(navHostController, database) }
                }

            }
        }
    }
}

@Composable
fun GeneralAppActivityMainPage(
    navHostController: NavHostController,
    settingsDataStore: SettingsDataStore,
    database: AppDatabase
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
                        ViewListOfHomeworks(navHostController, database)
                    }

                    2 -> {
                        GeneralAppPage(navHostController, horizontalPager)
                    }

                    3 -> {
                        ViewListOfNotes(navHostController, database)
                    }

                    4 -> {
                        ProfileSettingsPage(navHostController, settingsDataStore)
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

