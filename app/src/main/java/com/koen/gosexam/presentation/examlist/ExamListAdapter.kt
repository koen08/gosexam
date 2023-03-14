package com.koen.gosexam.presentation.examlist

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.koen.gosexam.presentation.models.QuestionUi

class ExamListAdapter(onClickListener: (QuestionUi) -> Unit) : AsyncListDifferDelegationAdapter<QuestionUi>(
    DiffUtils(),
    examListAdapterDelegate(onClickListener)
) {
    class DiffUtils : DiffUtil.ItemCallback<QuestionUi>() {
        override fun areItemsTheSame(
            oldItem: QuestionUi,
            newItem: QuestionUi
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: QuestionUi,
            newItem: QuestionUi
        ): Boolean {
            return oldItem == newItem
        }
    }
}