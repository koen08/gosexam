package com.koen.gosexam.presentation.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentProfileBinding
import com.koen.gosexam.databinding.FragmentSelectionFacultyBinding
import com.koen.gosexam.domain.models.TypeFaculty
import com.koen.gosexam.domain.models.TypeFaculty.Companion.isMedical
import com.koen.gosexam.domain.models.TypeFaculty.Companion.isPediator
import com.koen.gosexam.extension.findTopNavController
import com.koen.gosexam.presentation.base.BaseFragment
import com.koen.gosexam.presentation.exam.ExamTestFragment
import com.koen.gosexam.presentation.models.base.UiEvent
import com.koen.gosexam.presentation.models.uiEvent.ErrorUiEvent
import com.koen.gosexam.presentation.models.uiEvent.Finish
import com.koen.gosexam.presentation.profile.ProfileUiState
import com.koen.gosexam.presentation.profile.ProfileViewModel
import com.koen.gosexam.presentation.profile.SelectionFacultyUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectionFacultyFragment :
    BaseFragment<SelectionFacultyUiState, SelectionFacultyViewModel, FragmentSelectionFacultyBinding>(R.layout.fragment_selection_faculty) {

    companion object {
        const val REQUEST_KEY_SELECTION_FINISH = "REQUEST_KEY_SELECTION_FINISH"
    }

    override val viewModel: SelectionFacultyViewModel by viewModels()

    override fun initViewBinding(inflater: LayoutInflater): FragmentSelectionFacultyBinding =
        FragmentSelectionFacultyBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            btnSelectionFaculty.setOnClickListener {
                viewModel.saveTypeFaculty()
            }
            btnSelectionMedical.setOnClickListener {
                viewModel.updateTypeFaculty(TypeFaculty.MEDICAL)
            }
            btnSelectionPediator.setOnClickListener {
                viewModel.updateTypeFaculty(TypeFaculty.PEDIATOR)
            }
        }
    }

    override fun handleUiState(uiState: SelectionFacultyUiState) {
        super.handleUiState(uiState)
        binding.run {
            btnSelectionMedical.check = uiState.typeFaculty.isMedical
            btnSelectionPediator.check = uiState.typeFaculty.isPediator
        }
    }

    override fun handleUiEvent(uiEvent: UiEvent) {
        super.handleUiEvent(uiEvent)
        if (uiEvent is ErrorUiEvent) {
            Toast.makeText(requireContext(), uiEvent.errorText, Toast.LENGTH_SHORT).show()
        }
        if (uiEvent is Finish) {
            requireActivity().supportFragmentManager.setFragmentResult(REQUEST_KEY_SELECTION_FINISH, bundleOf())
            findTopNavController().navigate(
                R.id.action_selectionFacultyFragment_to_homeFragment
            )
        }
    }
}