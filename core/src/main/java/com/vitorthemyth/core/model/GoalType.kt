package com.vitorthemyth.core.model

sealed class GoalType(val name: String) {
    object LoseWeight: GoalType("lose_weight")
    object KeepWeight : GoalType("keep_weight")
    object GainWeight : GoalType("gain_weight")

    companion object {
        fun fromString(name: String): GoalType {
            return when (name) {
                LoseWeight.name -> LoseWeight
                KeepWeight.name -> KeepWeight
                GainWeight.name -> GainWeight
                else -> LoseWeight
            }
        }
    }
}