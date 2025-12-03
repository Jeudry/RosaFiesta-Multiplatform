package com.jeudry.chat.data.mappers

import com.jeudry.chat.data.dto.response.ProfilePictureUploadUrlsResponse
import com.jeudry.chat.domain.models.ProfilePictureUploadUrls

fun ProfilePictureUploadUrlsResponse.toDomain(): ProfilePictureUploadUrls {
    return ProfilePictureUploadUrls(
        uploadUrl = uploadUrl,
        publicUrl = publicUrl,
        headers = headers
    )
}