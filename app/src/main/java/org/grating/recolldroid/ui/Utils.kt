package org.grating.recolldroid.ui

private const val KB: Long = 1024
private const val MB: Long = KB * KB
private const val GB: Long = MB * KB
private const val TB: Long = GB * KB

fun Long.readableFileSize(): String {
    return when {
        this < KB -> "$this b"
        this < MB -> "${this / KB} Kb"
        this < GB -> "${this / MB} Mb"
        this < TB -> "${this / GB} Gb"
        else -> "${this / TB} Tb"
    }
}