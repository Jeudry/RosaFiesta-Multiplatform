package com.rosafiesta.api.chat.api.dto.ws

import com.rosafiesta.api.core.domain.types.UserId

data class ProfilePictureUpdateDto(
  val userId: UserId,
  val newUrl: String? = null
  )