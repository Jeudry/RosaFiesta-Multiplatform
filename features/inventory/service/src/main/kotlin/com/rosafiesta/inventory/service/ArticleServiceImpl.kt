package com.rosafiesta.inventory.service

import com.rosafiesta.core.domain.types.ArticleId
import com.rosafiesta.inventory.domain.repository.ArticleRepository
import com.rosafiesta.shared.service.dto.ArticleDto
import org.springframework.stereotype.Service
import kotlin.uuid.Uuid

/**
 * Implementation of the ArticleService interface.
 */
@Service
class ArticleServiceImpl(
    private val articleRepository: ArticleRepository
): ArticleService {
    /**
     * Adds a new article based on the provided DTO.
     * @param articleDto The article data transfer object.
     * @return The ID of the newly added article.
     */
    override fun addArticle(articleDto: ArticleDto): ArticleId {
        val articleDomain = articleDto.toDomain()
        articleRepository.save(articleDomain)
        return Uuid.random()
    }
}