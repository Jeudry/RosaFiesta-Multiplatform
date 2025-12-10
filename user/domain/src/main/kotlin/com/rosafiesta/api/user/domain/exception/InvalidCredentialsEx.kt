package com.rosafiesta.api.user.domain.exception

import java.lang.RuntimeException

class InvalidCredentialsEx: RuntimeException("The provided credentials are invalid")