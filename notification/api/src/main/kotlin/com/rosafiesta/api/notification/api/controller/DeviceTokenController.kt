package com.rosafiesta.api.notification.api.controller

import com.rosafiesta.api.notification.api.dto.DeviceTokenDto
import com.rosafiesta.api.notification.api.dto.RegisterDeviceRequest
import com.rosafiesta.api.notification.api.mappers.toDto
import com.rosafiesta.api.core.api.utils.requestUserId
import com.rosafiesta.api.notification.infrastructure.service.PushNotificationService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/notification")
class DeviceTokenController(private val pushNotificationService: PushNotificationService) {
  
  @PostMapping("/register")
  fun registerDeviceToken(
    @Valid @RequestBody body: RegisterDeviceRequest
  ): DeviceTokenDto {
    return pushNotificationService.registerDevice(
      userId = requestUserId,
      token = body.token,
      platform = body.platform.toDto()
    ).toDto()
  }
  
  @DeleteMapping("/{token}")
  fun unregisterDeviceToken(
    @PathVariable("token") token: String
  ) {
    pushNotificationService.unregisterDevice(
      token = token
    )
  }
  
  
}