package com.rosafiesta.api.chat.domain.events

import com.rosafiesta.api.core.domain.types.UserId

data class ProfilePictureUpdatedEv(
  val userId: UserId,
  val newUrl: String? = null
)