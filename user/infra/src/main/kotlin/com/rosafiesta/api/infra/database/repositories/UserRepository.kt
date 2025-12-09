package com.rosafiesta.api.infra.database.repositories

import com.rosafiesta.api.domain.types.UserId
import com.rosafiesta.api.infra.database.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository: JpaRepository<UserEntity, UserId> {
    fun findByEmail(email: String): UserEntity?
    fun findByEmailOrUsername(email:String, username: String): UserEntity?
}