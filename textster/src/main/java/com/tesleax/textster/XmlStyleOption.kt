package com.tesleax.textster

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class XmlStyleOption(
    val replacementList: List<Pair<CharSequence, CharSequence>> = emptyList(),
    val customAnnotations: List<Pair<CharSequence, Any>> = emptyList()
): Parcelable
