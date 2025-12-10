package com.rosafiesta.notification.infrastructure.mappers

import com.rosafiesta.notification.domain.model.DeviceToken
import com.rosafiesta.notification.infrastructure.database.DeviceTokenEntity

fun DeviceTokenEntity.toModel(): DeviceToken {
  return DeviceToken(
    id = id,
    userId = userId,
    token = token,
    platform = platform.toModel(),
    createdAt = createdAt,
  )
}