package com.vitorthemyth.core.domain

import com.vitorthemyth.core.model.ActivityLevel
import com.vitorthemyth.core.model.Gender
import com.vitorthemyth.core.model.GoalType
import com.vitorthemyth.core.model.UserInfo

interface Preferences  {
    fun saveGender(gender: Gender)
    fun saveGoalType(goal: GoalType)
    fun saveActivityLevel(level: ActivityLevel)
    fun saveAge(age: Int)
    fun saveWeight(weight: Float)
    fun saveHeight(height: Int)
    fun saveCarbRatio(ratio: Float)
    fun saveProteinRatio(ratio: Float)
    fun saveFatRatio(ratio: Float)

    fun loadUserInfo() : UserInfo

    fun saveShouldShowOnBoarding(shouldShow : Boolean)
    fun loadShouldShowOnBoarding() : Boolean

    companion object{
        const val KEY_GENDER = "gender"
        const val KEY_AGE = "age"
        const val KEY_HEIGHT = "height"
        const val KEY_WEIGHT = "weight"
        const val KEY_GOAL_TYPE = "goal_type"
        const val KEY_PROTEIN_RATION = "protein"
        const val KEY_CARB_RATION = "carb"
        const val KEY_FAT_RATION = "fat"
        const val KEY_ACTIVITY_LEVEL = "activity_level"
        const val KEY_SHOULD_SHOW_ONBOARDING = "should_show_onboarding"
    }
}