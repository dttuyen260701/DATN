package com.example.realestateapp.ui.base

import androidx.compose.runtime.Composable
import com.example.realestateapp.designsystem.components.DialogChoiceData
import com.example.realestateapp.designsystem.components.DialogConfirm
import com.example.realestateapp.designsystem.components.DialogMessage

/**
 * Created by tuyen.dang on 5/10/2023.
 */

@Composable
internal fun BaseDialog(
    dialog: TypeDialog,
    onDismissDialog: () -> Unit
) {
    when (dialog) {
        is TypeDialog.ErrorDialog -> {
            DialogMessage(
                message = dialog.message,
                onDismissDialog = onDismissDialog
            )
        }
        is TypeDialog.MessageDialog -> {
            DialogMessage(
                title = dialog.title,
                message = dialog.message,
                btnText = dialog.btnText,
                onDismissDialog = onDismissDialog
            )
        }
        is TypeDialog.ConfirmDialog -> {
            DialogConfirm(
                title = dialog.title,
                message = dialog.message,
                negativeBtnText = dialog.negativeBtnText,
                onBtnNegativeClick = dialog.onBtnNegativeClick,
                positiveBtnText = dialog.positiveBtnText,
                onBtnPositiveClick = dialog.onBtnPositiveClick,
                onDismissDialog = onDismissDialog
            )
        }
        is TypeDialog.ChoiceDataDialog -> {
            DialogChoiceData(
                onDismissDialog = onDismissDialog,
                isLoading = dialog.isLoading,
                title = dialog.title,
                onItemClick = dialog.onItemClick,
                isEnableSearchFromApi = dialog.isEnableSearchFromApi,
                loadData = dialog.loadData,
                data = dialog.data
            )
        }
        else -> {}
    }
}
