package com.greedygame.brokenandroidcomposeproject.data

import android.content.Context
import com.greedygame.brokenandroidcomposeproject.network.ApiClient
import com.greedygame.brokenandroidcomposeproject.BuildConfig
import com.greedygame.brokenandroidcomposeproject.db.ArticleDao
import com.greedygame.brokenandroidcomposeproject.db.DatabaseProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Article(
    val id: Int = 0,
    val title: String,
    val author: String?,
    val content: String?,
    val imageUrl: String?
)

object BrokenRepository {

    private lateinit var articleDao: ArticleDao

    fun init(context: Context) {
        articleDao = DatabaseProvider
            .getDatabase(context)
            .articleDao()
    }

    suspend fun fetchArticles(): List<Article> =
        withContext(Dispatchers.IO) {

            val cached = articleDao.getAllArticles()
            if (cached.isNotEmpty()) {
                return@withContext cached.map {it.toDomain()}
            }

            val response = ApiClient.api.getArticles(
                apiKey = BuildConfig.NEWS_API_KEY
            )

            val articles = response.articles.mapIndexed { index, dto ->
                Article(
                    id = index,
                    title = dto.title.orEmpty(),
                    author = dto.author,
                    content = null,
                    imageUrl = null
                )
            }

            val entities = articles.map {it.toEntity()}

            articleDao.insertArticles(entities)
            articles
        }

    suspend fun updateArticle(article: Article) {
        withContext(Dispatchers.IO) {
            articleDao.updateArticle(article.toEntity())
        }
    }
} 