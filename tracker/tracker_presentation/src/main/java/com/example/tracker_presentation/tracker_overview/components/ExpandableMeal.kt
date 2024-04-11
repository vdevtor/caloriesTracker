package com.example.tracker_presentation.tracker_overview.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.tracker_presentation.components.NutrientInfo
import com.example.tracker_presentation.components.UnitDisplay
import com.example.tracker_presentation.tracker_overview.Meal
import com.vitorthemyth.core.util.UiText
import com.vitorthemyth.core_ui.LocalSpacing

@Composable
fun ExpandableMeal(
    meal: Meal,
    content: @Composable () -> Unit,
    onToggleClick: () -> Unit,
    modifier: Modifier
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleClick() }
                .padding(spacing.spaceMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = meal.drawableRes),
                contentDescription = meal.name.asString(context)
            )

            Spacer(modifier = Modifier.width(spacing.spaceMedium))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = meal.name.asString(context),
                        style = MaterialTheme.typography.h3
                    )
                    Icon(
                        imageVector = if (meal.isExpanded) {
                            Icons.Default.KeyboardArrowUp
                        } else {
                            Icons.Default.KeyboardArrowDown
                        },
                        contentDescription = if (meal.isExpanded) {
                            stringResource(id = com.vitorthemyth.core.R.string.collapse)
                        } else {
                            stringResource(id = com.vitorthemyth.core.R.string.extend)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spaceSmall))

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(end = spacing.spaceSmall),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    UnitDisplay(
                        amount = meal.calories,
                        unit = stringResource(id = com.vitorthemyth.core.R.string.kcal),
                        amountTextSize = 25.sp
                    )

                    Spacer(modifier = Modifier.width(spacing.spaceSmall))

                    NutrientInfo(
                        name = stringResource(id = com.vitorthemyth.core.R.string.carbs),
                        amount = meal.carbs,
                        unit = stringResource(id = com.vitorthemyth.core.R.string.grams),
                        amountTextSize = 14.sp,
                        unitTextSize = 12.sp,
                        nameTextStyle = MaterialTheme.typography.body2.copy(
                            fontSize = 12.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(spacing.spaceSmall))

                    NutrientInfo(
                        name = stringResource(id = com.vitorthemyth.core.R.string.protein),
                        amount = meal.protein,
                        unit = stringResource(id = com.vitorthemyth.core.R.string.grams),
                        amountTextSize = 14.sp,
                        unitTextSize = 12.sp,
                        nameTextStyle = MaterialTheme.typography.body2.copy(
                            fontSize = 12.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(spacing.spaceSmall))

                    NutrientInfo(
                        name = stringResource(id = com.vitorthemyth.core.R.string.fat),
                        amount = meal.fat,
                        unit = stringResource(id = com.vitorthemyth.core.R.string.grams),
                        amountTextSize = 14.sp,
                        unitTextSize = 12.sp,
                        nameTextStyle = MaterialTheme.typography.body2.copy(
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        AnimatedVisibility(visible = meal.isExpanded) {
            content()
        }
    }
}

@Preview()
@Composable
fun PreviewExpandableMeal() {
    val spacing = LocalSpacing.current
    ExpandableMeal(
        meal = Meal(
            name = UiText.StringResource(com.vitorthemyth.core.R.string.breakfast),
            drawableRes = com.vitorthemyth.core.R.drawable.ic_burger,
            mealType = com.example.tracker_domain.model.MealType.Breakfast,
            carbs = 2100,
            protein = 9854,
            fat = 1717,
            calories = 9646,
            isExpanded = false
        ),
        content = {},
        onToggleClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.spaceSmall)
    )
}