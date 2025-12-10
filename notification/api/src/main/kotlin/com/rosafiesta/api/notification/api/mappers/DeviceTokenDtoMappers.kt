package com.rosafiesta.api.notification.api.mappers

import com.rosafiesta.api.notification.api.dto.DeviceTokenDto
import com.rosafiesta.api.notification.domain.model.DeviceToken

fun DeviceToken.toDto(): DeviceTokenDto {
  return DeviceTokenDto(
    userId = userId,
    token = token,
    createdAt = createdAt
  )
}