package com.koen.gosexam.presentation.dialog.info

import com.koen.gosexam.presentation.core.Text

data class InfoDialogBtnModel(
    val textBtn: Text,
    val actionDismissOnClick : (InfoDialog) -> Unit = { it.dismiss() }
)