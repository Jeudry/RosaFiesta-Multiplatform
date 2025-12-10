package com.rosafiesta.api.core.domain.events.user

import com.rosafiesta.api.core.domain.events.RosaFiestaEvent
import com.rosafiesta.api.core.domain.types.UserId
import java.time.Instant
import java.util.UUID

sealed class UserEvent(
    override val eventId: String = UUID.randomUUID().toString(),
    override val exchange: String = UserEventConstants.USER_EXCHANGE,
    override val occurredAt: Instant = Instant.now()
): RosaFiestaEvent {

    data class Created (
        val userId: UserId,
        val email: String,
        val username: String,
        val verificationToken: String,
        override val eventKey: String = UserEventConstants.USER_CREATED_KEY
    ): UserEvent(), RosaFiestaEvent

    data class Verified (
        val userId: UserId,
        val email: String,
        val username: String,
        override val eventKey: String = UserEventConstants.USER_VERIFIED
    ): UserEvent(), RosaFiestaEvent

    data class RequestResendVerification (
        val userId: UserId,
        val email: String,
        val username: String,
        val verificationToken: String,
        override val eventKey: String = UserEventConstants.USER_REQUEST_RESEND_VERIFICATION
    ): UserEvent(), RosaFiestaEvent

    data class RequestResetPassword (
        val userId: UserId,
        val email: String,
        val username: String,
        val verificationToken: String,
        val expiresInMinutes: Long,
        override val eventKey: String = UserEventConstants.USER_REQUEST_RESET_PASSWORD
    ): UserEvent(), RosaFiestaEvent
}