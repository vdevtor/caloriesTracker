package com.vitorthemyth.core.data.preferences

import android.content.SharedPreferences
import com.vitorthemyth.core.domain.Preferences
import com.vitorthemyth.core.domain.Preferences.Companion.KEY_SHOULD_SHOW_ONBOARDING
import com.vitorthemyth.core.model.ActivityLevel
import com.vitorthemyth.core.model.Gender
import com.vitorthemyth.core.model.GoalType
import com.vitorthemyth.core.model.UserInfo

class DefaultPreferences(
    private val sharedPref: SharedPreferences
) : Preferences {
    override fun saveGender(gender: Gender) {
        sharedPref.edit().putString(
            Preferences.KEY_GENDER,
            gender.name
        ).apply()
    }

    override fun saveGoalType(goal: GoalType) {
        sharedPref.edit().putString(
            Preferences.KEY_GOAL_TYPE,
            goal.name
        ).apply()
    }

    override fun saveActivityLevel(level: ActivityLevel) {
        sharedPref.edit().putString(
            Preferences.KEY_ACTIVITY_LEVEL,
            level.name
        ).apply()
    }

    override fun saveAge(age: Int) {
        sharedPref.edit().putInt(
            Preferences.KEY_AGE,
            age
        ).apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPref.edit().putFloat(
            Preferences.KEY_WEIGHT,
            weight
        ).apply()
    }

    override fun saveHeight(height: Int) {
        sharedPref.edit().putInt(
            Preferences.KEY_HEIGHT,
            height
        ).apply()
    }

    override fun saveCarbRatio(ratio: Float) {
        sharedPref.edit().putFloat(
            Preferences.KEY_CARB_RATION,
            ratio
        ).apply()
    }

    override fun saveProteinRatio(ratio: Float) {
        sharedPref.edit().putFloat(
            Preferences.KEY_PROTEIN_RATION,
            ratio
        ).apply()
    }

    override fun saveFatRatio(ratio: Float) {
        sharedPref.edit().putFloat(
            Preferences.KEY_FAT_RATION,
            ratio
        ).apply()
    }

    override fun loadUserInfo(): UserInfo {
        val age = sharedPref.getInt(Preferences.KEY_AGE, -1)
        val height = sharedPref.getInt(Preferences.KEY_HEIGHT, -1)
        val weight = sharedPref.getFloat(Preferences.KEY_WEIGHT, -1f)
        val carbRatio = sharedPref.getFloat(Preferences.KEY_CARB_RATION, -1f)
        val fatRatio = sharedPref.getFloat(Preferences.KEY_FAT_RATION, -1f)
        val proteinRatio = sharedPref.getFloat(Preferences.KEY_PROTEIN_RATION, -1f)
        val gender = sharedPref.getString(Preferences.KEY_GENDER, null)
        val goalType = sharedPref.getString(Preferences.KEY_GOAL_TYPE, null)
        val activityLevel = sharedPref.getString(Preferences.KEY_ACTIVITY_LEVEL, null)

        return UserInfo(
            gender = Gender.fromString(gender.orEmpty()),
            age = age,
            height = height,
            weight = weight,
            activityLevel = ActivityLevel.fromString(activityLevel.orEmpty()),
            goalType = GoalType.fromString(goalType.orEmpty()),
            carbRatio = carbRatio,
            proteinRatio = proteinRatio,
            fatRatio = fatRatio
        )
    }

    override fun saveShouldShowOnBoarding(shouldShow: Boolean) {
        sharedPref.edit()
            .putBoolean(KEY_SHOULD_SHOW_ONBOARDING, shouldShow)
            .apply()
    }

    override fun loadShouldShowOnBoarding(): Boolean {
        return sharedPref
            .getBoolean(KEY_SHOULD_SHOW_ONBOARDING, true)
    }

}