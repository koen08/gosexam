package com.koen.gosexam.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.koen.gosexam.presentation.core.CurrentTab

class MainAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return CurrentTab.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == CurrentTab.FIRST.ordinal) {
            CountQuestionFragment()
        } else {
            RangeQuestionFragment()
        }
    }
}