package com.koen.gosexam.presentation.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentMainBinding
import com.koen.gosexam.extension.applyStatusBarInsetsOnly
import com.koen.gosexam.extension.applyWindowInsets
import com.koen.gosexam.presentation.base.BaseFragment
import com.koen.gosexam.presentation.widget.SelectionButtonMini
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment :
    BaseFragment<MainUiState, MainViewModel, FragmentMainBinding>(R.layout.fragment_main) {

    private var listSelectionButtonMini: Map<ButtonHelpers, SelectionButtonMini> = emptyMap()

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val buttonHelpers = ButtonHelpers.getButtonHelpers(text.toString())
            viewModel.changeButtonHelpersChangeText(buttonHelpers, text.toString())
        }

        override fun afterTextChanged(edit: Editable?) = Unit

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyStatusBarInsetsOnly(binding.toolbar)
        initMapSelectionBtnMini()
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

        Log.e("TAG1", uiState.toString())
        binding.etAmountQuestion.editText?.setText(uiState.currentText)
        val text = binding.etAmountQuestion.editText?.text.toString().length
        binding.etAmountQuestion.editText?.setSelection(text)
    }

    override val viewModel: MainViewModel by viewModels()

    override fun initViewBinding(inflater: LayoutInflater): FragmentMainBinding =
        FragmentMainBinding.inflate(inflater)


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