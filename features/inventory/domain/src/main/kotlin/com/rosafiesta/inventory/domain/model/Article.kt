@file:OptIn(ExperimentalUuidApi::class)

package com.rosafiesta.inventory.domain.model

import com.rosafiesta.core.domain.types.ArticleId
import kotlin.uuid.ExperimentalUuidApi

data class Article(
  val id: ArticleId,
  val name: String,
  val code: String,
) {
  companion object {
    const val CODE_MIN_LENGTH = 3
    const val CODE_MAX_LENGTH = 10
  }
  
  init {
    require(isNameValid()) { "Article name cannot be blank" }
    require(isCodeValid(code)) { "Invalid article code: must be $CODE_MIN_LENGTH-$CODE_MAX_LENGTH alphanumeric characters" }
  }
  
  fun isCodeValid(code: String): Boolean {
    val lengthValid = code.length in CODE_MIN_LENGTH..CODE_MAX_LENGTH
    val formatValid = code.all { it.isLetterOrDigit() }
    return lengthValid && formatValid
  }
  
  fun isNameValid(): Boolean = name.isNotBlank()
}