package com.koen.gosexam.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentExamListBinding
import com.koen.gosexam.databinding.FragmentMainBinding
import com.koen.gosexam.databinding.FragmentProfileBinding
import com.koen.gosexam.domain.models.TypeFaculty
import com.koen.gosexam.domain.models.TypeFaculty.Companion.isMedical
import com.koen.gosexam.domain.models.TypeFaculty.Companion.isPediator
import com.koen.gosexam.presentation.base.BaseFragment
import com.koen.gosexam.presentation.dialog.loader.FullScreenLoaderDialog
import com.koen.gosexam.presentation.examlist.ExamListUiState
import com.koen.gosexam.presentation.examlist.ExamListViewModel
import com.koen.gosexam.presentation.models.base.UiEvent
import com.koen.gosexam.presentation.models.uiEvent.DismissLoading
import com.koen.gosexam.presentation.models.uiEvent.Loading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment:
    BaseFragment<ProfileUiState, ProfileViewModel, FragmentProfileBinding>(R.layout.fragment_profile) {
    override val viewModel: ProfileViewModel by viewModels()

    override fun initViewBinding(inflater: LayoutInflater): FragmentProfileBinding =
        FragmentProfileBinding.inflate(inflater)

    private var loadingDialog : FullScreenLoaderDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            btnSelectionMedical.setOnClickListener {
                viewModel.saveTypeFaculty(TypeFaculty.MEDICAL)
            }
            btnSelectionPediator.setOnClickListener {
                viewModel.saveTypeFaculty(TypeFaculty.PEDIATOR)
            }
        }
    }

    override fun handleUiState(uiState: ProfileUiState) {
        super.handleUiState(uiState)
        binding.run {
            btnSelectionMedical.check = uiState.typeFaculty.isMedical
            btnSelectionPediator.check = uiState.typeFaculty.isPediator
        }
    }

    override fun handleUiEvent(uiEvent: UiEvent) {
        super.handleUiEvent(uiEvent)
        when(uiEvent) {
            is Loading -> {
                loadingDialog = FullScreenLoaderDialog.Builder(requireContext()).run {
                    setTitleText("Загрузка")
                    show()
                }
            }
            is DismissLoading -> {
                clearLoadingDialog()
            }
        }
    }

    private fun clearLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }
}