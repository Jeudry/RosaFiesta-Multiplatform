package com.rosafiesta.api.service

import com.rosafiesta.api.domain.exceptions.InvalidTokenEx
import com.rosafiesta.api.domain.events.user.UserEvent
import com.rosafiesta.api.domain.exception.EmailNotVerifiedEx
import com.rosafiesta.api.domain.exception.InvalidCredentialsEx
import com.rosafiesta.api.domain.exception.PasswordHashFailedEx
import com.rosafiesta.api.domain.exception.UserAlreadyExistsEx
import com.rosafiesta.api.domain.exception.UserNotFoundEx
import com.rosafiesta.api.domain.model.AuthenticatedUser
import com.rosafiesta.api.domain.model.User
import com.rosafiesta.api.domain.types.UserId
import com.rosafiesta.api.infra.database.entities.RefreshTokenEntity
import com.rosafiesta.api.infra.database.entities.UserEntity
import com.rosafiesta.api.infra.database.mappers.toModel
import com.rosafiesta.api.infra.database.repositories.RefreshTokenRepository
import com.rosafiesta.api.infra.database.repositories.UserRepository
import com.rosafiesta.api.infra.message_queue.EventPublisher
import com.rosafiesta.api.infra.security.PasswordEncoder
import com.rosafiesta.api.services.JwtService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.MessageDigest
import java.time.Instant
import java.util.Base64

@Service
class AuthService (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val emailVerificationService: EmailVerificationService,
    private val eventPublisher: EventPublisher
){
    fun register(username: String, email: String, password: String): User {
        val trimmedEmail = email.trim()

        val user = userRepository.findByEmailOrUsername(
            trimmedEmail,
            username.trim()
        )

        if(user != null){
            throw UserAlreadyExistsEx()
        }

        val passwordHashed = passwordEncoder.encode(password.trim())
            ?: throw PasswordHashFailedEx()

        val savedUser = userRepository.saveAndFlush(
            UserEntity(
                email = trimmedEmail,
                username = username.trim(),
                hashedPassword = passwordHashed
            )
        ).toModel()

        val emailToken = emailVerificationService.createVerificationToken(trimmedEmail)

        eventPublisher.publish(
            event = UserEvent.Created(
                userId = savedUser.id,
                email = savedUser.email,
                username = savedUser.username,
                verificationToken = emailToken.token
            )
        )

        return savedUser
    }

    fun login(email: String, password: String): AuthenticatedUser {
        val user = userRepository.findByEmail(email) ?: throw InvalidCredentialsEx()

        if (!passwordEncoder.matches(password, user.hashedPassword)) {
            throw InvalidCredentialsEx()
        }

        if(!user.hasVerifiedEmail){
            throw EmailNotVerifiedEx()
        }

        return user.id?.let { userId ->
            val accessToken = jwtService.generateAccessToken(userId)
            val refreshToken = jwtService.generateRefreshToken(userId)
            storeRefreshToken(userId, refreshToken)
            AuthenticatedUser(
                user = user.toModel(),
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        } ?: throw UserNotFoundEx()
    }

    @Transactional
     fun refresh(refreshToken: String): AuthenticatedUser {
        if(!jwtService.validateRefreshToken(refreshToken)){
            throw InvalidTokenEx("Invalid refresh token")
        }

        val userId = jwtService.getUserIdFromToken(refreshToken)
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundEx() }
        val hashed = hashToken(refreshToken)

        return user.id?.let { userId ->
            refreshTokenRepository.findByUserIdAndHashedToken(
                userId = userId,
                hashedToken = hashed
            ) ?: throw InvalidTokenEx("Invalid refreshToken")

            refreshTokenRepository.deleteByUserIdAndHashedToken(
                userId = userId,
                hashedToken = hashed
            )

            val newAccessToken = jwtService.generateAccessToken(userId)
            val newRefreshToken = jwtService.generateRefreshToken(userId)

            storeRefreshToken(userId, newRefreshToken)

            AuthenticatedUser(
                user = user.toModel(),
                accessToken = newAccessToken,
                refreshToken = newRefreshToken
            )
        } ?: throw UserNotFoundEx()
    }

    private fun storeRefreshToken(userId: UserId, token: String) {
        val hashed = hashToken(token)
        val expiryMs = jwtService.refreshTokenValidityMs
        val expiresAt = Instant.now().plusMillis(expiryMs)

        refreshTokenRepository.save(
            RefreshTokenEntity(
                userId = userId,
                hashedToken = hashed,
                createdAt = Instant.now(),
                updatedAt = expiresAt
            )
        )
    }

    private fun hashToken(token: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(token.encodeToByteArray())
        return Base64.getEncoder().encodeToString(hashBytes)
    }
}