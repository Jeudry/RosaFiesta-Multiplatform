package com.rosafiesta.notification.api.mappers

import com.rosafiesta.notification.api.dto.PlatformDto
import com.rosafiesta.notification.domain.model.DeviceToken

fun PlatformDto.toDto(): DeviceToken.Platform {
  return when (this) {
    PlatformDto.ANDROID -> DeviceToken.Platform.ANDROID
    PlatformDto.IOS -> DeviceToken.Platform.IOS
    PlatformDto.WEB -> DeviceToken.Platform.WEB
  }
}