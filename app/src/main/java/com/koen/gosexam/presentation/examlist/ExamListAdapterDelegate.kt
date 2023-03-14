package com.koen.gosexam.presentation.examlist

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.koen.gosexam.databinding.ItemQuestionBinding
import com.koen.gosexam.presentation.models.QuestionUi

fun examListAdapterDelegate(onClickListener: (QuestionUi) -> Unit) = adapterDelegateViewBinding<QuestionUi, QuestionUi, ItemQuestionBinding>(
    { layoutInflater, root -> ItemQuestionBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        binding.tvQuestion.text = item.question
        binding.tvNumberQuestion.text = item.numberQuestion
        binding.cardQuestion.setOnClickListener {
            onClickListener(item)
        }
    }
}