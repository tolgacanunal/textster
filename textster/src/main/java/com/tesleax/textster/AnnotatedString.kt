package com.tesleax.textster

import android.content.Context
import android.os.Parcelable
import android.text.Annotation
import android.text.SpannableStringBuilder
import android.text.SpannedString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnnotatedString(
    val resId: Int,

    val xmlStyleOption: XmlStyleOption = XmlStyleOption()
): Parcelable {

    fun getStyledCharSequence(context: Context): CharSequence {
        val xmlText = context.resources.getText(resId)
        if (xmlText !is SpannedString) {
            return xmlText
        }

        val spannableString = SpannableStringBuilder(xmlText)
        xmlText.getSpans(0, xmlText.length, Annotation::class.java).forEach { annotation ->
            when (annotation.key) {
                "type" -> {
                    spannableString.applyType(annotation)
                }
                "replacement" -> {
                    xmlStyleOption.replacementList.find { (key, _) ->
                        key == annotation.value
                    }?.let { (_, replacementValue) ->
                        spannableString.replaceAnnotation(
                            annotation,
                            replacementValue
                        )
                    }
                }
                "color" -> {
                    spannableString.colorizeAnnotation(annotation)
                }
                "font" -> {
                    spannableString.applyFontAnnotation(context, annotation)
                }
                "custom" -> {
                    xmlStyleOption.customAnnotations.find { (key, _) ->
                        key == annotation.value
                    }?.let { (_, span) ->
                        spannableString.applySpan(span, annotation)
                    }
                }
            }
        }
        return spannableString
    }
}
