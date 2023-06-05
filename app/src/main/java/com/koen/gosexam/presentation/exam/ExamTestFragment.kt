package com.koen.gosexam.presentation.exam

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentExamBinding
import com.koen.gosexam.extension.applyStatusBarInsetsOnly
import com.koen.gosexam.extension.findTopNavController
import com.koen.gosexam.extension.hideBtn
import com.koen.gosexam.extension.showBtn
import com.koen.gosexam.presentation.base.BaseFragment
import com.koen.gosexam.presentation.exam.ExamTestUiState.ExamMode.Companion.isExam
import com.koen.gosexam.presentation.exam.result.ExamResultFragment.Companion.ARG_KEY_RESULT_UI
import com.koen.gosexam.presentation.models.base.UiEvent
import com.koen.gosexam.presentation.models.uiEvent.HideButton
import com.koen.gosexam.presentation.models.uiEvent.OpenResultTest
import com.koen.gosexam.presentation.models.uiEvent.ShowAds
import com.koen.gosexam.presentation.models.uiEvent.ShowButton
import com.koen.gosexam.presentation.models.uiEvent.TimerTicket
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.rewarded.Reward
import com.yandex.mobile.ads.rewarded.RewardedAd
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExamTestFragment :
    BaseFragment<ExamTestUiState, ExamViewModel, FragmentExamBinding>(R.layout.fragment_exam) {

    companion object {
        const val KEY_ARG_EXAM_TEST_UI = "KEY_ARG_EXAM_TEST_UI"
    }

    override val viewModel: ExamViewModel by viewModels()

    private val adapterExam by lazy {
        ExamTestAdapter { exam, answer ->
            viewModel.updateAnswerList(answer, exam)
        }
    }

    override fun initViewBinding(inflater: LayoutInflater): FragmentExamBinding {
        return FragmentExamBinding.inflate(inflater)
    }

    var mRewardedAd : RewardedAd? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            applyStatusBarInsetsOnly(root)
            vpExam.apply {
                adapter = adapterExam
                isUserInputEnabled = false
            }
            var lastClickTime: Long = 0
            btnNext.setOnClickListener {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime > 500) {
                    viewModel.updatePosition()
                    lastClickTime = currentTime
                }
            }
            toolbar.setNavigationOnClickListener {
                findTopNavController().popBackStack()
            }
        }
        mRewardedAd = RewardedAd(requireContext());
        mRewardedAd?.setAdUnitId("R-M-2317669-1");
        val adRequest: AdRequest = AdRequest.Builder().build()
        //demo - demo-rewarded-yandex //release - R-M-2317669-1
        mRewardedAd?.setRewardedAdEventListener(object : RewardedAdEventListener {
            override fun onAdLoaded() {
                Log.e("YANDEX:ADS:", "Show completed: onAdLoaded")
                viewModel.updateSuccessAds(ExamTestUiState.LoadingStateAds.SUCCESS)
            }

            override fun onAdFailedToLoad(error: AdRequestError) {
                Log.e("YANDEX:ADS:", "Failed process load ads : ${error.description}")
                viewModel.updateSuccessAds(ExamTestUiState.LoadingStateAds.FAILED)
            }

            override fun onAdShown() = Unit

            override fun onAdDismissed() = Unit

            override fun onAdClicked() = Unit

            override fun onLeftApplication() = Unit

            override fun onReturnedToApplication() = Unit

            override fun onImpression(p0: ImpressionData?) = Unit

            override fun onRewarded(p0: Reward) {
                viewModel.prepareResult()
            }

        })
        mRewardedAd?.loadAd(adRequest);
    }

    override fun handleUiState(uiState: ExamTestUiState) {
        super.handleUiState(uiState)
        adapterExam.items = uiState.examList
        binding.vpExam.setCurrentItem(uiState.currentPosition, true)
        uiState.currentExam?.let { element ->
            binding.toolbar.title = element.positionQuestionTitle
        }
        binding.btnNext.text = uiState.btnText
        binding.tvTimer.isVisible = uiState.examMode.isExam()
    }

    override fun handleUiEvent(uiEvent: UiEvent) {
        super.handleUiEvent(uiEvent)
        when(uiEvent) {
            is ShowButton -> {
                binding.btnNext.showBtn()
            }
            is HideButton -> {
                binding.btnNext.hideBtn()
            }
            is OpenResultTest -> {
                findNavController().navigate(
                    R.id.action_examFragment_to_examResultFragment, bundleOf(
                        ARG_KEY_RESULT_UI to uiEvent.id
                    )
                )
            }
            is ShowAds -> {
                Toast.makeText(requireContext(), "Загрузка рекламы", Toast.LENGTH_SHORT).show()
                if (mRewardedAd?.isLoaded == true) {
                    mRewardedAd?.show()
                } else viewModel.sendShowAds()
            }
            is TimerTicket -> {
                binding.tvTimer.text = uiEvent.time
            }
        }
    }

}