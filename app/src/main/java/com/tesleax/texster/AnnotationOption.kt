package com.tesleax.texster

data class AnnotationOption(
    val replacementList: List<Pair<CharSequence, CharSequence>> = emptyList(),
    val clickableList: List<Pair<CharSequence, (CharSequence) -> Unit>> = emptyList(),
    val customAnnotations: List<Pair<CharSequence, Any>> = emptyList()
)
