package com.rosafiesta.api.chat.domain.exceptions

class StorageEx(
  override val message: String? = null
): RuntimeException (message ?: "Unable to store file")