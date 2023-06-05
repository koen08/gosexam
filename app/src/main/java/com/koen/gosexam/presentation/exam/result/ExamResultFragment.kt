package com.koen.gosexam.presentation.exam.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentExamBinding
import com.koen.gosexam.databinding.FragmentExamResultBinding
import com.koen.gosexam.extension.applyStatusBarInsetsOnly
import com.koen.gosexam.extension.findTopNavController
import com.koen.gosexam.presentation.base.BaseFragment
import com.koen.gosexam.presentation.exam.ExamTestUiState
import com.koen.gosexam.presentation.exam.ExamViewModel
import com.koen.gosexam.presentation.models.base.UiEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExamResultFragment :
    BaseFragment<ExamResultUiState, ExamResultViewModel, FragmentExamResultBinding>(R.layout.fragment_exam_result) {

    companion object {
        const val ARG_KEY_RESULT_UI = "id"
    }

    override val viewModel: ExamResultViewModel by viewModels()

    override fun initViewBinding(inflater: LayoutInflater): FragmentExamResultBinding = FragmentExamResultBinding.inflate(inflater)

    private val adapterResult by lazy {
        ResultAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            applyStatusBarInsetsOnly(root)
            tvExams.adapter = adapterResult
            tvExams.layoutManager = LinearLayoutManager(requireContext())
            toolbar.setNavigationOnClickListener {
                findTopNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        binding.tvExams.adapter = null
        super.onDestroyView()
    }

    override fun handleUiState(uiState: ExamResultUiState) {
        super.handleUiState(uiState)
        binding.run {
            uiState.examResultList?.let {
                tvTitle.text = it.textTitleResult
                tvInfo.text = it.textDescriptionResult
                adapterResult.items = it.examList
                containerTitle.background = ContextCompat.getDrawable(requireContext(), it.backgroundTitle)
                tvTimer.isVisible = uiState.examResultList.isExam
                tvTimer.text = uiState.timerText
            }
        }
    }
}