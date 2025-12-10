package com.rosafiesta.user.api.dtos

data class LoginRequest(
    val email: String,
    val password: String,
    val rememberMe: Boolean = false
)