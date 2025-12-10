package com.rosafiesta.user.api.exception_handling

import com.rosafiesta.api.user.domain.exception.*
import com.rosafiesta.api.core.domain.exceptions.InvalidTokenEx
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class AuthExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsEx::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun onUserAlreadyExists(
        e: UserAlreadyExistsEx
    ) = mapOf(
        "code" to "USER_EXISTS",
        "message" to e.message
    )

    @ExceptionHandler(UserNotFoundEx::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onUserNotFound(
        e: UserNotFoundEx
    ) = mapOf(
        "code" to "USER_NOT_FOUND",
        "message" to e.message
    )

    @ExceptionHandler(InvalidCredentialsEx::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun onInvalidCredentials(
        e: InvalidCredentialsEx
    ) = mapOf(
        "code" to "INVALID_CREDENTIALS",
        "message" to e.message
    )

    @ExceptionHandler(InvalidTokenEx::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun onInvalidToken(
        e: InvalidTokenEx
    ) = mapOf(
        "code" to "INVALID_TOKEN",
        "message" to e.message
    )

    @ExceptionHandler(EmailNotVerifiedEx::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun onEmailNotVerified(
        e: EmailNotVerifiedEx
    ) = mapOf(
        "code" to "EMAIL_NOT_VERIFIED",
        "message" to e.message
    )

    @ExceptionHandler(SamePasswordEx::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun onSamePassword(
        e: SamePasswordEx
    ) = mapOf(
        "code" to "SAME_PASSWORD",
        "message" to e.message
    )

    @ExceptionHandler(RateLimitEx::class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    fun onRateLimitExceeded(
        e: RateLimitEx
    ) = mapOf(
        "code" to "RATE_LIMIT_EXCEEDED",
        "message" to e.message
    )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun onValidationException(
        e: MethodArgumentNotValidException
    ): ResponseEntity<Map<String, Any>> {
        val errors = e.bindingResult.allErrors.map {
            it.defaultMessage ?: "Invalid value"
        }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                mapOf(
                    "code" to "VALIDATION_ERROR",
                    "errors" to errors
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleAll(e: Exception): ResponseEntity<String> {
        return ResponseEntity(e.message ?: "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}