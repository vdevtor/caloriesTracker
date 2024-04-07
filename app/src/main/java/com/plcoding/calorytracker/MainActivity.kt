package com.plcoding.calorytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onboarding_presentation.gender.GenderScreen
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
                NavHost(
                    navController = navController,
                    startDestination = Route.WELCOME
                ) {
                    composable(route = Route.WELCOME) {
                        WelcomeScreen(onNavigate = navController::navigate)
                    }
                    composable(route = Route.AGE) {

                    }
                    composable(route = Route.GENDER) {
                        GenderScreen(onNavigate = navController::navigate)
                    }
                    composable(route = Route.HEIGHT) {

                    }
                    composable(route = Route.WEIGHT) {

                    }
                    composable(route = Route.NUTRIENTS_GOAL) {

                    }
                    composable(route = Route.ACTIVITY) {

                    }
                    composable(route = Route.GOAL) {

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