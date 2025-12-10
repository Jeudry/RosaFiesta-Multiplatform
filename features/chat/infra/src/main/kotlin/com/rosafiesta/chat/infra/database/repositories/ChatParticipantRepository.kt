package com.rosafiesta.chat.infra.database.repositories

import com.rosafiesta.core.domain.types.UserId
import com.rosafiesta.chat.infra.database.entities.ChatParticipantEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface ChatParticipantRepository: JpaRepository<ChatParticipantEntity, UserId> {
    fun findByUserIdIn(userId: List<UserId>): Set<ChatParticipantEntity>
    @Query("""
        SELECT p 
        FROM ChatParticipantEntity p 
        WHERE LOWER(p.username) = :query OR LOWER(p.email) = :query
    """)
    fun findByEmailOrUsername(query: String): ChatParticipantEntity?
    fun userId(userId: UUID): MutableList<ChatParticipantEntity>
}