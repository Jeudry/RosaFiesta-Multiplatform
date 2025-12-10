package com.rosafiesta.api.chat.api.exception_handling

import com.rosafiesta.api.chat.domain.exceptions.ChatMessageNotFoundEx
import com.rosafiesta.api.chat.domain.exceptions.ChatNotFoundEx
import com.rosafiesta.api.chat.domain.exceptions.ChatParticipantNotFoundEx
import com.rosafiesta.api.chat.domain.exceptions.InvalidChatSizeEx
import com.rosafiesta.api.chat.domain.exceptions.InvalidProfilePictureEx
import com.rosafiesta.api.chat.domain.exceptions.StorageEx
import com.rosafiesta.api.core.domain.exceptions.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ChatExceptionHandler(

) {
  @ExceptionHandler(
    ChatNotFoundEx::class,
    ChatMessageNotFoundEx::class,
    ChatParticipantNotFoundEx::class
  )
  @ResponseStatus(HttpStatus.NOT_FOUND)
  fun onForbidden(
    e: Exception
  ) = mapOf(
    "code" to "NOT_FOUND",
    "message" to e.message
  )
  
  @ExceptionHandler(InvalidChatSizeEx::class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  fun onInvalidChatSize(
    e: InvalidChatSizeEx
  ) = mapOf(
    "code" to "INVALID_CHAT_SIZE",
    "message" to e.message
  )
  
  @ExceptionHandler(InvalidProfilePictureEx::class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  fun onInvalidProfilePicture(
    e: InvalidProfilePictureEx
  ) = mapOf(
    "code" to "INVALID_PROFILE_PICTURE",
    "message" to e.message
  )
  
  @ExceptionHandler(StorageEx::class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  fun onStorageEx(
    e: StorageEx
  ) = mapOf(
    "code" to "STORAGE_ERROR",
    "message" to e.message
  )
}