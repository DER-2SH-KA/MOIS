package ru.der2shka.cursovedcote.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.der2shka.cursovedcote.R

@Composable
fun BottomMenu(
    containerModifier: Modifier = Modifier,
    pager: PagerState
) {
    val coroutineScope = rememberCoroutineScope()

    Box(
        contentAlignment = Alignment.Center,
        modifier = containerModifier
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Add Grade.
            FilledIconButton(
                onClick = {
                    coroutineScope.launch {
                        pager.scrollToPage(0)
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    Color.Transparent,
                    Color.Transparent,
                    Color.Transparent,
                    Color.Transparent
                ),
                shape = RectangleShape,
                modifier = Modifier
                        .fillMaxHeight(0.8f)
                        .aspectRatio(1f)
                        .background(
                            color = Color.Transparent,
                            shape = RectangleShape
                        )
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_plus_square),
                    contentDescription = null,
                    tint = if (pager.currentPage == 0) colorResource(R.color.secondary_cyan) else Color.White,
                    modifier = Modifier
                        .aspectRatio(1f)
                        /*.background(
                            color = Color.Green,
                            shape = RectangleShape
                        )*/
                        .padding( if (pager.currentPage == 0) 5.dp else 7.dp )
                )
            }

            // Add HomeWork.
            FilledIconButton(
                onClick = {
                    coroutineScope.launch {
                        pager.scrollToPage(1)
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    Color.Transparent,
                    Color.Transparent,
                    Color.Transparent,
                    Color.Transparent
                ),
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .aspectRatio(1f)
                    .background(
                        color = Color.Transparent,
                        shape = RectangleShape
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.list_unordered),
                    contentDescription = null,
                    tint = if (pager.currentPage == 1) colorResource(R.color.secondary_cyan) else Color.White,
                    modifier = Modifier
                        .aspectRatio(1f)
                        /*.background(
                            color = Color.Green,
                            shape = RectangleShape
                        )*/
                        .padding( if (pager.currentPage == 1) 5.dp else 7.dp )
                )
            }

            // General Page.
            FilledIconButton(
                onClick = {
                    coroutineScope.launch {
                        pager.scrollToPage(2)
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    Color.Transparent,
                    Color.Transparent,
                    Color.Transparent,
                    Color.Transparent
                ),
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .aspectRatio(1f)
                    .background(
                        color = Color.Transparent,
                        shape = RectangleShape
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.house_01),
                    contentDescription = null,
                    tint = if (pager.currentPage == 2) colorResource(R.color.secondary_cyan) else Color.White,
                    modifier = Modifier
                        .aspectRatio(1f)
                        /*.background(
                            color = Color.Green,
                            shape = RectangleShape
                        )*/
                        .padding( if (pager.currentPage == 2) 7.dp else 10.dp )
                )
            }

            // Add Note.
            FilledIconButton(
                onClick = {
                    coroutineScope.launch {
                        pager.scrollToPage(3)
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    Color.Transparent,
                    Color.Transparent,
                    Color.Transparent,
                    Color.Transparent
                ),
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .aspectRatio(1f)
                    .background(
                        color = Color.Transparent,
                        shape = RectangleShape
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.chat_add),
                    contentDescription = null,
                    tint = if (pager.currentPage == 3) colorResource(R.color.secondary_cyan) else Color.White,
                    modifier = Modifier
                        .aspectRatio(1f)
                        /*.background(
                            color = Color.Green,
                            shape = RectangleShape
                        )*/
                        .padding( if (pager.currentPage == 3) 5.dp else 7.dp )
                )
            }

            // Profile.
            FilledIconButton(
                onClick = {
                    coroutineScope.launch {
                        pager.scrollToPage(4)
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    Color.Transparent,
                    Color.Transparent,
                    Color.Transparent,
                    Color.Transparent
                ),
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .aspectRatio(1f)
                    .background(
                        color = Color.Transparent,
                        shape = RectangleShape
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.user_02),
                    contentDescription = null,
                    tint = if (pager.currentPage == 4) colorResource(R.color.secondary_cyan) else Color.White,
                    modifier = Modifier
                        .aspectRatio(1f)
                        /*.background(
                            color = Color.Green,
                            shape = RectangleShape
                        )*/
                        .padding( if (pager.currentPage == 4) 5.dp else 7.dp )
                )
            }
        }
    }
}