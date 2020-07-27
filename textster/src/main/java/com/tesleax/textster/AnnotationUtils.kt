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

fun Context.getXmlStyledString(
    @StringRes stringResId: Int,
    annotationOption: AnnotationOption
): CharSequence {
    val xmlText = resources.getText(stringResId)
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
                val replacementValue =
                    annotationOption.replacementList.find { it.first == annotation.value }?.second
                if (replacementValue != null) {
                    spannableString.replaceAnnotation(annotation, replacementValue)
                }
            }
            "color" -> {
                spannableString.colorizeAnnotation(annotation)
            }
            "click" -> {
                spannableString.applyClickable(annotation, annotationOption.clickableList)
            }
            "url" -> {
                spannableString.applyUrlAnnotation(annotation)
            }
            "font" -> {
                spannableString.applyFontAnnotation(this, annotation)
            }
            "custom" -> {
                val customAnnotation =
                    annotationOption.customAnnotations.find { it.first == annotation.value }
                if (customAnnotation != null) {
                    spannableString.applySpan(customAnnotation.second, annotation)
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
    val span: Any? = when (annotation.value) {
        "bold" -> StyleSpan(Typeface.BOLD)
        "underline" -> UnderlineSpan()
        "italic" -> StyleSpan(Typeface.ITALIC)
        "bullet" -> BulletSpan()
        "bolditalic" -> StyleSpan(Typeface.BOLD_ITALIC)
        else -> null
    }
    if (span != null) {
        setSpan(
            span,
            getSpanStart(annotation),
            getSpanEnd(annotation),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}

private fun SpannableStringBuilder.applyClickable(
    annotation: Annotation,
    clickableList: List<Pair<CharSequence, (CharSequence) -> Unit>>
) {
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            val relatedClickableItem = clickableList.find { clickableItem ->
                clickableItem.first == annotation.value
            }
            relatedClickableItem?.second?.invoke(relatedClickableItem.first)
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = false
        }
    }
    applySpan(clickableSpan, annotation)
}

private fun SpannableStringBuilder.colorizeAnnotation(annotation: Annotation) {
    applySpan(ForegroundColorSpan(Color.parseColor(annotation.value)), annotation)
}

private fun SpannableStringBuilder.applyUrlAnnotation(annotation: Annotation) {
    applySpan(URLSpan(annotation.value), annotation)
}

private fun SpannableStringBuilder.applyFontAnnotation(context: Context, annotation: Annotation) {
    val fontName = annotation.value
    val typeface = ResourcesCompat.getFont(
        context,
        context.resources.getIdentifier(fontName, "font", context.packageName)
    )
    if (typeface != null) {
        applySpan(CustomTypefaceSpan(typeface), annotation)
    }
}
