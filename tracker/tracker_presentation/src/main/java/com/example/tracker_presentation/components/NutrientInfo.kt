package com.example.tracker_presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun NutrientInfo(
    name: String,
    amount: Int,
    unit: String,
    modifier: Modifier = Modifier,
    amountTextSize: TextUnit = 20.sp,
    amountTextColor: Color = MaterialTheme.colors.onBackground,
    unitTextSize: TextUnit = 14.sp,
    unitTextColor: Color = MaterialTheme.colors.onBackground,
    nameTextStyle: TextStyle = MaterialTheme.typography.body2
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        UnitDisplay(
            amount = amount,
            unit = unit,
            amountTextSize = amountTextSize,
            amountTextColor = amountTextColor,
            unitTextSize = unitTextSize,
            unitTextColor = unitTextColor
        )

        Text(
            text = name,
            color = MaterialTheme.colors.onBackground,
            style = nameTextStyle,
            fontWeight = FontWeight.Light,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview()
@Composable
fun PreviewNutrientInfo() {
    NutrientInfo(
        name = stringResource(id = com.vitorthemyth.core.R.string.protein),
        amount = 45,
        unit = stringResource(id = com.vitorthemyth.core.R.string.grams)
    )
}