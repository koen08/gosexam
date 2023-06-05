package com.koen.gosexam.presentation.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
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
import com.koen.gosexam.presentation.models.uiEvent.ShowAds
import com.koen.gosexam.presentation.start.SelectionFacultyFragment.Companion.REQUEST_KEY_SELECTION_FINISH
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.rewarded.Reward
import com.yandex.mobile.ads.rewarded.RewardedAd
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener
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

    var mRewardedAd : RewardedAd? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeSoftInput(false)
        binding.run {
            //reward()
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

    private fun reward() {
        mRewardedAd = RewardedAd(requireContext());
        mRewardedAd?.setAdUnitId("demo-rewarded-yandex");
        val adRequest: AdRequest = AdRequest.Builder().build()
        //demo - demo-rewarded-yandex //release - R-M-2317669-1
        mRewardedAd?.setRewardedAdEventListener(object : RewardedAdEventListener {
            override fun onAdLoaded() {
                Log.e("YANDEX:ADS:", "Show completed: onAdLoaded")

            }

            override fun onAdFailedToLoad(error: AdRequestError) {
                Log.e("YANDEX:ADS:", "Failed process load ads : ${error.description}")
                viewModel.generateExam()
            }

            override fun onAdShown() = Unit

            override fun onAdDismissed() {
                viewModel.generateExam()
            }

            override fun onAdClicked() = Unit

            override fun onLeftApplication() = Unit

            override fun onReturnedToApplication() = Unit

            override fun onImpression(p0: ImpressionData?) = Unit

            override fun onRewarded(p0: Reward) {
                viewModel.generateExam()
            }

        })
        mRewardedAd?.loadAd(adRequest);
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
                        KEY_ARG_EXAM_TEST_UI to uiEvent.settingsExam
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
            is ShowAds -> {
                Toast.makeText(requireContext(), "Загрузка рекламы", Toast.LENGTH_SHORT).show()
                if (mRewardedAd?.isLoaded == true) {
                    mRewardedAd?.show()
                } else viewModel.sendShowAds()
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