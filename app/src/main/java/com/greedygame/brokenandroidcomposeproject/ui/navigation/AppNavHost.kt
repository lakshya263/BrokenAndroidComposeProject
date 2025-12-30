package com.greedygame.brokenandroidcomposeproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.greedygame.brokenandroidcomposeproject.ui.DetailScreen
import com.greedygame.brokenandroidcomposeproject.ui.NewsScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.News.route,
        modifier = modifier
    ) {
        composable(NavRoutes.News.route) {
            NewsScreen(
                onArticleClick = { articleId ->
                    navController.navigate(
                        NavRoutes.Detail.createRoute(articleId)
                    )
                }
            )
        }

        composable(
            route = NavRoutes.Detail.route,
            arguments = listOf(
                navArgument("articleId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val articleId =
                backStackEntry.arguments?.getInt("articleId") ?: 0

            DetailScreen(articleId = articleId)
        }
    }
}