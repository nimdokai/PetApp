package com.nimdokai.pet.core.data.model

sealed class Temperature(
    open val value: Double,
){
    data class Celsius(
        override val value: Double,
    ) : Temperature(value)

    data class Fahrenheit(
        override val value: Double,
    ) : Temperature(value)

}