package com.koen.gosexam.presentation.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.koen.gosexam.R
import com.koen.gosexam.databinding.WidgetSelectionButtonBinding
import com.koen.gosexam.extension.orEmpty

class SelectionButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding = WidgetSelectionButtonBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    var text: CharSequence?
        get() = binding.tvBtnSelection.text
        set(value) {
            binding.tvBtnSelection.text = value
        }

    var backgroundButton: Drawable?
        get() = binding.containerSelection.background
        set(value) {
            binding.containerSelection.background = value
        }

    var check: Boolean = false
        set(value) {
            field = value
            backgroundButton = changeBackground(value, context)
            binding.tvBtnSelection.setTextColor(changeTextColor(value, context))
            visibleIconCheck(value)
        }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.SelectionButton).apply {
            getString(R.styleable.SelectionButton_android_text).orEmpty().apply {
                text = this
            }
            getBoolean(R.styleable.SelectionButton_check, false).also {
                check = it
            }
            recycle()
        }
    }

    private fun visibleIconCheck(check: Boolean) {
        binding.imgCheck.isVisible = check
    }
}