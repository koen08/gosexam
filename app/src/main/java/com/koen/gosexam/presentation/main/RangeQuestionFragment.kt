package com.koen.gosexam.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentMainBinding
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
                viewModel.updateRangeAndSendEvent(
                    startRange = it.toString()
                )
            }

            etFinish.editText?.doAfterTextChanged {
                viewModel.updateRangeAndSendEvent(
                    endRange = it.toString()
                )
            }
        }
    }

    override fun handleUiState(uiState: MainUiState) {
        super.handleUiState(uiState)
        binding.run {
            etStart.editText?.setText(uiState.startRange.toString())
            etFinish.editText?.setText(uiState.endRange.toString())
            etStart.editText?.setSelection(etStart.editText?.length().orZero)
            etFinish.editText?.setSelection(etFinish.editText?.length().orZero)
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