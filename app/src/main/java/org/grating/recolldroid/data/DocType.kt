package org.grating.recolldroid.data

import org.grating.recolldroid.R

enum class DocType(val text: String) {
    HTML("text/html"),
    PDF("application/pdf"),
    DJVU("image/vnd.djvu"),
    UNKNOWN("???");

    val typeIcon: Int
        get() = when(this) {
            HTML -> R.drawable.html
            PDF -> R.drawable.pdf
            DJVU -> R.drawable.image
            UNKNOWN -> R.drawable.unknown_file_type
        }

    companion object {
        val BY_TEXT = DocType.entries.associateBy({ it.text }, { it }) // <3
    }
}