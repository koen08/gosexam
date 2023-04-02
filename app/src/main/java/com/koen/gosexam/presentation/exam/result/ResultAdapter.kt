package com.koen.gosexam.presentation.exam.result

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.koen.gosexam.presentation.exam.examAdapterDelegate
import com.koen.gosexam.presentation.models.ResultExamUi

class ResultAdapter() : AsyncListDifferDelegationAdapter<ResultExamUi>(
    DiffCallback(),
    resultAdapterDelegate()
) {
    class DiffCallback : DiffUtil.ItemCallback<ResultExamUi>() {
        override fun areItemsTheSame(
            oldItem: ResultExamUi,
            newItem: ResultExamUi
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ResultExamUi,
            newItem: ResultExamUi
        ): Boolean {
            return oldItem == newItem
        }
    }
}