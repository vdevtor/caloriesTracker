package com.example.tracker_presentation.tracker_overview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.tracker_domain.model.TrackedFood
import com.example.tracker_presentation.components.NutrientInfo
import com.vitorthemyth.core_ui.LocalSpacing

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TrackedFoodItem(
    trackedFood: TrackedFood,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current

    Row(
        modifier = modifier
           .clip(RoundedCornerShape(5.dp))
           .padding(spacing.spaceExtraSmall)
           .shadow(
              elevation = 1.dp,
              shape = RoundedCornerShape(5.dp)
           )
           .background(MaterialTheme.colors.surface)
           .padding(end = spacing.spaceMedium)
           .height(100.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(data = trackedFood.imageUrl,
                builder = {
                    crossfade(true)
                    error(com.vitorthemyth.core.R.drawable.ic_burger)
                }),
            contentDescription = trackedFood.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
               .fillMaxHeight()
               .aspectRatio(1f)
               .clip(
                  RoundedCornerShape(
                     topStart = 5.dp,
                     bottomStart = 5.dp
                  )
               )
        )
        Spacer(modifier = Modifier.width(spacing.spaceMedium))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = trackedFood.name,
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
            Text(
                text = stringResource(
                    id = com.vitorthemyth.core.R.string.nutrient_info,
                    trackedFood.amount,
                    trackedFood.calories
                ),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {

                NutrientInfo(
                    name = stringResource(id = com.vitorthemyth.core.R.string.carbs),
                    amount = trackedFood.carbs,
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
                    amount = trackedFood.protein,
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
                    amount = trackedFood.fat,
                    unit = stringResource(id = com.vitorthemyth.core.R.string.grams),
                    amountTextSize = 14.sp,
                    unitTextSize = 12.sp,
                    nameTextStyle = MaterialTheme.typography.body2.copy(
                        fontSize = 12.sp
                    )
                )
            }
        }
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(id = com.vitorthemyth.core.R.string.delete),
            modifier = Modifier
                .clickable {
                    onDeleteClick()
                }
                .align(Alignment.Top)
                .padding(top = spacing.spaceSmall)
        )
    }
}