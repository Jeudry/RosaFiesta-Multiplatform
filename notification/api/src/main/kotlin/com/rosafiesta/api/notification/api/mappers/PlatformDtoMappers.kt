package com.rosafiesta.api.notification.api.mappers

import com.rosafiesta.api.notification.api.dto.PlatformDto
import com.rosafiesta.api.notification.domain.model.DeviceToken

fun PlatformDto.toDto(): DeviceToken.Platform {
  return when (this) {
    PlatformDto.ANDROID -> DeviceToken.Platform.ANDROID
    PlatformDto.IOS -> DeviceToken.Platform.IOS
    PlatformDto.WEB -> DeviceToken.Platform.WEB
  }
}