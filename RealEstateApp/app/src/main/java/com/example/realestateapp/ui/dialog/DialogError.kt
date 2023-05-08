package com.example.realestateapp.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.realestateapp.R
import com.example.realestateapp.designsystem.theme.RealStateTypography
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_DIFFERENT_VIEW
import com.example.realestateapp.util.Constants.DefaultValue.MARGIN_VIEW

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
internal fun ErrorDialog(
    modifier: Modifier = Modifier,
    messageError: String,
    onDismissListener: () -> Unit = {}
) {
    AlertDialog(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
        onDismissRequest = onDismissListener,
        title = {
            Text(
                text = stringResource(id = R.string.errorDialogTitle),
                style = RealStateTypography.button.copy(
                    fontSize = 16.sp,
                    color = Color.Black
                )
            )
        },
        text = {
            Text(
                text = messageError,
                style = RealStateTypography.button.copy(
                    color = Color.Black
                )
            )
        },
        buttons = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MARGIN_VIEW.dp, end = MARGIN_DIFFERENT_VIEW.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = stringResource(id = R.string.errorDialogBtn),
                    style = RealStateTypography.button.copy(
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(MARGIN_VIEW.dp)
                        .clickable {
                            onDismissListener()
                        },
                    textAlign = TextAlign.Center
                )
            }
        },
        properties = DialogProperties(),
        backgroundColor = Color.White
    )

}

@Preview
@Composable
private fun PreviewDialogError() {
    ErrorDialog(messageError = "Lỗi cuộc đời")
}
