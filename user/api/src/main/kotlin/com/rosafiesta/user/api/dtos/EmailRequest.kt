package com.rosafiesta.user.api.dtos

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import kotlinx.serialization.Serializable

@Serializable
data class EmailRequest @JsonCreator constructor(
    @field:Email
    @JsonProperty("email")
    val email: String
)