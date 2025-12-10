package com.rosafiesta.chat.api.dto.ws

import com.rosafiesta.core.domain.types.UserId

data class ProfilePictureUpdateDto(
  val userId: UserId,
  val newUrl: String? = null
  )