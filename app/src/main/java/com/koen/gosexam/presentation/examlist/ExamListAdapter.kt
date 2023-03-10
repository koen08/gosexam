package com.koen.gosexam.presentation.examlist

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.koen.gosexam.presentation.models.ExamUi

class ExamListAdapter(onClickListener: (ExamUi) -> Unit) : AsyncListDifferDelegationAdapter<ExamUi>(
    DiffUtils(),
    examListAdapterDelegate(onClickListener)
) {
    class DiffUtils : DiffUtil.ItemCallback<ExamUi>() {
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