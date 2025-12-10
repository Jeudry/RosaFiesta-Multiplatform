package com.rosafiesta.api.chat.services

import com.rosafiesta.api.core.domain.types.ChatId
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Component

@Component
class MessageCacheEvictionHelper {
  
  @CacheEvict(
    value = ["messages"],
    key = "#chatId",
  )
  fun evictMessagesCache(chatId: ChatId){
    // NO-OP: Let spring handle cache eviction
    
  }
  
}