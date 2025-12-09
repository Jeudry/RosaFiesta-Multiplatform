package com.rosafiesta.api.api.dtos

import com.rosafiesta.api.api.utils.Password
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class EmailRequest(
    @field:Email val email: String,
)
