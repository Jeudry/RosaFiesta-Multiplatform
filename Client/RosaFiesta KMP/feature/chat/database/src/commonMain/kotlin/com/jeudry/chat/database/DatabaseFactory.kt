package com.jeudry.chat.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<RosaFiestaChatDatabase>
}