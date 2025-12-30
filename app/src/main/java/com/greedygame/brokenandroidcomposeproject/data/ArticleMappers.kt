package com.greedygame.brokenandroidcomposeproject.data

import com.greedygame.brokenandroidcomposeproject.db.ArticleEntity

fun ArticleEntity.toDomain() = Article(
    id = id,
    title = title,
    author = author,
    content = content,
    imageUrl = imageUrl
)

fun Article.toEntity() = ArticleEntity(
    id = id,
    title = title,
    author = author,
    content = content,
    imageUrl = imageUrl
)