package com.koen.gosexam.presentation.exam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentExamBinding
import com.koen.gosexam.extension.applyStatusBarInsetsOnly
import com.koen.gosexam.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExamTestFragment :
    BaseFragment<ExamTestUiState, ExamViewModel, FragmentExamBinding>(R.layout.fragment_exam) {

    companion object {
        const val KEY_ARG_EXAM_TEST_UI = "KEY_ARG_EXAM_TEST_UI"
    }

    override val viewModel: ExamViewModel by viewModels()

    private val adapterExam by lazy {
        ExamTestAdapter { exam, answer ->
            viewModel.updateAnswerList(answer, exam)
        }
    }

    override fun initViewBinding(inflater: LayoutInflater): FragmentExamBinding {
        return FragmentExamBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            applyStatusBarInsetsOnly(root)
            vpExam.apply {
                adapter = adapterExam
                isUserInputEnabled = false
            }
        }
    }

    override fun handleUiState(uiState: ExamTestUiState) {
        super.handleUiState(uiState)
        adapterExam.items = uiState.examUiList
    }

}