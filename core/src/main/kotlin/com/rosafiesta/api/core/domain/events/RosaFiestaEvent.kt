package com.rosafiesta.api.core.domain.events

import java.time.Instant

interface RosaFiestaEvent {
    val eventId: String
    val eventKey: String
    val occurredAt: Instant
    val exchange: String
}