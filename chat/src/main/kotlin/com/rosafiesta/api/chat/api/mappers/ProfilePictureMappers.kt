package com.rosafiesta.api.chat.api.mappers

import com.rosafiesta.api.chat.api.dto.ProfilePictureUploadResponse
import com.rosafiesta.api.chat.domain.models.ProfilePictureUploadCredentials

fun ProfilePictureUploadCredentials.toDto(): ProfilePictureUploadResponse {
  return ProfilePictureUploadResponse(
    uploadUrl = this.uploadUrl,
    publicUrl = this.publicUrl,
    headers = this.headers,
    expiresAt = this.expiresAt
  )
}