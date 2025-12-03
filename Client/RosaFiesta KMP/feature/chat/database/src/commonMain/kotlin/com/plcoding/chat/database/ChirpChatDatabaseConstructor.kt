package com.plcoding.chat.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object RosaFiestaChatDatabaseConstructor: RoomDatabaseConstructor<RosaFiestaChatDatabase> {
    override fun initialize(): RosaFiestaChatDatabase
}