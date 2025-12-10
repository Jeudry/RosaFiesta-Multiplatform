package com.rosafiesta.api.notification.infrastructure.mappers

import com.rosafiesta.api.notification.domain.model.DeviceToken
import com.rosafiesta.api.notification.infrastructure.database.DeviceTokenEntity

fun DeviceTokenEntity.toModel(): DeviceToken {
  return DeviceToken(
    id = id,
    userId = userId,
    token = token,
    platform = platform.toModel(),
    createdAt = createdAt,
  )
}