package com.jeudry.chat.presentation.di

import com.jeudry.chat.presentation.chat_detail.ChatDetailViewModel
import com.jeudry.chat.presentation.chat_list.ChatListViewModel
import com.jeudry.chat.presentation.chat_list_detail.ChatListDetailViewModel
import com.jeudry.chat.presentation.create_chat.CreateChatViewModel
import com.jeudry.chat.presentation.manage_chat.ManageChatViewModel
import com.jeudry.chat.presentation.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val chatPresentationModule = module {
    viewModelOf(::ChatListViewModel)
    viewModelOf(::ChatListDetailViewModel)
    viewModelOf(::CreateChatViewModel)
    viewModelOf(::ChatDetailViewModel)
    viewModelOf(::ManageChatViewModel)
    viewModelOf(::ProfileViewModel)
}