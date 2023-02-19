package com.koen.gosexam.presentation.profile

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentExamListBinding
import com.koen.gosexam.databinding.FragmentMainBinding
import com.koen.gosexam.databinding.FragmentProfileBinding
import com.koen.gosexam.presentation.base.BaseFragment
import com.koen.gosexam.presentation.examlist.ExamListUiState
import com.koen.gosexam.presentation.examlist.ExamListViewModel

class ProfileFragment:
    BaseFragment<ProfileUiState, ProfileViewModel, FragmentProfileBinding>(R.layout.fragment_profile) {
    override val viewModel: ProfileViewModel by viewModels()

    override fun initViewBinding(inflater: LayoutInflater): FragmentProfileBinding =
        FragmentProfileBinding.inflate(inflater)
}