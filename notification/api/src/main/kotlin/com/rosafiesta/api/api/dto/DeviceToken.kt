package com.rosafiesta.api.api.dto

import com.rosafiesta.api.domain.types.UserId
import java.time.Instant

data class DeviceTokenDto(
  val userId: UserId,
  val token: String,
  val createdAt: Instant
)