package com.koen.gosexam.extension

import android.graphics.Rect
import android.view.View
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment

/**
 * Apply system and IME insets to the provided [view] within the indicated [Fragment]. Both, top and
 * bottom system insets, as well IME are applied to [view]'s paddings. Besides that, the flags for
 * inset bars' lightness status.
 *
 * @param view the view to which the insets must be applied
 * @param isStatusBarLight indicates if the bg color of the status bar is currently light.
 * @param isNavigationBarLight indicates if the bg color of the nav bar is currently light.
 */
fun Fragment.applyWindowInsets(
    view: View,
    isStatusBarLight: Boolean = true,
    isNavigationBarLight: Boolean = true,
    keyboardVisibilityListener: (Boolean) -> Unit = {}
) {

    WindowInsetsControllerCompat(
        requireActivity().window,
        requireActivity().window.decorView
    ).isAppearanceLightStatusBars = isStatusBarLight

    WindowInsetsControllerCompat(
        requireActivity().window,
        requireActivity().window.decorView
    ).isAppearanceLightNavigationBars = isNavigationBarLight

    val iniViewPaddings = Rect(
        view.paddingLeft,
        view.paddingTop,
        view.paddingRight,
        view.paddingBottom
    )
    val types = WindowInsetsCompat.Type.systemBars() + WindowInsetsCompat.Type.ime()
    ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
        val typeInsets = insets.getInsets(types)
        view.setPadding(
            iniViewPaddings.left,
            iniViewPaddings.top + typeInsets.top,
            iniViewPaddings.right,
            iniViewPaddings.bottom + typeInsets.bottom
        )

        keyboardVisibilityListener(insets.isVisible(WindowInsetsCompat.Type.ime()))

        WindowInsetsCompat.CONSUMED
    }
}

/**
 * Apply system and IME insets to the provided [topView] and [bottomView] within the indicated
 * [Fragment]. Top system insets are applied to the [topView], while bottom insets (IME and nvaBar)
 * are applied to the [bottomView].
 * Besides that, the flags for inset bars' lightness status.
 *
 * @param topView the top view to which the top insets (status bar) must be applied
 * @param bottomView the bottom view to which the bottom insets (IME and nav bar) must be applied
 * @param isStatusBarLight indicates if the bg color of the status bar is currently light
 * @param isNavigationBarLight indicates if the bg color of the nav bar is currently light
 */
fun Fragment.applyWindowInsets(
    topView: View,
    bottomView: View,
    isStatusBarLight: Boolean = true,
    isNavigationBarLight: Boolean = true
) {
    setStatusBarLight(isStatusBarLight)
    setNavBarLight(isNavigationBarLight)

    val initTopViewPaddings = Rect(
        topView.paddingLeft,
        topView.paddingTop,
        topView.paddingRight,
        topView.bottom
    )
    val initBottomViewPaddings = Rect(
        bottomView.paddingLeft,
        bottomView.paddingTop,
        bottomView.paddingRight,
        bottomView.bottom
    )

    val types = WindowInsetsCompat.Type.systemBars() + WindowInsetsCompat.Type.ime()
    ViewCompat.setOnApplyWindowInsetsListener(bottomView) { _, insets ->
        val typeInsets = insets.getInsets(types)
        topView.setPadding(
            initTopViewPaddings.left,
            initTopViewPaddings.top + typeInsets.top,
            initTopViewPaddings.right,
            initTopViewPaddings.bottom
        )
        bottomView.setPadding(
            initBottomViewPaddings.left,
            initBottomViewPaddings.top,
            initBottomViewPaddings.right,
            initBottomViewPaddings.bottom + typeInsets.bottom
        )
        WindowInsetsCompat.CONSUMED
    }
}

/**
 * Applies only the status bar insets to the top of the provided [view].
 */
fun Fragment.applyStatusBarInsetsOnly(
    view: View,
    isStatusBarLight: Boolean = true,
) {
    WindowInsetsControllerCompat(
        requireActivity().window,
        requireActivity().window.decorView
    ).isAppearanceLightStatusBars = isStatusBarLight

    val iniViewTopPadding = view.paddingTop
    val types = WindowInsetsCompat.Type.systemBars()
    ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
        val typeInsets = insets.getInsets(types)
        view.updatePadding(top = iniViewTopPadding + typeInsets.top)
        WindowInsetsCompat.CONSUMED
    }
}

fun Fragment.setStatusBarLight(isStatusBarLight: Boolean) {
    WindowInsetsControllerCompat(
        requireActivity().window,
        requireActivity().window.decorView
    ).isAppearanceLightStatusBars = isStatusBarLight
}

fun Fragment.setNavBarLight(isNavigationBarLight: Boolean) {
    WindowInsetsControllerCompat(
        requireActivity().window,
        requireActivity().window.decorView
    ).isAppearanceLightNavigationBars = isNavigationBarLight
}

fun Fragment.changeSoftInput(maskAdjustResize: Boolean = true) {
    if (maskAdjustResize) {
        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
    } else {
        WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST
    }.also {
        activity?.window?.setSoftInputMode(it)
    }

}