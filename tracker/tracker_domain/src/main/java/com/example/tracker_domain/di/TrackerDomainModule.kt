package com.example.tracker_domain.di

import com.example.tracker_domain.repository.TrackerRepository
import com.example.tracker_domain.use_case.CalculateMealsNutrients
import com.example.tracker_domain.use_case.DeleteTrackedFood
import com.example.tracker_domain.use_case.GetFoodsForDate
import com.example.tracker_domain.use_case.SearchFood
import com.example.tracker_domain.use_case.TrackFood
import com.example.tracker_domain.use_case.TrackerUseCases
import com.vitorthemyth.core.domain.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @ViewModelScoped
    @Provides
    fun providesTrackerUseCases(
        repository: TrackerRepository,
        preferences: Preferences
    ): TrackerUseCases {
        return TrackerUseCases(
            trackFood = TrackFood(trackerRepository = repository),
            searchFood = SearchFood(trackerRepository = repository),
            getFoodsForDate = GetFoodsForDate(trackerRepository = repository),
            deleteTrackedFood = DeleteTrackedFood(
                trackerRepository = repository
            ),
            calculateMealsNutrients = CalculateMealsNutrients(
                preferences = preferences
            )
        )
    }
}