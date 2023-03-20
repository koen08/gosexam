package com.koen.gosexam.presentation.exam

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.koen.gosexam.databinding.ItemAnswerExamTestBinding
import com.koen.gosexam.databinding.ItemQuestionAnswersBinding
import com.koen.gosexam.presentation.models.AnswerTestUi
import com.koen.gosexam.presentation.models.ExamUi

fun examAdapterDelegate(onClickExamListener: (ExamUi, AnswerTestUi) -> Unit) =
    adapterDelegateViewBinding<ExamUi, ExamUi, ItemQuestionAnswersBinding>(
        { layoutInflater, root -> ItemQuestionAnswersBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            val adapterAnswer = AnswerTestAdapter { answerTestUi ->
                onClickExamListener(item, answerTestUi)
            }
            binding.run {
                rvAnswers.run {
                    adapter = adapterAnswer
                    layoutManager = LinearLayoutManager(context)
                }
                tvQuestionTitle.text = item.question
                adapterAnswer.items = item.answers
            }
        }
    }

fun examAnswerAdapterDelegate(onClickAnswerListener: (AnswerTestUi) -> Unit) =
    adapterDelegateViewBinding<AnswerTestUi, AnswerTestUi, ItemAnswerExamTestBinding>(
        { layoutInflater, root -> ItemAnswerExamTestBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.run {
                tvAnswer.text = item.text
                container.background = ContextCompat.getDrawable(context, item.backgroundSelected)
                root.setOnClickListener {
                    onClickAnswerListener(item)
                }
            }
        }
    }
