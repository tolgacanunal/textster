package com.tesleax.textster

import androidx.annotation.StringRes

data class AnnotatedString(
    @StringRes
    val resId: Int,

    val xmlStyleOption: XmlStyleOption = XmlStyleOption()
)
