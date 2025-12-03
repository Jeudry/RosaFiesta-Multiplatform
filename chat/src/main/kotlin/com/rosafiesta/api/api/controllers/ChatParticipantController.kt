package com.rosafiesta.api.api.controllers

import com.rosafiesta.api.api.dto.ChatParticipantDto
import com.rosafiesta.api.api.dto.ConfirmProfilePictureRequest
import com.rosafiesta.api.api.dto.ProfilePictureUploadResponse
import com.rosafiesta.api.api.mappers.toDto
import com.rosafiesta.api.api.utils.requestUserId
import com.rosafiesta.api.services.ChatParticipantService
import com.rosafiesta.api.services.ProfilePictureService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/participants")
class ChatParticipantController (
  private val chatParticipantService: ChatParticipantService,
  private val profilePictureService: ProfilePictureService
){
  @GetMapping
    fun getChatParticipantsByUsernameOrEmail(
        @RequestParam(required = false) query: String
    ): ChatParticipantDto {
        val participant = if(query == null){
            chatParticipantService.findChatParticipantById(requestUserId)
        } else {
            chatParticipantService.findChatParticipantByEmailOrUsername(query)
        }

        return participant?.toDto() ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
  
  @PostMapping("/profile-picture-upload")
  fun getProfilePictureUploadUrl(
    @RequestParam mimeType: String
  ): ProfilePictureUploadResponse {
    return profilePictureService.generateUploadCredentials(
      userId = requestUserId,
      mimeType = mimeType
    ).toDto()
  }
  
  @PostMapping("/confirm-profile-picture")
  fun confirmProfilePicture(
    @Valid @RequestBody body: ConfirmProfilePictureRequest 
  ) {
    return profilePictureService.confirmProfilePictureUpload(
      userId = requestUserId,
      url = body.publicUrl
    )
  }
  
  @DeleteMapping("/profile-picture")
  fun deleteProfilePicture() {
    return profilePictureService.deleteProfilePicture(
      userId = requestUserId
    )
  }
}