package com.koen.gosexam.presentation.exam

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.koen.gosexam.presentation.models.AnswerTestUi
import com.koen.gosexam.presentation.models.ExamUi

class AnswerAdapter(onClickAnswerListener: (AnswerTestUi) -> Unit) : AsyncListDifferDelegationAdapter<AnswerTestUi>(
    DiffCallback(),
    examAnswerAdapterDelegate(onClickAnswerListener)
) {
    class DiffCallback : DiffUtil.ItemCallback<AnswerTestUi>() {
        override fun areItemsTheSame(
            oldItem: AnswerTestUi,
            newItem: AnswerTestUi
        ): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(
            oldItem: AnswerTestUi,
            newItem: AnswerTestUi
        ): Boolean {
            return oldItem == newItem
        }
    }
}