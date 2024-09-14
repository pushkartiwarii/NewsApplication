package com.example.newsapplication.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsapplication.ui.screens.ArticleDetailScreen
import com.example.newsapplication.ui.screens.NewsListScreen
import com.example.newsapplication.ui.screens.NewsViewModel

@Composable
fun NewsNavHost(navController: NavHostController, viewModel: NewsViewModel) {
    NavHost(navController = navController, startDestination = "newsList") {
        composable("newsList") {
            NewsListScreen(viewModel = viewModel, onArticleClick = { article ->
                navController.navigate("articleDetail/${article.title}/${article.description}/${article.urlToImage}")
            })
        }
        composable("articleDetail/{title}/{description}/{urlToImage}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")
            val description = backStackEntry.arguments?.getString("description")
            val urlToImage = backStackEntry.arguments?.getString("urlToImage")
            ArticleDetailScreen(title = title, description = description, urlToImage = urlToImage)
        }
    }
}