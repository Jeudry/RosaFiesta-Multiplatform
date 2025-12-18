package com.rosafiesta.inventory.infra.repository

import com.rosafiesta.core.domain.types.ArticleId
import com.rosafiesta.inventory.domain.repository.ArticleRepository
import com.rosafiesta.inventory.infra.db.entities.ArticleEntity
import com.rosafiesta.shared.inventory.model.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/// <summary>JPA implementation of ArticleRepository.</summary>
@Repository
class JpaArticleRepository(
    private val jpaRepository: ArticleJpaRepository
) : ArticleRepository {

    /// <summary>Finds an article by its ID.</summary>
    /// <param name="id">The article ID.</param>
    /// <returns>The article if found, null otherwise.</returns>
    override fun findById(id: ArticleId): Article? {
        return jpaRepository.findById(id).orElse(null)?.toDomain()
    }

    /// <summary>Saves an article.</summary>
    /// <param name="article">The article to save.</param>
    /// <returns>The saved article.</returns>
    override fun save(article: Article): Article {
        val entity = ArticleEntity.fromDomain(article)
        val savedEntity = jpaRepository.save(entity)
        return savedEntity.toDomain()
    }

    /// <summary>Deletes an article by its ID.</summary>
    /// <param name="id">The article ID.</param>
    override fun deleteById(id: ArticleId) {
        jpaRepository.deleteById(id)
    }
}

/// <summary>JPA repository interface for ArticleEntity.</summary>
interface ArticleJpaRepository : JpaRepository<ArticleEntity, ArticleId>