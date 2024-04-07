package com.example.onboarding_domain.use_case

import com.vitorthemyth.core.util.UiText

class ValidateNutrients {

    operator fun invoke(
        carbsRatioText: String,
        proteinsRatioText: String,
        fatRatioText: String
    ): Result {
        val carbsRatio = carbsRatioText.toIntOrNull()
        val proteinsRatio = proteinsRatioText.toIntOrNull()
        val fatRatio = fatRatioText.toIntOrNull()
        if (carbsRatio == null || proteinsRatio == null || fatRatio == null) {
            return Result.Error(
                message = UiText.StringResource(com.vitorthemyth.core.R.string.error_not_100_percent)
            )
        }
        if ((carbsRatio + proteinsRatio + fatRatio) != 100) {
            return Result.Error(
                message = UiText.StringResource(com.vitorthemyth.core.R.string.error_invalid_values)
            )
        }
        return Result.Success(
            carbsRatio / 100f,
            proteinsRatio / 100f,
            fatRatio / 100f
        )
    }

    sealed class Result {
        data class Success(
            val carbsRatio: Float,
            val proteinsRatio: Float,
            val fatRatio: Float
        ) : Result()

        data class Error(val message: UiText) : Result()
    }
}