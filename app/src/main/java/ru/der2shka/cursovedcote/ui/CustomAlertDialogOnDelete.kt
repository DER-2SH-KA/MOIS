package ru.der2shka.cursovedcote.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import ru.der2shka.cursovedcote.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAlertDialogOnDelete(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    openDialog: MutableState<Boolean>,
    isConfirmAction: MutableState<Boolean>
)  {
    if (openDialog.value) {
        BasicAlertDialog(
            onDismissRequest = {
                onDismissRequest()

                isConfirmAction.value = false

                // Close basic alert dialog.
                openDialog.value = false
           }
        ) {
            Surface(
                color = colorResource(R.color.primary_blue),
                shape = RoundedCornerShape(10.dp),
                tonalElevation = AlertDialogDefaults.TonalElevation,
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.warning_delete_text)
                    )
                    Spacer( modifier = Modifier.height(24.dp) )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(
                            onClick = {

                                isConfirmAction.value = true
                                openDialog.value = false
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.ok)
                            )
                        }

                        TextButton(
                            onClick = {
                                onDismissRequest()

                                isConfirmAction.value = false
                                openDialog.value = false
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.cancel)
                            )
                        }
                    }
                }
            }
        }
    }
}