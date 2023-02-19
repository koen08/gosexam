package com.koen.gosexam.presentation.examlist

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentExamListBinding
import com.koen.gosexam.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExamListFragment :
    BaseFragment<ExamListUiState, ExamListViewModel, FragmentExamListBinding>(R.layout.fragment_exam_list) {
    override val viewModel: ExamListViewModel by viewModels()

    override fun initViewBinding(inflater: LayoutInflater): FragmentExamListBinding =
        FragmentExamListBinding.inflate(inflater)
}