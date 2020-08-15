package com.tesleax.textster

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.*
import android.text.Annotation
import android.text.style.*
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat

fun AnnotatedString.getStyledCharSequence(context: Context): CharSequence {
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
                    spannableString.replaceAnnotation(annotation, replacementValue)
                }
            }
            "color" -> {
                spannableString.colorizeAnnotation(annotation)
            }
            "url" -> {
                spannableString.applyUrlAnnotation(annotation)
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

private fun SpannableStringBuilder.replaceAnnotation(
    annotation: Annotation,
    replacementValue: CharSequence
) {
    replace(
        getSpanStart(annotation),
        getSpanEnd(annotation),
        replacementValue
    )
}

private fun SpannableStringBuilder.applySpan(span: Any, annotation: Annotation) {
    setSpan(
        span,
        getSpanStart(annotation),
        getSpanEnd(annotation),
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

private fun SpannableStringBuilder.applyType(annotation: Annotation) {
    when (annotation.value) {
        "bold" -> StyleSpan(Typeface.BOLD)
        "italic" -> StyleSpan(Typeface.ITALIC)
        "bolditalic" -> StyleSpan(Typeface.BOLD_ITALIC)
        "underline" -> UnderlineSpan()
        "bullet" -> BulletSpan()
        else -> null
    }?.let { span ->
        applySpan(span, annotation)
    }
}

private fun SpannableStringBuilder.colorizeAnnotation(annotation: Annotation) {
    applySpan(ForegroundColorSpan(Color.parseColor(annotation.value)), annotation)
}

private fun SpannableStringBuilder.applyUrlAnnotation(annotation: Annotation) {
    applySpan(URLSpan(annotation.value), annotation)
}

private fun SpannableStringBuilder.applyFontAnnotation(context: Context, annotation: Annotation) {
    val type = context.resources.getIdentifier(
        annotation.value, "font", context.packageName
    )
    ResourcesCompat.getFont(context, type)?.let { typeface ->
        applySpan(CustomTypefaceSpan(typeface), annotation)
    }
}
