package com.rosafiesta.inventory.infra.db.mappers

import com.rosafiesta.inventory.infra.db.entities.ArticleEntity
import com.rosafiesta.inventory.domain.model.Article

/**
 * Converts this entity to a domain Article.
 */
fun ArticleEntity.toDomain(): Article {
    return Article(id, name, code)
}

/**
 * Creates an ArticleEntity from this domain Article.
 */
fun Article.fromDomain(): ArticleEntity {
    return ArticleEntity(id, name, code)
}