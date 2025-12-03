package com.rosafiesta.api.domain.events

import com.rosafiesta.api.domain.types.UserId

data class ProfilePictureUpdatedEv(
  val userId: UserId,
  val newUrl: String? = null
)