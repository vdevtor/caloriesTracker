package com.example.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracker_presentation.tracker_overview.components.DaySelector
import com.example.tracker_presentation.tracker_overview.components.ExpandableMeal
import com.example.tracker_presentation.tracker_overview.components.NutrientsHeader
import com.vitorthemyth.core.util.UiEvent
import com.vitorthemyth.core_ui.LocalSpacing

@Composable
fun TrackerOverViewScreen(
   onNavigate: (UiEvent.Navigate) -> Unit,
   viewModel: TrackerOverViewViewModel = hiltViewModel()
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
         DaySelector(
           date = state.date,
           onPreviousDayClick = {
              viewModel.onEvent(TrackerOverViewEvent.OnPreviousDayClick)
           },
           onNextDayClick = {
              viewModel.onEvent(TrackerOverViewEvent.OnNextDayClick)
           },
           modifier = Modifier
              .fillMaxWidth()
              .padding(spacing.spaceMedium)
         )
         Spacer(modifier = Modifier.height(spacing.spaceMedium))
      }
      items(state.meals){meal ->
         ExpandableMeal(
           meal = meal,
           content = {

           },
           onToggleClick = {
            viewModel.onEvent(TrackerOverViewEvent.OnToggleMealClick(meal))
           },
           modifier = Modifier.fillMaxWidth()
         )
      }
   }
}