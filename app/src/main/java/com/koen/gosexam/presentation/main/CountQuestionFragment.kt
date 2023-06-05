package com.koen.gosexam.presentation.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentCountQuestionBinding
import com.koen.gosexam.extension.changeSoftInput
import com.koen.gosexam.extension.findTopNavController
import com.koen.gosexam.presentation.base.BaseFragment
import com.koen.gosexam.presentation.exam.ExamTestFragment
import com.koen.gosexam.presentation.models.base.UiEvent
import com.koen.gosexam.presentation.models.uiEvent.ErrorTextInput
import com.koen.gosexam.presentation.models.uiEvent.OpenExamTest
import com.koen.gosexam.presentation.models.uiEvent.StartAnim
import com.koen.gosexam.presentation.widget.SelectionButtonMini
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountQuestionFragment : BaseFragment<MainUiState, MainViewModel, FragmentCountQuestionBinding>(
    R.layout.fragment_count_question) {

    override val viewModel: MainViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun initViewBinding(inflater: LayoutInflater): FragmentCountQuestionBinding =
        FragmentCountQuestionBinding.inflate(inflater)

    private var listSelectionButtonMini: Map<ButtonHelpers, SelectionButtonMini> = emptyMap()

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val buttonHelpers = ButtonHelpers.getButtonHelpers(text.toString(), viewModel.uiState.value.examSize)
            binding.etAmountQuestion.error = ""
            viewModel.changeButtonHelpersChangeText(buttonHelpers, text.toString())
        }

        override fun afterTextChanged(edit: Editable?) = Unit

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMapSelectionBtnMini()
        changeSoftInput(false)
        binding.btnSelectionExam.setOnClickListener {
            viewModel.changeTypeExam(TypeExam.EXAM)
        }
        binding.btnSelectionWorkout.setOnClickListener {
            viewModel.changeTypeExam(TypeExam.PRACTICE)
            binding.etAmountQuestion.error = "true"
        }
        binding.btnMax.setOnClickListener {
            viewModel.changeButtonHelpersActive(ButtonHelpers.MAX)
        }
        binding.btn100.setOnClickListener {
            viewModel.changeButtonHelpersActive(ButtonHelpers.HIGH)
        }
        binding.btn50.setOnClickListener {
            viewModel.changeButtonHelpersActive(ButtonHelpers.MEDIUM)
        }
        binding.btnMin.setOnClickListener {
            viewModel.changeButtonHelpersActive(ButtonHelpers.MIN)
        }
        binding.imgInfoExamType.setOnClickListener {
            viewModel.onClickInfoTypeExam()
        }

        binding.btnModeExam.setOnClickListener {
            viewModel.updateModeExam()
        }

        binding.etAmountQuestion.editText?.addTextChangedListener(textWatcher)
    }

    override fun onDestroyView() {
        binding.etAmountQuestion.editText?.removeTextChangedListener(textWatcher)
        super.onDestroyView()
    }

    private val alphaAnimationTrue = AlphaAnimation(0f, 1f).apply {
        duration = 250
    }

    private val alphaAnimationFalse = AlphaAnimation(1f, 0f).apply {
        duration = 250
    }

    override fun handleUiState(uiState: MainUiState) {
        super.handleUiState(uiState)
        binding.btnSelectionWorkout.check = uiState.typeExam == TypeExam.PRACTICE
        binding.btnSelectionExam.check = uiState.typeExam == TypeExam.EXAM
        setCheckHelpersButton(uiState.buttonHelpers)

        binding.etAmountQuestion.editText?.setText(uiState.currentText)
        val text = binding.etAmountQuestion.editText?.text.toString().length
        binding.etAmountQuestion.editText?.setSelection(text)
        binding.btnMax.text = uiState.examSize.toString()
        binding.tvInfoTest.text = uiState.textInfoTest
        val modeExam = uiState.examMode == MainUiState.ExamMode.EXAM
        binding.btnModeExam.check = modeExam
        binding.etAmountQuestion.isEnabled = !modeExam
        binding.containerMiniBtn.isVisible = uiState.visibleBtnMini
        binding.tvInfoTime.isVisible = !uiState.visibleBtnMini

        binding.btnModeExam.text = uiState.btnExamText
    }

    override fun handleUiEvent(uiEvent: UiEvent) {
        super.handleUiEvent(uiEvent)
        if (uiEvent is ErrorTextInput) {
            binding.etAmountQuestion.error = uiEvent.error
        }
        if (uiEvent is StartAnim) {
            if (uiEvent.visible) {
                alphaAnimationFalse
            } else {
                alphaAnimationTrue
            }.let {
                binding.tvInfoTime.startAnimation(it)
            }
            if (uiEvent.visible) {
                alphaAnimationTrue
            } else {
                alphaAnimationFalse
            }.let {
                binding.containerMiniBtn.startAnimation(it)
            }
        }
    }

    private fun setCheckHelpersButton(buttonHelpers: ButtonHelpers?) {
        listSelectionButtonMini.forEach {
            it.value.check = false
        }

        listSelectionButtonMini[buttonHelpers]?.check = true
    }

    private fun initMapSelectionBtnMini() {
        listSelectionButtonMini = mapOf(
            ButtonHelpers.MAX to binding.btnMax,
            ButtonHelpers.HIGH to binding.btn100,
            ButtonHelpers.MEDIUM to binding.btn50,
            ButtonHelpers.MIN to binding.btnMin
        )
    }
}