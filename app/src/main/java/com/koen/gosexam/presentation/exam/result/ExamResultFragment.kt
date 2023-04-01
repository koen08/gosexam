package com.koen.gosexam.presentation.exam.result

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentExamBinding
import com.koen.gosexam.databinding.FragmentExamResultBinding
import com.koen.gosexam.presentation.base.BaseFragment
import com.koen.gosexam.presentation.exam.ExamTestUiState
import com.koen.gosexam.presentation.exam.ExamViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExamResultFragment :
    BaseFragment<ExamResultUiState, ExamResultViewModel, FragmentExamResultBinding>(R.layout.fragment_exam_result) {

    companion object {
        const val ARG_KEY_RESULT_UI = "ARG_KEY_RESULT_UI"
    }

    override val viewModel: ExamResultViewModel by viewModels()

    override fun initViewBinding(inflater: LayoutInflater): FragmentExamResultBinding = FragmentExamResultBinding.inflate(inflater)


}