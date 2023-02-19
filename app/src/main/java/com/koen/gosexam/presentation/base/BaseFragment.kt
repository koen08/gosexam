package com.koen.gosexam.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.koen.gosexam.presentation.dialog.info.InfoDialog
import kotlinx.coroutines.launch

abstract class BaseFragment<UiStateModel : UiState, ViewModel : BaseViewModel<UiStateModel>, ViewBindingModel: ViewBinding>(@LayoutRes fragmentId: Int) :
    Fragment(fragmentId) {

    lateinit var binding: ViewBindingModel
    abstract val viewModel: ViewModel

    open fun handleUiState(uiState: UiStateModel) = Unit

    abstract fun initViewBinding(inflater: LayoutInflater) : ViewBindingModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initViewBinding(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectUiState()
        collectInfoDialog()
    }

    private fun collectUiState() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                handleUiState(it)
            }
        }
    }

    private fun collectInfoDialog() {
        lifecycleScope.launch {
            viewModel.infoDialogSharedFlow.flowWithLifecycle(lifecycle).collect {
                InfoDialog.create(requireContext(), it).show()
            }
        }
    }
}