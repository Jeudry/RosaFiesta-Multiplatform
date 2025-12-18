package com.rosafiesta.inventory.infra.db.entities

import com.rosafiesta.core.domain.types.ArticleId
import com.rosafiesta.shared.inventory.model.Article
import com.rosafiesta.shared.inventory.model.Article.Companion.CODE_MAX_LENGTH
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

/// <summary>JPA entity for Article.</summary>
@Entity
@Table(
    name = "articles",
    schema = "inventory_service",
    indexes = []
)
class ArticleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: ArticleId,
    @Column(nullable = false, length = 255)
    var name: String,
    @Column(nullable = false, length = CODE_MAX_LENGTH)
    var code: String,
) {
    /// <summary>Converts this entity to a domain Article.</summary>
    /// <returns>The domain Article.</returns>
    fun toDomain(): Article {
        return Article(id, name, code)
    }

    companion object {
        /// <summary>Creates an ArticleEntity from a domain Article.</summary>
        /// <param name="article">The domain Article.</param>
        /// <returns>The ArticleEntity.</returns>
        fun fromDomain(article: Article): ArticleEntity {
            return ArticleEntity(article.id, article.name, article.code)
        }
    }
}