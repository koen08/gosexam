package com.koen.gosexam.presentation.home

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.core.view.*
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


        WindowInsetsControllerCompat(
            requireActivity().window,
            requireActivity().window.decorView
        ).isAppearanceLightStatusBars = true
    }

    //todo https://github.com/aurelhubert/ahbottomnavigation/issues/171 ---- https://stackoverflow.com/questions/52391743/bottom-navigation-bar-moves-up-with-keyboard
    private fun isShowing() {
        val relative = binding.container
        relative.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            // r will be populated with the coordinates of your view
            // that area still visible.
            relative.getWindowVisibleDisplayFrame(r)
            val heightDiff: Int = (relative.rootView.height
                    - (r.bottom - r.top))
           // watcherState = heightDiff > 200


            //binding.bottomNav.isVisible = !watcherState
        }
    }
}