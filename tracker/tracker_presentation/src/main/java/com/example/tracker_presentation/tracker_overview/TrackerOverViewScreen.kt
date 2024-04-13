package com.example.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracker_presentation.tracker_overview.components.AddButton
import com.example.tracker_presentation.tracker_overview.components.DaySelector
import com.example.tracker_presentation.tracker_overview.components.ExpandableMeal
import com.example.tracker_presentation.tracker_overview.components.NutrientsHeader
import com.example.tracker_presentation.tracker_overview.components.TrackedFoodItem
import com.vitorthemyth.core.util.UiEvent
import com.vitorthemyth.core_ui.LocalSpacing
import com.vitorthemyth.tracker_presentation.R

@Composable
fun TrackerOverViewScreen(
   onNavigateToSearch: (String,Int,Int,Int,) -> Unit, viewModel: TrackerOverViewViewModel =
      hiltViewModel()
) {
   val spacing = LocalSpacing.current
   val state = viewModel.state
   val context = LocalContext.current

   LazyColumn(
      modifier = Modifier
         .fillMaxSize()
         .padding(bottom = spacing.spaceMedium)
   ) {
      item {
         NutrientsHeader(state = state)
         DaySelector(date = state.date, onPreviousDayClick = {
            viewModel.onEvent(TrackerOverViewEvent.OnPreviousDayClick)
         }, onNextDayClick = {
            viewModel.onEvent(TrackerOverViewEvent.OnNextDayClick)
         }, modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.spaceMedium)
         )
         Spacer(modifier = Modifier.height(spacing.spaceMedium))
      }
      items(state.meals) { meal ->
         ExpandableMeal(meal = meal, content = {
            Column(
               modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = spacing.spaceSmall)
            ) {
               state.trackedFood.forEach { food ->
                  TrackedFoodItem(trackedFood = food, onDeleteClick = {
                     viewModel.onEvent(
                        TrackerOverViewEvent.OnDeleteTrackedFoodClick(food)
                     )
                  })
                  Spacer(modifier = Modifier.height(spacing.spaceMedium))

               }
               AddButton(
                  text = stringResource(
                     id = com.vitorthemyth.core.R.string.add_meal, meal.name.asString(context)
                  ), onClick = {
                     onNavigateToSearch(
                        meal.mealType.name,
                        state.date.dayOfMonth,
                        state.date.monthValue,
                        state.date.year
                     )
                  }, modifier = Modifier.fillMaxWidth()
               )
            }
         }, onToggleClick = {
            viewModel.onEvent(TrackerOverViewEvent.OnToggleMealClick(meal))
         }, modifier = Modifier.fillMaxWidth()
         )
      }
   }
}