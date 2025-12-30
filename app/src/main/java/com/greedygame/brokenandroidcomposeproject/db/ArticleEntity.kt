package com.greedygame.brokenandroidcomposeproject.db


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val author: String?,
    val content: String?,
    val imageUrl: String?
)