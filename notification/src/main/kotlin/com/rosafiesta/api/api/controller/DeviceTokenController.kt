package com.rosafiesta.api.api.controller

import com.rosafiesta.api.api.dto.DeviceTokenDto
import com.rosafiesta.api.api.dto.RegisterDeviceRequest
import com.rosafiesta.api.api.mappers.toDto
import com.rosafiesta.api.api.utils.requestUserId
import com.rosafiesta.api.infra.service.PushNotificationService
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