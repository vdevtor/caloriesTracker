package com.example.tracker_presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracker_domain.model.MealType
import com.example.tracker_presentation.search.components.SearchTextField
import com.example.tracker_presentation.search.components.TrackableFoodItem
import com.example.tracker_presentation.util.toMealTypeTranslated
import com.vitorthemyth.core.util.UiEvent
import com.vitorthemyth.core_ui.LocalSpacing
import java.time.LocalDate

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    scaffoldState: ScaffoldState,
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    onNavigateUp: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current
    val keyBoardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = keyBoardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState
                        .showSnackbar(
                            message = event.message.asString(context)
                        )
                    keyBoardController?.hide()
                }

                is UiEvent.NavigateUp -> {
                    onNavigateUp()
                }

                else -> Unit
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {
        Text(
            text = stringResource(
                id = com.vitorthemyth.core.R.string.add_meal,
                mealName.toMealTypeTranslated(context)
            ),
            style = MaterialTheme.typography.h2
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        SearchTextField(
            text = state.query,
            onValueChanged = {
                viewModel.onEvent(
                    SearchEvent.OnQueryChanged(
                        query = it
                    )
                )
            },
            shouldShowHint = state.isHintVisible,
            onSearch = {
                keyBoardController?.hide()
                viewModel.onEvent(SearchEvent.OnSearch)
            },
            onFocusChanges = {
                viewModel.onEvent(SearchEvent.OnSearchFocusChanged(isFocused = it.isFocused))
            }
        )

        Spacer(modifier = Modifier.height(spacing.spaceMedium))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.trackableFoods) { food ->
                TrackableFoodItem(
                    trackableFoodUiState = food,
                    onClick = {
                        viewModel.onEvent(SearchEvent.OnToggleTrackableFood(food.food))
                    },
                    onAmountChange = {
                        viewModel.onEvent(
                            SearchEvent.OnAmountFoodChanged(
                                amount = it, food = food.food
                            )
                        )
                    },
                    onTrack = {
                        keyBoardController?.hide()
                        viewModel.onEvent(
                            SearchEvent.OnTrackFoodClick(
                                food = food.food,
                                mealType = MealType.fromString(mealName),
                                date = LocalDate.of(year, month, dayOfMonth)
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when{
            state.isSearching-> CircularProgressIndicator()
            state.trackableFoods.isEmpty()-> {
                Text(
                    text = stringResource(id = com.vitorthemyth.core.R.string.no_results),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}