package com.example.tracker_presentation.tracker_overview.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.tracker_presentation.tracker_overview.TrackerOverViewState
import com.vitorthemyth.core_ui.LocalSpacing

@Composable
fun NutrientsHeader(
    state: TrackerOverViewState,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val animatedCalorieCount = animateIntAsState(
        targetValue = state.totalCalories, label = ""
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomStart = 50.dp,
                    bottomEnd = 50.dp
                )
            )
            .background(MaterialTheme.colors.primary)
            .padding(
                horizontal = spacing.spaceLarge,
                vertical = spacing.spaceExtraLarge
            )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

        }
    }
}