package com.rosafiesta.api.user.domain.exception

import java.lang.RuntimeException

class UserAlreadyExistsEx: RuntimeException("User already exists")
