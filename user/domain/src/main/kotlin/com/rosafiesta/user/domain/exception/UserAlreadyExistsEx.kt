package com.rosafiesta.user.domain.exception

import java.lang.RuntimeException

class UserAlreadyExistsEx: RuntimeException("User already exists")
