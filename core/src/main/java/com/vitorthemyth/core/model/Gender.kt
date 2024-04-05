package com.vitorthemyth.core.model

sealed class Gender(val name: String) {
    object Male : Gender("male")
    object Female : Gender("female")

    companion object {
        fun fromString(name: String): Gender {
            return when (name) {
                Male.name -> Male
                Female.name -> Female
                else -> Female
            }
        }
    }
}