package com.gturedi.github.util

inline fun <R> R?.orElse(block: () -> R): R {
    return this ?: block()
}

fun <T> Collection<T>?.isNotNullOrEmpty() =
    this.isNullOrEmpty().not()

inline fun String?.isNotNullOrBlank() =
    this.isNullOrBlank().not()

inline fun String?.isNullOrBlankThen(default: String) =
    if (this.isNullOrBlank()) default
    else this