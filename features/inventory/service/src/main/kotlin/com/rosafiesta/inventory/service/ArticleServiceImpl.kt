@file:OptIn(ExperimentalUuidApi::class)

package com.rosafiesta.inventory.service

import com.rosafiesta.core.domain.types.ArticleId
import org.springframework.stereotype.Service
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Implementation of the ArticleService interface.
 */
@Service
class ArticleServiceImpl : ArticleService {
    /**
     * Adds a new article based on the provided DTO.
     * @param articleDto The article data transfer object.
     * @return The ID of the newly added article.
     */
    override fun addArticle(articleDto: ArticleDto): ArticleId {
      
        return Uuid.random()
    }
}