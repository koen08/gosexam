package com.koen.gosexam.presentation.question.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentQuestionDetailsBinding
import com.koen.gosexam.extension.findTopNavController
import com.koen.gosexam.extension.getOrEmpty
import com.koen.gosexam.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionDetailsFragment :
    BaseFragment<QuestionDetailsUiState, QuestionDetailsViewModel, FragmentQuestionDetailsBinding>(R.layout.fragment_question_details) {

    companion object {
        const val KEY_ARG_QUESTION_EXAM_UI = "examUi"
        const val INDEX_TRUE_ANSWER = 0
    }

    override val viewModel: QuestionDetailsViewModel by viewModels()

    override fun initViewBinding(inflater: LayoutInflater): FragmentQuestionDetailsBinding =
        FragmentQuestionDetailsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findTopNavController().popBackStack()
        }
    }

    override fun handleUiState(uiState: QuestionDetailsUiState) {
        super.handleUiState(uiState)
        binding.tvTitle.text = uiState.question
        binding.toolbar.title = uiState.titleNumber
        binding.tvTrueQuestion.text =
            uiState.answers.getOrEmpty(INDEX_TRUE_ANSWER)

        binding.listFalseQuestion.removeAllViews()
        addAnswerFalse(
            uiState.answerFalseText,
            uiState.answerFalseStyle,
            uiState.answerFalseColor
        )

    }

    private fun addAnswerFalse(answers: List<String>, style: Int, color: Int) {
        answers.forEach { answer ->
            val textView = TextView(requireContext())
            textView.text = answer
            textView.setTextAppearance(style)
            textView.setTextColor(color)
            binding.listFalseQuestion.addView(textView)
        }
    }

}