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

internal fun SpannableStringBuilder.replaceAnnotation(
    annotation: Annotation,
    replacementValue: CharSequence
) {
    replace(
        getSpanStart(annotation),
        getSpanEnd(annotation),
        replacementValue
    )
}

internal fun SpannableStringBuilder.applySpan(span: Any, annotation: Annotation) {
    setSpan(
        span,
        getSpanStart(annotation),
        getSpanEnd(annotation),
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

internal fun SpannableStringBuilder.applyType(annotation: Annotation) {
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

internal fun SpannableStringBuilder.colorizeAnnotation(annotation: Annotation) {
    applySpan(ForegroundColorSpan(Color.parseColor(annotation.value)), annotation)
}

internal fun SpannableStringBuilder.applyUrlAnnotation(annotation: Annotation) {
    applySpan(URLSpan(annotation.value), annotation)
}

internal fun SpannableStringBuilder.applyFontAnnotation(context: Context, annotation: Annotation) {
    val type = context.resources.getIdentifier(
        annotation.value, "font", context.packageName
    )
    ResourcesCompat.getFont(context, type)?.let { typeface ->
        applySpan(CustomTypefaceSpan(typeface), annotation)
    }
}
