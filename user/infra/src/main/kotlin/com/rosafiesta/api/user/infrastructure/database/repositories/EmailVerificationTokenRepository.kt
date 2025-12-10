package com.rosafiesta.api.user.infrastructure.database.repositories

import com.rosafiesta.api.user.infrastructure.database.entities.EmailVerificationTokenEntity
import com.rosafiesta.api.user.infrastructure.database.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.Instant

interface EmailVerificationTokenRepository: JpaRepository<EmailVerificationTokenEntity, Long>{
    fun findByToken(token: String): EmailVerificationTokenEntity?
    fun deleteByExpiresAtLessThan(now: Instant)
    @Modifying
    @Query("UPDATE EmailVerificationTokenEntity t SET t.usedAt = CURRENT_TIMESTAMP WHERE t.user = :user")
    fun invalidateActiveTokensForUser(user: UserEntity)
}