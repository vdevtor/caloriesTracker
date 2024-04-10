package com.example.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
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
    ){
        item { 
            NutrientsHeader(state = state)
        }
    }
}