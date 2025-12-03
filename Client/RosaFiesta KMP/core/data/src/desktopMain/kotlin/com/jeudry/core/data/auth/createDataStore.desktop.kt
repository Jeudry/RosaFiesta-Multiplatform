package com.jeudry.core.data.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jeudry.core.data.util.DesktopOs
import com.jeudry.core.data.util.appDataDirectory
import com.jeudry.core.data.util.currentOs
import java.io.File

fun createDataStore(): DataStore<Preferences> = createDataStore {
    val directory = appDataDirectory

    if(!directory.exists()) {
        directory.mkdirs()
    }

    File(directory, DATA_STORE_FILE_NAME).absolutePath
}