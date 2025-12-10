package com.rosafiesta.api.notification.api.dto

import com.rosafiesta.api.core.domain.types.UserId
import java.time.Instant

data class DeviceTokenDto(
  val userId: UserId,
  val token: String,
  val createdAt: Instant
)