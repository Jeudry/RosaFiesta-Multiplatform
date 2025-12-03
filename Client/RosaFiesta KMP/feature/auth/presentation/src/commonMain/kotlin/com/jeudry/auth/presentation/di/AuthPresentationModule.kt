package com.jeudry.auth.presentation.di

import com.jeudry.auth.presentation.email_verification.EmailVerificationViewModel
import com.jeudry.auth.presentation.forgot_password.ForgotPasswordViewModel
import com.jeudry.auth.presentation.login.LoginViewModel
import com.jeudry.auth.presentation.register.RegisterViewModel
import com.jeudry.auth.presentation.register_success.RegisterSuccessViewModel
import com.jeudry.auth.presentation.reset_password.ResetPasswordViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::RegisterSuccessViewModel)
    viewModelOf(::EmailVerificationViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::ResetPasswordViewModel)
}