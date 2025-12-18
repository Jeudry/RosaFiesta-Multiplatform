@file:OptIn(ExperimentalUuidApi::class)

package com.rosafiesta.inventory.service

import com.rosafiesta.core.domain.types.ArticleId
import com.rosafiesta.shared.service.dto.ArticleDto
import kotlin.uuid.ExperimentalUuidApi

/**
 * Service interface for managing articles.
 */
interface ArticleService {
    /**
     * Adds a new article based on the provided DTO.
     * @param articleDto The article data transfer object.
     * @return The ID of the newly added article.
     */
    fun addArticle(articleDto: ArticleDto): ArticleId
}