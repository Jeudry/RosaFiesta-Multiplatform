package com.rosafiesta.chat.domain.exceptions

class InvalidProfilePictureEx(
  override val message: String? = null
): RuntimeException (message ?: "Invalid profile picture")