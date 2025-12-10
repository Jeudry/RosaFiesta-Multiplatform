package com.rosafiesta.user.domain.model

data class EmailVerificationToken(
    val id: Long,
    val token: String,
    val user: User
)
