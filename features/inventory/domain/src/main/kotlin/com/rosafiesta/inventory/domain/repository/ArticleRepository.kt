package com.rosafiesta.inventory.domain.repository

import com.rosafiesta.core.domain.types.ArticleId
import com.rosafiesta.shared.inventory.model.Article

/// <summary>Repository interface for Article domain operations.</summary>
interface ArticleRepository {
    /// <summary>Finds an article by its ID.</summary>
    /// <param name="id">The article ID.</param>
    /// <returns>The article if found, null otherwise.</returns>
    fun findById(id: ArticleId): Article?

    /// <summary>Saves an article.</summary>
    /// <param name="article">The article to save.</param>
    /// <returns>The saved article.</returns>
    fun save(article: Article): Article

    /// <summary>Deletes an article by its ID.</summary>
    /// <param name="id">The article ID.</param>
    fun deleteById(id: ArticleId)
}