package com.koen.gosexam.presentation.exam.result

import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.koen.gosexam.databinding.ItemResultExamBinding
import com.koen.gosexam.presentation.models.ResultExamUi

fun resultAdapterDelegate() =
    adapterDelegateViewBinding<ResultExamUi, ResultExamUi, ItemResultExamBinding>(
        { layoutInflater, root -> ItemResultExamBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.linearLayout.removeAllViews()
            item.resultAnswerList.forEach { answer ->
                val textView = TextView(context)
                textView.text = answer.text
                textView.setTextAppearance(answer.styleText)
                textView.setTextColor(answer.textColor)
                binding.linearLayout.addView(textView)
            }
            binding.tvQuestion.text = item.question
        }
    }
