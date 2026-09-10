package com.example.tvshowapp.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tvshowapp.model.RetrofitInstance
import com.example.tvshowapp.model.ShowRepository
import com.example.tvshowapp.viewmodel.DetailViewModel
import com.example.tvshowapp.viewmodel.DetailViewModelFactory
import com.example.tvshowapp.viewmodel.HomeViewModel
import com.example.tvshowapp.viewmodel.HomeViewModelFactory

object Routes {
    const val HOME = "home"
    const val DETAIL = "detail/{showId}"

    fun detail(showId: Int): String {
        return "detail/$showId"
    }
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {

        composable(Routes.HOME) {
            val viewModel: HomeViewModel = viewModel(
                factory = HomeViewModelFactory(
                    ShowRepository(
                        RetrofitInstance.api
                    )
                )
            )

            HomeScreen(
                uiState = viewModel.uiState,

                onShowClick = { showId ->
                    navController.navigate(
                        Routes.detail(showId)
                    )
                },

                onRetry = {
                    viewModel.loadShows()
                }
            )
        }

        composable(
            route = Routes.DETAIL
        ) { backStackEntry ->

            val showId = backStackEntry
                .arguments
                ?.getString("showId")
                ?.toIntOrNull()

            if (showId != null) {

                val viewModel: DetailViewModel = viewModel(
                    factory = DetailViewModelFactory(
                        repository = ShowRepository(
                            RetrofitInstance.api
                        ),
                        showId = showId
                    )
                )

                DetailScreen(
                    uiState = viewModel.uiState,

                    onRetry = {
                        viewModel.loadShow()
                    },

                    onBack = {
                        navController.popBackStack()
                    },

                )
            }
        }
    }
}
