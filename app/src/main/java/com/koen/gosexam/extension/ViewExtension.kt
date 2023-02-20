package com.koen.gosexam.extension

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet


private const val EXPAND_ANIM_DURATION = 100L
private const val COLLAPSE_ANIM_DURATION = 100L
/**
 * Animates the provided view to be expanded by changing its visibility.
 *
 * IMPORTANT!!!!!!!!
 * When the expandable view is inside a ViewHolder in RecyclerView it is NECESSARY to provide the
 * RecyclerView to [containerViewGroup] argument of this function to ensure smooth animations.
 *
 * @param containerViewGroup the ViewGroup which holds the provided expandable View. For a view
 * which is inside a ViewHolder, please provide the RecyclerView which holds this ViewHolder.
 *
 * @param animDuration the duration of the animation, by default set to NULL. If null, then default
 * value of [EXPAND_ANIM_DURATION] is taken.
 */
fun View.expand(containerViewGroup: ViewGroup, animDuration: Long? = null) {
    if (isVisible) return

    val autoTransition = AutoTransition().apply {
        duration = animDuration ?: EXPAND_ANIM_DURATION
        ordering = TransitionSet.ORDERING_TOGETHER
    }
    TransitionManager.beginDelayedTransition(containerViewGroup, autoTransition)
    isVisible = true
}

/**
 * Animates the provided view to be collapsed by changing its visibility.
 *
 * IMPORTANT!!!!!!!!
 * When the collapsable view is inside a ViewHolder in RecyclerView it is NECESSARY to provide the
 * RecyclerView to [containerViewGroup] argument of this function to ensure smooth animations.
 *
 * @param containerViewGroup the ViewGroup which holds the provided collapsable View. For a view
 * which is inside a ViewHolder, please provide the RecyclerView which holds this ViewHolder.
 *
 * @param animDuration the duration of the animation, by default set to NULL. If null, then default
 * value of [COLLAPSE_ANIM_DURATION] is taken.
 */
fun View.collapse(containerViewGroup: ViewGroup, animDuration: Long? = null) {
    if (!isVisible) return

    val autoTransition = AutoTransition().apply {
        duration = animDuration ?: COLLAPSE_ANIM_DURATION
        ordering = TransitionSet.ORDERING_TOGETHER
    }
    TransitionManager.beginDelayedTransition(containerViewGroup, autoTransition)
    isVisible = false
}