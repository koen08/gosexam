package com.koen.gosexam.presentation.exam

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.koen.gosexam.presentation.models.AnswerTestUi
import com.koen.gosexam.presentation.models.ExamUi

class ExamAdapter(onClickExamListener: (ExamUi, AnswerTestUi) -> Unit) : AsyncListDifferDelegationAdapter<ExamUi>(
    DiffCallback(),
    examAdapterDelegate(onClickExamListener)
) {
    class DiffCallback : DiffUtil.ItemCallback<ExamUi>() {
        override fun areItemsTheSame(
            oldItem: ExamUi,
            newItem: ExamUi
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ExamUi,
            newItem: ExamUi
        ): Boolean {
            return oldItem == newItem
        }
    }
}