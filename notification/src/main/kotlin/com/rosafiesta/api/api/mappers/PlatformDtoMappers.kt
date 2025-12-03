package com.rosafiesta.api.api.mappers

import com.rosafiesta.api.api.dto.PlatformDto
import com.rosafiesta.api.domain.model.DeviceToken

fun PlatformDto.toDto(): DeviceToken.Platform {
  return when (this) {
    PlatformDto.ANDROID -> DeviceToken.Platform.ANDROID
    PlatformDto.IOS -> DeviceToken.Platform.IOS
    PlatformDto.WEB -> DeviceToken.Platform.WEB
  }
}