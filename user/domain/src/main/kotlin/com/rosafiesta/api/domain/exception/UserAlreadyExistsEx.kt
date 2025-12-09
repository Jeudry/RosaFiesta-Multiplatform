package com.rosafiesta.api.domain.exception

import java.lang.RuntimeException

class UserAlreadyExistsEx: RuntimeException("User already exists")
