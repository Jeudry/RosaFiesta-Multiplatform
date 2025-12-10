package com.rosafiesta.api.user.infrastructure.database.repositories

import com.rosafiesta.api.core.domain.types.UserId
import com.rosafiesta.api.user.infrastructure.database.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository: JpaRepository<UserEntity, UserId> {
    fun findByEmail(email: String): UserEntity?
    fun findByEmailOrUsername(email:String, username: String): UserEntity?
}