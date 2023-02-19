package com.koen.gosexam.presentation.dialog.info

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.koen.gosexam.R
import com.koen.gosexam.databinding.DialogInfoBinding
import com.koen.gosexam.presentation.core.Text

class InfoDialog(context: Context) : BottomSheetDialog(context, R.style.BottomSheetDialogTheme) {
    private val binding = DialogInfoBinding.inflate(layoutInflater)

    companion object {
        fun create(context: Context, infoDialogModel: InfoDialogModel) = InfoDialog(context).apply {
            setTitle(infoDialogModel.title)
            setContent(infoDialogModel.text)
            setButton(infoDialogModel.infoDialogBtnModel)
        }
    }

    init {
        setContentView(binding.root)
    }

    fun setTitle(title: Text) {
        binding.tvTitle.text = title.getText(context)
    }

    fun setContent(content: Text) {
        binding.tvContent.text = content.getText(context)
    }

    fun setButton(infoDialogBtnModel: InfoDialogBtnModel) {
        binding.btnAccept.text = infoDialogBtnModel.textBtn.getText(context)
        binding.btnAccept.setOnClickListener {
            infoDialogBtnModel.actionDismissOnClick(this)
        }
    }
}