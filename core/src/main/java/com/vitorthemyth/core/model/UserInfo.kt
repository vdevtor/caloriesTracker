package com.vitorthemyth.core.model

data class UserInfo(
    val gender: Gender,
    val age: Int,
    val height : Int,
    val weight : Float,
    val activityLevel: ActivityLevel,
    val goalType: GoalType,
    val carbRatio : Float,
    val proteinRatio : Float,
    val fatRatio : Float,
)
