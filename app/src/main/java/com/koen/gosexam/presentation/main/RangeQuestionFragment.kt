package com.koen.gosexam.presentation.main

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.slider.RangeSlider
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentRangeQuestionBinding
import com.koen.gosexam.extension.orZero
import com.koen.gosexam.presentation.base.BaseFragment
import com.koen.gosexam.presentation.models.base.UiEvent
import com.koen.gosexam.presentation.models.uiEvent.RangeSliderInit
import com.koen.gosexam.presentation.models.uiEvent.RangeSliderValues

class RangeQuestionFragment :
    BaseFragment<MainUiState, MainViewModel, FragmentRangeQuestionBinding>(R.layout.fragment_range_question) {
    override val viewModel: MainViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun initViewBinding(inflater: LayoutInflater): FragmentRangeQuestionBinding =
        FragmentRangeQuestionBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            viewModel.sendEventRangeSlider()
            rangeSlider.addOnChangeListener { slider, value, fromUser ->
                val sliderValues = slider.values
                val fromValue = sliderValues.getOrNull(0)
                val toValue = sliderValues.getOrNull(1)
                viewModel.updateRange(
                    startRange = fromValue?.toInt().orZero,
                    endRange = toValue?.toInt().orZero
                )
            }
            rangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: RangeSlider) = Unit

                override fun onStopTrackingTouch(slider: RangeSlider) {
                    val sliderValues = slider.values
                    viewModel.updateRange(
                        startRange = sliderValues.getOrNull(0).orZero.toInt(),
                        endRange = sliderValues.getOrNull(1).orZero.toInt()
                    )
                }

            })

            etStart.editText?.doAfterTextChanged {
                viewModel.updateStartTextRange(
                    text = it.toString()
                )
            }

            etFinish.editText?.doAfterTextChanged {
                viewModel.updateEndTextRange(
                    text = it.toString()
                )
            }

            etStart.editText?.setOnEditorActionListener { textView, i, keyEvent ->
                return@setOnEditorActionListener if (i == EditorInfo.IME_ACTION_DONE) {
                    viewModel.updateRangeAndSendEvent(
                        startRange = textView.text.toString(),
                        endRange = etFinish.editText?.text.toString()
                    )
                    val imm = context?.getSystemService(
                        INPUT_METHOD_SERVICE
                    ) as InputMethodManager?
                    imm?.hideSoftInputFromWindow(binding.etStart.windowToken, 0)
                    true
                } else false
            }
            etFinish.editText?.setOnEditorActionListener { textView, i, keyEvent ->
                return@setOnEditorActionListener if (i == EditorInfo.IME_ACTION_DONE) {
                    viewModel.updateRangeAndSendEvent(
                        endRange = textView.text.toString(),
                        startRange = etStart.editText?.text.toString()
                    )
                    val imm = context?.getSystemService(
                        INPUT_METHOD_SERVICE
                    ) as InputMethodManager?
                    imm?.hideSoftInputFromWindow(binding.etFinish.windowToken, 0)
                    true
                } else false
            }

            cbRandomQuestions.setOnCheckedChangeListener { _, isChecked ->
                viewModel.updateRandomRange(
                    isChecked
                )
            }

        }
    }

    override fun handleUiEvent(uiEvent: UiEvent) {
        super.handleUiEvent(uiEvent)
        if (uiEvent is RangeSliderInit) {
            binding.rangeSlider.apply {
                val valueMin = uiEvent.min.toFloat()
                val valueMax = uiEvent.max.toFloat()
                valueFrom = valueMin
                valueTo = valueMax
                stepSize = valueMin
                values = listOf(valueMin, valueMax)
            }
        }
        if (uiEvent is RangeSliderValues) {
            binding.rangeSlider.apply {
                values = listOf(uiEvent.min.toFloat(), uiEvent.max.toFloat())
            }
        }
    }
}