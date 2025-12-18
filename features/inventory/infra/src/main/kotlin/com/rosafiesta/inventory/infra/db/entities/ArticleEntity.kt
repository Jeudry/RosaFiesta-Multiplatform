package com.rosafiesta.inventory.infra.db.entities

import com.rosafiesta.core.domain.types.ArticleId
import com.rosafiesta.inventory.domain.model.Article.Companion.CODE_MAX_LENGTH
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
}