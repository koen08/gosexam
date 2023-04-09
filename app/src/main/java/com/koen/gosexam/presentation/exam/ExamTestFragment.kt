package com.koen.gosexam.presentation.exam

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentExamBinding
import com.koen.gosexam.extension.applyStatusBarInsetsOnly
import com.koen.gosexam.extension.findTopNavController
import com.koen.gosexam.extension.hideBtn
import com.koen.gosexam.extension.showBtn
import com.koen.gosexam.presentation.base.BaseFragment
import com.koen.gosexam.presentation.exam.result.ExamResultFragment.Companion.ARG_KEY_RESULT_UI
import com.koen.gosexam.presentation.models.base.UiEvent
import com.koen.gosexam.presentation.models.uiEvent.HideButton
import com.koen.gosexam.presentation.models.uiEvent.OpenResultTest
import com.koen.gosexam.presentation.models.uiEvent.ShowButton
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
            btnNext.setOnClickListener {
                viewModel.updatePosition()
            }
            toolbar.setNavigationOnClickListener {
                findTopNavController().popBackStack()
            }
        }
    }

    override fun handleUiState(uiState: ExamTestUiState) {
        super.handleUiState(uiState)
        adapterExam.items = uiState.examUiList
        binding.vpExam.setCurrentItem(uiState.currentPosition, true)
        uiState.currentExam?.let { element ->
            binding.toolbar.title = element.positionQuestionTitle
        }
        binding.btnNext.text = uiState.btnText
    }

    override fun handleUiEvent(uiEvent: UiEvent) {
        super.handleUiEvent(uiEvent)
        when(uiEvent) {
            is ShowButton -> {
                binding.btnNext.showBtn()
            }
            is HideButton -> {
                binding.btnNext.hideBtn()
            }
            is OpenResultTest -> {
                findNavController().navigate(
                    R.id.action_examFragment_to_examResultFragment, bundleOf(
                        ARG_KEY_RESULT_UI to uiEvent.resultTest
                    )
                )
            }
        }
    }

}