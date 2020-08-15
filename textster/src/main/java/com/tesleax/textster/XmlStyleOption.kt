package com.tesleax.textster

data class XmlStyleOption(
    val replacementList: List<Pair<CharSequence, CharSequence>> = emptyList(),
    val customAnnotations: List<Pair<CharSequence, Any>> = emptyList()
)
