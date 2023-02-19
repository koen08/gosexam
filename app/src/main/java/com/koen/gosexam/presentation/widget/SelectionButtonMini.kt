package com.koen.gosexam.presentation.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.koen.gosexam.R
import com.koen.gosexam.databinding.WidgetSelectionButtonMiniBinding
import com.koen.gosexam.extension.orEmpty

class SelectionButtonMini @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding = WidgetSelectionButtonMiniBinding.inflate(
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
        }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.SelectionButtonMini).apply {
            getString(R.styleable.SelectionButtonMini_android_text).orEmpty().apply {
                text = this
            }
            getBoolean(R.styleable.SelectionButtonMini_checkBtn, false).also {
                check = it
            }
            recycle()
        }
    }
}