package com.koen.gosexam.extension

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.widget.Button
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible

private const val DURATION_ANIM = 350L

fun Button.showBtn(durationTime: Long = DURATION_ANIM) {
    isVisible = true
    alpha = 0f
    translationY = 100f

    val animator = ObjectAnimator.ofPropertyValuesHolder(
        this,
        PropertyValuesHolder.ofFloat(View.ALPHA, 1f),
        PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f)
    ).apply {
        duration = durationTime
        startDelay = 0
    }

    post {
        animator.start()
    }
}

fun Button.hideBtn(durationTime: Long = DURATION_ANIM) {
    alpha = 1f
    translationY = 0f

    val animator = ObjectAnimator.ofPropertyValuesHolder(
        this,
        PropertyValuesHolder.ofFloat(View.ALPHA, 0f),
        PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 100f)
    ).apply {
        duration = durationTime
        startDelay = 0
    }

    post {
        animator.start()
    }
    animator.doOnEnd {
        isVisible = false
    }
}