package com.plcoding.rosafiesta

import com.plcoding.chat.data.notification.IosDeviceTokenHolder

object IosDeviceTokenHolderBridge {
    fun updateToken(token: String) {
        IosDeviceTokenHolder.updateToken(token)
    }
}