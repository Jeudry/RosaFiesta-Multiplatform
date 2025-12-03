package com.rosafiesta.api.api.dto

import com.rosafiesta.api.domain.types.UserId
import jakarta.validation.constraints.Size

data class CreateChatRequest(
    @field:Size(min = 1, message = "Chat must have at least two participants.")
    val otherUsersId: List<UserId>
){

}
