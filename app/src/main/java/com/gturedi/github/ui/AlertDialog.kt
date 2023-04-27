package com.gturedi.github.ui

import com.gturedi.github.R
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.gturedi.github.util.logI

@Composable
fun ShowErrorAlert(
    message: String,
    onDismiss: (() -> Unit)? = null,
) {

    var dialogOpen by remember { mutableStateOf(true) }

    if (dialogOpen) {
        AlertDialog(
            {
                logI("onDismiss")
                //not cancelable
                //dialogOpen = false
            },
            {
                TextButton(
                    onClick = {
                        logI("onConfirm")
                        dialogOpen = false
                        onDismiss?.invoke()
                    }) {
                    Text(stringResource(android.R.string.ok))
                }
            },
            //icon = { Icon(Icons.Filled.Warning, "")},
            title = {
                Text(stringResource(R.string.error))
            },
            text = {
                Text(message)
            },
        )
    }
}