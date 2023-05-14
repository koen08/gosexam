package com.koen.gosexam.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentMainBinding
import com.koen.gosexam.extension.changeSoftInput
import com.koen.gosexam.extension.findTopNavController
import com.koen.gosexam.extension.orZero
import com.koen.gosexam.presentation.base.BaseFragment
import com.koen.gosexam.presentation.core.CurrentTab
import com.koen.gosexam.presentation.dialog.loader.FullScreenLoaderDialog
import com.koen.gosexam.presentation.exam.ExamTestFragment.Companion.KEY_ARG_EXAM_TEST_UI
import com.koen.gosexam.presentation.models.base.UiEvent
import com.koen.gosexam.presentation.models.uiEvent.DismissLoading
import com.koen.gosexam.presentation.models.uiEvent.Loading
import com.koen.gosexam.presentation.models.uiEvent.OpenExamTest
import com.koen.gosexam.presentation.models.uiEvent.OpenSelectionFaculty
import com.koen.gosexam.presentation.start.SelectionFacultyFragment.Companion.REQUEST_KEY_SELECTION_FINISH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment :
    BaseFragment<MainUiState, MainViewModel, FragmentMainBinding>(R.layout.fragment_main) {

    override val viewModel: MainViewModel by viewModels()

    override fun initViewBinding(inflater: LayoutInflater): FragmentMainBinding =
        FragmentMainBinding.inflate(inflater)

    private val tabSelectorListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            viewModel.changeTabSelected(
                currentTab = CurrentTab.getTab(tab?.position.orZero)
            )
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

        override fun onTabReselected(tab: TabLayout.Tab?) = Unit
    }

    private var loadingDialog: FullScreenLoaderDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeSoftInput(false)
        binding.run {
            btnStartExam.setOnClickListener {
                viewModel.updateRangeAndSendEvent(
                    viewModel.textStartRange, viewModel.testEndRange
                )
                viewModel.generateExam()
            }
            vpCreateTest.apply {
                adapter = MainAdapter(childFragmentManager, lifecycle)
                isUserInputEnabled = false
                isSaveEnabled = false
            }

            doubleTab.addOnTabSelectedListener(tabSelectorListener)

            doubleTab.getTabAt(viewModel.uiState.value.currentTab.ordinal)?.select()
            vpCreateTest.setCurrentItem(viewModel.uiState.value.currentTab.ordinal, false)
        }
        selectionSetFragmentResultListener()
        viewModel.checkIsFirstApp()
    }

    override fun handleUiState(uiState: MainUiState) {
        super.handleUiState(uiState)
        binding.run {
            vpCreateTest.setCurrentItem(uiState.currentTab.ordinal, true)
        }
    }

    override fun handleUiEvent(uiEvent: UiEvent) {
        super.handleUiEvent(uiEvent)
        when (uiEvent) {
            is OpenExamTest -> {
                findTopNavController().navigate(
                    R.id.action_homeFragment_to_examFragment, bundleOf(
                        KEY_ARG_EXAM_TEST_UI to uiEvent.examUiList
                    )
                )
            }

            is Loading -> {
                loadingDialog = FullScreenLoaderDialog.Builder(requireContext()).run {
                    setTitleText("Загрузка")
                    show()
                }
            }

            is DismissLoading -> {
                clearLoadingDialog()
            }

            is OpenSelectionFaculty -> {
                findTopNavController().navigate(
                    R.id.action_homeFragment_to_selectionFacultyFragment
                )
            }
        }
    }

    private fun clearLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    private fun selectionSetFragmentResultListener() {
        requireActivity().supportFragmentManager.setFragmentResultListener(REQUEST_KEY_SELECTION_FINISH, viewLifecycleOwner) { _, _ ->
        }
    }
}