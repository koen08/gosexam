package com.koen.gosexam.presentation.exam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentExamBinding
import com.koen.gosexam.extension.applyStatusBarInsetsOnly
import com.koen.gosexam.presentation.base.BaseFragment

class ExamFragment:
    BaseFragment<ExamUiState, ExamViewModel, FragmentExamBinding>(R.layout.fragment_exam) {
    override val viewModel: ExamViewModel by viewModels()

    override fun initViewBinding(inflater: LayoutInflater): FragmentExamBinding {
        return FragmentExamBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyStatusBarInsetsOnly(binding.root)
    }

}