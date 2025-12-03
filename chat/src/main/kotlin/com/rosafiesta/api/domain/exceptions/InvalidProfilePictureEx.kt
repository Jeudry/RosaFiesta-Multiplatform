package com.rosafiesta.api.domain.exceptions

class InvalidProfilePictureEx(
  override val message: String? = null
): RuntimeException (message ?: "Invalid profile picture")