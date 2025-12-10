package com.rosafiesta.notification.domain.model

import com.rosafiesta.api.core.domain.types.ChatId
import java.util.*

data class PushNotification(
  val id: String = UUID.randomUUID().toString(),
  val title: String,
  val recipients: List<DeviceToken>,
  val message: String,
  val chatId: ChatId,
  val data: Map<String, String>
)