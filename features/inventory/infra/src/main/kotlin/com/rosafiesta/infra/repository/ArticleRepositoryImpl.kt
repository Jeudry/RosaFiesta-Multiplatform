package com.rosafiesta.infra.repository

import com.rosafiesta.core.domain.types.ArticleId
import com.rosafiesta.inventory.domain.model.Article
import com.rosafiesta.inventory.domain.repository.ArticleRepository
import com.rosafiesta.inventory.infra.db.entities.ArticleEntity
import com.rosafiesta.inventory.infra.db.mappers.fromDomain
import com.rosafiesta.inventory.infra.db.mappers.toDomain
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * JPA implementation of ArticleRepository.
 */
@Repository
class ArticleRepositoryImpl(
    private val jpaRepository: ArticleJpaRepository
) : ArticleRepository {

    /**
     * Finds an article by its ID.
     * @param id The article ID.
     * @return The article if found, null otherwise.
     */
    override fun findById(id: ArticleId): Article? {
        return jpaRepository.findById(id).orElse(null)?.toDomain()
    }

    /**
     * Saves an article.
     * @param article The article to save.
     * @return The saved article.
     */
    override fun save(article: Article): Article {
        val entity = article.fromDomain()
        val savedEntity = jpaRepository.save(entity)
        return savedEntity.toDomain()
    }

    /**
     * Deletes an article by its ID.
     * @param id The article ID.
     */
    override fun deleteById(id: ArticleId) {
        jpaRepository.deleteById(id)
    }
}

/**
 * JPA repository interface for ArticleEntity.
 */
interface ArticleJpaRepository : JpaRepository<ArticleEntity, ArticleId>