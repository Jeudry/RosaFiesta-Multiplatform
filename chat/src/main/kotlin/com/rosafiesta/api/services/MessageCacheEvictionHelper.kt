package com.rosafiesta.api.services

import com.rosafiesta.api.domain.types.ChatId
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