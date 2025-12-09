package com.rosafiesta.api.domain.events

import java.time.Instant

interface RosaFiestaEvent {
    val eventId: String
    val eventKey: String
    val occurredAt: Instant
    val exchange: String
}