package com.rosafiesta.api.user.api.dtos

data class LoginRequest(
    val email: String,
    val password: String,
    val rememberMe: Boolean = false
)