package com.rosafiesta.api.api.dtos

import com.rosafiesta.api.api.utils.Password
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class ResetPasswordRequest(
    @field:NotBlank val token: String,
    @field:Password
    val newPassword: String,
)
