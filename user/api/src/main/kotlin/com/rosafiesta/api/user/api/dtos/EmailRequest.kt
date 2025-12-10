package com.rosafiesta.api.user.api.dtos

import com.rosafiesta.api.user.api.utils.Password
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class EmailRequest(
    @field:Email val email: String,
)
