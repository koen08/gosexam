package com.koen.gosexam.presentation.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
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
            val buttonHelpers = ButtonHelpers.getButtonHelpers(text.toString())
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

        binding.etAmountQuestion.editText?.addTextChangedListener(textWatcher)
    }

    override fun onDestroyView() {
        binding.etAmountQuestion.editText?.removeTextChangedListener(textWatcher)
        super.onDestroyView()
    }

    override fun handleUiState(uiState: MainUiState) {
        super.handleUiState(uiState)
        binding.btnSelectionWorkout.check = uiState.typeExam == TypeExam.PRACTICE
        binding.btnSelectionExam.check = uiState.typeExam == TypeExam.EXAM
        setCheckHelpersButton(uiState.buttonHelpers)

        binding.etAmountQuestion.editText?.setText(uiState.currentText)
        val text = binding.etAmountQuestion.editText?.text.toString().length
        binding.etAmountQuestion.editText?.setSelection(text)
    }

    override fun handleUiEvent(uiEvent: UiEvent) {
        super.handleUiEvent(uiEvent)
        if (uiEvent is ErrorTextInput) {
            binding.etAmountQuestion.error = uiEvent.error
        }
    }

    private fun setCheckHelpersButton(buttonHelpers: ButtonHelpers?) {
        `listSelectionButtonMini`.forEach {
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