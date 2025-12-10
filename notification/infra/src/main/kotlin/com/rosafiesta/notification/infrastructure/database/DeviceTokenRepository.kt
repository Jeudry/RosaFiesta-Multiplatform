package com.rosafiesta.notification.infrastructure.database

import com.rosafiesta.api.core.domain.types.UserId
import org.springframework.data.jpa.repository.JpaRepository

interface DeviceTokenRepository: JpaRepository<DeviceTokenEntity, Long> {
  fun findByUserIdIn(userIds: List<UserId>): List<DeviceTokenEntity>
  fun findByToken(token: String): DeviceTokenEntity?
  fun deleteByToken(token: String)
}