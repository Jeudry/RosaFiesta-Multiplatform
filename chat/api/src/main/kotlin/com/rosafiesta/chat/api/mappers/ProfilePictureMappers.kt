package com.rosafiesta.chat.api.mappers

import com.rosafiesta.chat.api.dto.ProfilePictureUploadResponse
import com.rosafiesta.chat.domain.models.ProfilePictureUploadCredentials

fun ProfilePictureUploadCredentials.toDto(): ProfilePictureUploadResponse {
  return ProfilePictureUploadResponse(
    uploadUrl = this.uploadUrl,
    publicUrl = this.publicUrl,
    headers = this.headers,
    expiresAt = this.expiresAt
  )
}