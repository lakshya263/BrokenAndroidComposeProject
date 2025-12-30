package com.greedygame.brokenandroidcomposeproject.ui.navigation

sealed class NavRoutes(val route: String) {
    object News : NavRoutes("news")
    object Detail : NavRoutes("detail/{articleId}") {
        fun createRoute(articleId: Int) = "detail/$articleId"
    }
}