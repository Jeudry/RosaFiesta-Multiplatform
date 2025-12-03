package com.plcoding.chat.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<RosaFiestaChatDatabase> {
        val dbFile = context.applicationContext.getDatabasePath(RosaFiestaChatDatabase.DB_NAME)

        return Room.databaseBuilder(
            context.applicationContext,
            dbFile.absolutePath
        )
    }
}