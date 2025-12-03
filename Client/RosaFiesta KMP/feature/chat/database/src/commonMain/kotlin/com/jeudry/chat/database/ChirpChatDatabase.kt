package com.jeudry.chat.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.jeudry.chat.database.dao.ChatDao
import com.jeudry.chat.database.dao.ChatMessageDao
import com.jeudry.chat.database.dao.ChatParticipantDao
import com.jeudry.chat.database.dao.ChatParticipantsCrossRefDao
import com.jeudry.chat.database.entities.ChatEntity
import com.jeudry.chat.database.entities.ChatMessageEntity
import com.jeudry.chat.database.entities.ChatParticipantCrossRef
import com.jeudry.chat.database.entities.ChatParticipantEntity
import com.jeudry.chat.database.view.LastMessageView

@Database(
    entities = [
        ChatEntity::class,
        ChatParticipantEntity::class,
        ChatMessageEntity::class,
        ChatParticipantCrossRef::class,
    ],
    views = [
        LastMessageView::class
    ],
    version = 1,
)
@ConstructedBy(RosaFiestaChatDatabaseConstructor::class)
abstract class RosaFiestaChatDatabase: RoomDatabase() {
    abstract val chatDao: ChatDao
    abstract val chatParticipantDao: ChatParticipantDao
    abstract val chatMessageDao: ChatMessageDao
    abstract val chatParticipantsCrossRefDao: ChatParticipantsCrossRefDao

    companion object {
        const val DB_NAME = "rosafiesta.db"
    }
}