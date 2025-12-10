package com.rosafiesta.chat.domain.events

import com.rosafiesta.core.domain.types.UserId

data class ProfilePictureUpdatedEv(
  val userId: UserId,
  val newUrl: String? = null
)