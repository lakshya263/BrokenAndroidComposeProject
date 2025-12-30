package com.greedygame.brokenandroidcomposeproject.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.greedygame.brokenandroidcomposeproject.data.Article
import com.greedygame.brokenandroidcomposeproject.data.BrokenRepository

@Composable
fun NewsScreen(
    onArticleClick: (Int) -> Unit
) {
    var articles by remember { mutableStateOf<List<Article>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val result = BrokenRepository.fetchArticles()
            articles = result
        } catch (e: Exception) {
            error = e.message
        } finally {
            loading = false
        }
    }

    when {
        loading -> {
            CircularProgressIndicator()
        }

        error != null -> {
            Text(text = "Error: $error")
        }

        articles.isEmpty() -> {
            Text(text = "No articles found")
        }

        else -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(articles) { article ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onArticleClick(article.id)
                            }
                            .padding(16.dp)
                    ) {
                        Text(text = article.title)
                        Text(text = article.author ?: "no author")
                    }
                }
            }
        }
    }
}