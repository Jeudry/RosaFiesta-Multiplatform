package com.rosafiesta.user.infrastructure.database.repositories

import com.rosafiesta.core.domain.types.UserId
import com.rosafiesta.user.infrastructure.database.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<UserEntity, UserId> {
    fun findByEmail(email: String): UserEntity?
    fun findByEmailOrUsername(email:String, username: String): UserEntity?
}