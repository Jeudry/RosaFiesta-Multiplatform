package com.jeudry.chat.data.notification

import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.jeudry.chat.domain.notification.PushNotificationService
import com.jeudry.core.domain.logging.RosaFiestaLogger
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.coroutineContext

actual class FirebasePushNotificationService(
    private val logger: RosaFiestaLogger
) : PushNotificationService {

    actual override fun observeDeviceToken(): Flow<String?> = flow {
        try {
            val fcmToken = Firebase.messaging.token.await()
            logger.info("Initial FCM token received: $fcmToken")
            emit(fcmToken)
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            logger.error("Failed to get FCM token", e)
            emit(null)
        }
    }
}