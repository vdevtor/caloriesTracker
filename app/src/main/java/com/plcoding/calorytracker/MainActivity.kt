package com.plcoding.calorytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.onboarding_presentation.activity_level.ActivityLevelScreen
import com.example.onboarding_presentation.age.AgeScreen
import com.example.onboarding_presentation.gender.GenderScreen
import com.example.onboarding_presentation.goal.GoalScreen
import com.example.onboarding_presentation.height.HeightScreen
import com.example.onboarding_presentation.nutrients_goal.NutrientGoalScreen
import com.example.onboarding_presentation.weight.WeightScreen
import com.example.onboarding_presentation.welcome.WelcomeScreen
import com.example.tracker_presentation.search.SearchScreen
import com.example.tracker_presentation.tracker_overview.TrackerOverViewScreen
import com.plcoding.calorytracker.ui.theme.CaloryTrackerTheme
import com.vitorthemyth.core.domain.Preferences
import com.plcoding.calorytracker.navigation.Route
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

   @Inject
   lateinit var preferences: Preferences
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val shouldShowOnboarding = preferences.loadShouldShowOnBoarding()
      setContent {
         CaloryTrackerTheme {
            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState()

            Scaffold(
               scaffoldState = scaffoldState, modifier = Modifier.fillMaxSize()
            ) { padding ->
               padding.toString()
               NavHost(
                  navController = navController, startDestination = if (shouldShowOnboarding) {
                     Route.WELCOME
                  } else Route.TRACKER_OVERVIEW
               ) {
                  composable(route = Route.WELCOME) {
                     WelcomeScreen(onNextClick = {
                        navController.navigate(Route.GENDER)
                     })
                  }
                  composable(route = Route.AGE) {
                     AgeScreen(
                        onNextClick = {
                           navController.navigate(Route.HEIGHT)
                        }, scaffoldState = scaffoldState
                     )
                  }
                  composable(route = Route.GENDER) {
                     GenderScreen(onNextClick = {
                        navController.navigate(Route.AGE)
                     })
                  }
                  composable(route = Route.HEIGHT) {
                     HeightScreen(
                        onNextClick = {
                           navController.navigate(Route.WEIGHT)
                        }, scaffoldState = scaffoldState
                     )
                  }
                  composable(route = Route.WEIGHT) {
                     WeightScreen(
                        onNextClick = {
                           navController.navigate(Route.ACTIVITY)
                        }, scaffoldState = scaffoldState
                     )
                  }
                  composable(route = Route.NUTRIENTS_GOAL) {
                     NutrientGoalScreen(
                        onNextClick = {
                           navController.navigate(Route.TRACKER_OVERVIEW)
                        }, scaffoldState = scaffoldState
                     )
                  }
                  composable(route = Route.ACTIVITY) {
                     ActivityLevelScreen(onNextClick = {
                        navController.navigate(Route.GOAL)
                     })
                  }
                  composable(route = Route.GOAL) {
                     GoalScreen(onNextClick = {
                        navController.navigate(Route.NUTRIENTS_GOAL)
                     })
                  }
                  composable(route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                             arguments = listOf(navArgument("mealName") {
                                type = NavType.StringType
                             }, navArgument("dayOfMonth") {
                                type = NavType.IntType
                             }, navArgument("month") {
                                type = NavType.IntType
                             }, navArgument("year") {
                                type = NavType.IntType
                             })) {
                     val mealName = it.arguments?.getString("mealName")!!
                     val dayOfMonth = it.arguments?.getInt("dayOfMonth")!!
                     val month = it.arguments?.getInt("month")!!
                     val year = it.arguments?.getInt("year")!!

                     SearchScreen(scaffoldState = scaffoldState,
                                  mealName = mealName,
                                  dayOfMonth = dayOfMonth,
                                  month = month,
                                  year = year,
                                  onNavigateUp = {
                                     navController.navigateUp()
                                  })
                  }

                  composable(route = Route.TRACKER_OVERVIEW) {
                     TrackerOverViewScreen(onNavigateToSearch = { mealName, day, month, year ->
                        navController.navigate(
                           Route.SEARCH + "/$mealName" + "/$day" + "/$month" + "/$year"
                        )
                     })
                  }
               }
            }
         }
      }
   }
}