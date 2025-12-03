package com.rosafiesta.api.api.mappers

import com.rosafiesta.api.api.dto.DeviceTokenDto
import com.rosafiesta.api.domain.model.DeviceToken

fun DeviceToken.toDto(): DeviceTokenDto {
  return DeviceTokenDto(
    userId = userId,
    token = token,
    createdAt = createdAt
  )
}