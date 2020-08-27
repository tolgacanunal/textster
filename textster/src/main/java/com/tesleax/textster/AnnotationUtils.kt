package com.tesleax.textster

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.*
import android.text.Annotation
import android.text.style.*
import androidx.core.content.res.ResourcesCompat

fun SpannableStringBuilder.replaceAnnotation(
    annotation: Annotation,
    replacementValue: CharSequence
) {
    replace(getSpanStart(annotation), getSpanEnd(annotation), replacementValue)
}

fun SpannableStringBuilder.applySpan(span: Any, annotation: Annotation) {
    setSpan(
        span,
        getSpanStart(annotation),
        getSpanEnd(annotation),
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

fun SpannableStringBuilder.applyType(annotation: Annotation) {
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

fun SpannableStringBuilder.colorizeAnnotation(annotation: Annotation) {
    applySpan(ForegroundColorSpan(Color.parseColor(annotation.value)), annotation)
}

fun SpannableStringBuilder.applyFontAnnotation(context: Context, annotation: Annotation) {
    val type = context.resources.getIdentifier(
        annotation.value, "font", context.packageName
    )
    ResourcesCompat.getFont(context, type)?.let { typeface ->
        applySpan(CustomTypefaceSpan(typeface), annotation)
    }
}
