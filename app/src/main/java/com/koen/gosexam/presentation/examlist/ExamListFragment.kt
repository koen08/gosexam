package com.koen.gosexam.presentation.examlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentExamListBinding
import com.koen.gosexam.presentation.base.BaseFragment
import com.koen.gosexam.presentation.models.ExamUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExamListFragment :
    BaseFragment<ExamListUiState, ExamListViewModel, FragmentExamListBinding>(R.layout.fragment_exam_list) {
    override val viewModel: ExamListViewModel by viewModels()
    private val adapterExamList by lazy {
        ExamListAdapter()
    }

    override fun initViewBinding(inflater: LayoutInflater): FragmentExamListBinding =
        FragmentExamListBinding.inflate(inflater)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvQuestions.apply {
            adapter = adapterExamList
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun handleUiState(uiState: ExamListUiState) {
        super.handleUiState(uiState)
        adapterExamList.items = uiState.examList
    }
}