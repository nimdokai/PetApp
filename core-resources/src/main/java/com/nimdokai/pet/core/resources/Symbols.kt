package com.nimdokai.pet.core.resources

sealed class UnicodeDegreeSings(
    open val value: String,
) {
    object Celsius : UnicodeDegreeSings("\u2103")
    object Fahrenheit : UnicodeDegreeSings("\u2109")

    override fun toString(): String {
        return value
    }
}