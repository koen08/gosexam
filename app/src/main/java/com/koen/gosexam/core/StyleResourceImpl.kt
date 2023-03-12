package com.koen.gosexam.core

import android.content.res.Resources
import com.koen.gosexam.R

class StyleResourceImpl() : StyleResource {
    override val getDefaultBody: Int
        get() = R.style.DefaultBody
}