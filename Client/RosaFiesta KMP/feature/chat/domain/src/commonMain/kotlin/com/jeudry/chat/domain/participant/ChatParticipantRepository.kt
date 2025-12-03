package com.jeudry.chat.domain.participant

import com.jeudry.chat.domain.models.ChatParticipant
import com.jeudry.core.domain.util.DataError
import com.jeudry.core.domain.util.EmptyResult
import com.jeudry.core.domain.util.Result

interface ChatParticipantRepository {
    suspend fun fetchLocalParticipant(): Result<ChatParticipant, DataError>
    suspend fun uploadProfilePicture(
        imageBytes: ByteArray,
        mimeType: String
    ): EmptyResult<DataError.Remote>

    suspend fun deleteProfilePicture(): EmptyResult<DataError.Remote>
}