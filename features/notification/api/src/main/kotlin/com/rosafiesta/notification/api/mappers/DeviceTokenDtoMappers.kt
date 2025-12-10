package com.rosafiesta.notification.api.mappers

import com.rosafiesta.notification.api.dto.DeviceTokenDto
import com.rosafiesta.notification.domain.model.DeviceToken

fun DeviceToken.toDto(): DeviceTokenDto {
  return DeviceTokenDto(
    userId = userId,
    token = token,
    createdAt = createdAt
  )
}