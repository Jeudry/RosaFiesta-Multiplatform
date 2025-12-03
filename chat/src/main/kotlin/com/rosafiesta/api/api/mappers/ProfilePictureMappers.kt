package com.rosafiesta.api.api.mappers

import com.rosafiesta.api.api.dto.ProfilePictureUploadResponse
import com.rosafiesta.api.domain.models.ProfilePictureUploadCredentials

fun ProfilePictureUploadCredentials.toDto(): ProfilePictureUploadResponse {
  return ProfilePictureUploadResponse(
    uploadUrl = this.uploadUrl,
    publicUrl = this.publicUrl,
    headers = this.headers,
    expiresAt = this.expiresAt
  )
}