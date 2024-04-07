package com.plcoding.calorytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onboarding_presentation.activity_level.ActivityLevelScreen
import com.example.onboarding_presentation.age.AgeScreen
import com.example.onboarding_presentation.gender.GenderScreen
import com.example.onboarding_presentation.goal.GoalScreen
import com.example.onboarding_presentation.height.HeightScreen
import com.example.onboarding_presentation.weight.WeightScreen
import com.example.onboarding_presentation.welcome.WelcomeScreen
import com.plcoding.calorytracker.navigation.navigate
import com.plcoding.calorytracker.ui.theme.CaloryTrackerTheme
import com.vitorthemyth.core.navigation.Route
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloryTrackerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    scaffoldState = scaffoldState,
                    modifier = Modifier.fillMaxSize()
                ) { padding ->
                    padding.toString()
                    NavHost(
                        navController = navController,
                        startDestination = Route.WELCOME
                    ) {
                        composable(route = Route.WELCOME) {
                            WelcomeScreen(onNavigate = navController::navigate)
                        }
                        composable(route = Route.AGE) {
                            AgeScreen(onNavigate = navController::navigate,scaffoldState = scaffoldState)
                        }
                        composable(route = Route.GENDER) {
                            GenderScreen(onNavigate = navController::navigate)
                        }
                        composable(route = Route.HEIGHT) {
                            HeightScreen(onNavigate = navController::navigate,scaffoldState = scaffoldState)
                        }
                        composable(route = Route.WEIGHT) {
                            WeightScreen(onNavigate = navController::navigate,scaffoldState = scaffoldState)
                        }
                        composable(route = Route.NUTRIENTS_GOAL) {
    
                        }
                        composable(route = Route.ACTIVITY) {
                            ActivityLevelScreen(onNavigate = navController::navigate)
                        }
                        composable(route = Route.GOAL) {
                            GoalScreen(onNavigate = navController::navigate)
                        }
                        composable(route = Route.SEARCH) {

                        }

                        composable(route = Route.TRACKER_OVERVIEW) {

                        }
                    }
                }
            }
        }
    }
}