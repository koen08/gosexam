package com.koen.gosexam.presentation.examlist

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.koen.gosexam.databinding.ItemQuestionBinding
import com.koen.gosexam.presentation.models.ExamUi

fun examListAdapterDelegate() = adapterDelegateViewBinding<ExamUi, ExamUi, ItemQuestionBinding>(
    { layoutInflater, root -> ItemQuestionBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        binding.tvQuestion.text = item.question
    }
}