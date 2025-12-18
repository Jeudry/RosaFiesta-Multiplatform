package com.rosafiesta.inventory.service

import com.rosafiesta.inventory.domain.model.Article
import com.rosafiesta.shared.service.dto.ArticleDto
import kotlin.uuid.Uuid

/**
 * Converts this DTO to a domain Article.
 */
fun ArticleDto.toDomain(): Article {
    return Article(Uuid.parse(id), name, code)
}