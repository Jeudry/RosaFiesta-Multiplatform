package com.rosafiesta.api.infra.mappers

import com.rosafiesta.api.domain.model.DeviceToken
import com.rosafiesta.api.infra.database.DeviceTokenEntity

fun DeviceTokenEntity.toModel(): DeviceToken {
  return DeviceToken(
    id = id,
    userId = userId,
    token = token,
    platform = platform.toModel(),
    createdAt = createdAt,
  )
}