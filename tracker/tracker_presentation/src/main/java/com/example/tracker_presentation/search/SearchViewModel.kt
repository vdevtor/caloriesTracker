package com.example.tracker_presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tracker_domain.use_case.TrackerUseCases
import com.vitorthemyth.core.domain.use_case.FilterOutDigits
import com.vitorthemyth.core.util.UiEvent
import com.vitorthemyth.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val filterOutDigits: FilterOutDigits

) : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryChanged -> {
                state = state.copy(query = event.query)
            }

            is SearchEvent.OnTrackFoodClick -> {
                trackFood(event)
            }

            is SearchEvent.OnSearch -> {
                executeSearch()
            }

            is SearchEvent.OnToggleTrackableFood -> {
                state = state.copy(
                    trackableFoods = state.trackableFoods.map {
                        if (it.food == event.food) {
                            it.copy(isExpanded = !it.isExpanded)
                        } else it
                    }
                )
            }

            is SearchEvent.OnAmountFoodChanged -> {
                state = state.copy(
                    trackableFoods = state.trackableFoods.map {
                        if (it.food == event.food) {
                            it.copy(amount = filterOutDigits(event.amount))
                        } else it
                    }
                )
            }

            is SearchEvent.OnSearchFocusChanged -> {
                state = state.copy(isHintVisible = !event.isFocused && state.query.isBlank())
            }
        }
    }

    private fun executeSearch() {
        viewModelScope.launch {
            state = state.copy(
                isSearching = true,
                trackableFoods = emptyList()
            )

            trackerUseCases.searchFood(
                query = state.query
            ).onSuccess { foods ->
                state = state.copy(
                    trackableFoods = foods.map {
                        TrackableFoodUiState(it)
                    },
                    isSearching = false,
                    query = ""
                )
            }.onFailure {
                state = state.copy(
                    isSearching = false
                )
                _uiEvent.send(
                    UiEvent.ShowSnackBar(
                        message = UiText.StringResource(com.vitorthemyth.core.R.string.error_something_went_wrong)
                    )
                )
            }
        }
    }

    private fun trackFood(event: SearchEvent.OnTrackFoodClick) {
        viewModelScope.launch {
            val uiState = state.trackableFoods.find { it.food == event.food }
            trackerUseCases.trackFood(
                food = uiState?.food ?: return@launch,
                amount = uiState.amount.toIntOrNull() ?: return@launch,
                mealType = event.mealType,
                date = event.date
            )

            _uiEvent.send(UiEvent.NavigateUp)
        }
    }
}