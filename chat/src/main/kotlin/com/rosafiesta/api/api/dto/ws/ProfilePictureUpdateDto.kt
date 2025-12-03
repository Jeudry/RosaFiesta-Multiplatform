package com.rosafiesta.api.api.dto.ws

import com.rosafiesta.api.domain.types.UserId

data class ProfilePictureUpdateDto(
  val userId: UserId,
  val newUrl: String? = null
  )