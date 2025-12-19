@file:OptIn(ExperimentalUuidApi::class)

package com.rosafiesta.inventory.api.controllers

import com.rosafiesta.core.api.utils.ValidationUtils
import com.rosafiesta.core.domain.types.ArticleId
import com.rosafiesta.inventory.service.ArticleService
import com.rosafiesta.shared.service.dto.ArticleDto
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.uuid.ExperimentalUuidApi

/**
 * REST controller for managing articles.
 */
@RestController
@RequestMapping("/api/article")
class ArticleController(
  private val articleService: ArticleService
) {
  /**
   * Adds a new article.
   * @param articleDto The article data transfer object.
   * @return ResponseEntity containing the ID of the added article.
   */
  @PreAuthorize("isAuthenticated()")
  @PostMapping("add_article")
  fun addArticle(
    @RequestBody articleDto: ArticleDto
  ): ResponseEntity<ArticleId> {
    ValidationUtils.handleErrors(articleDto)
    
    val articleId = articleService.addArticle(articleDto)
    return ResponseEntity.ok(articleId)
  }
}