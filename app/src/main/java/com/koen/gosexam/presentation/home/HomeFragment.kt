package com.koen.gosexam.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.koen.gosexam.R
import com.koen.gosexam.databinding.FragmentHomeBinding
import com.koen.gosexam.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<HomeUiState, HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {
    override val viewModel: HomeViewModel by viewModels()

    override fun initViewBinding(inflater: LayoutInflater): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment: NavHostFragment =
            childFragmentManager.findFragmentById(R.id.navHostContainer) as NavHostFragment
        val bottomNav = binding.bottomNav
        bottomNav.setupWithNavController(navHostFragment.navController)
        applySystemInsets()
    }

    private fun applySystemInsets() {
        val types = WindowInsetsCompat.Type.systemBars() + WindowInsetsCompat.Type.ime()
        ViewCompat.setOnApplyWindowInsetsListener(binding.navHostContainer) { _, insets ->
            val typeInsets = insets.getInsets(types)

            if (insets.isVisible(WindowInsetsCompat.Type.ime())) {
                binding.apply {
                    navHostContainer.updatePadding(bottom = typeInsets.bottom)
                    bottomNav.isVisible = false
                }
            } else {
                binding.apply {
                    bottomNav.post {
                        binding.navHostContainer.updatePadding(
                            bottom = binding.bottomNav.height
                        )
                    }
                    bottomNav.isVisible = true
                }
            }
            insets
        }
    }
}