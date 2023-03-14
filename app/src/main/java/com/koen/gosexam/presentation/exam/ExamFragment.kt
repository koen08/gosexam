package com.koen.gosexam.presentation.exam

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentExamBinding
import com.koen.gosexam.extension.applyStatusBarInsetsOnly
import com.koen.gosexam.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@AndroidEntryPoint
class ExamFragment :
    BaseFragment<ExamUiState, ExamViewModel, FragmentExamBinding>(R.layout.fragment_exam) {

    companion object {
        const val KEY_ARG_EXAM_TEST_UI = "KEY_ARG_EXAM_TEST_UI"
    }

    override val viewModel: ExamViewModel by viewModels()

    private val adapterExam by lazy {
        ExamAdapter { exam, answer ->
            viewModel.updateAnswerList()
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
            }
        }
    }

    override fun handleUiState(uiState: ExamUiState) {
        super.handleUiState(uiState)
        Log.e("TAG1", uiState.examUiList.size.toString())
        adapterExam.items = uiState.examUiList
    }

}