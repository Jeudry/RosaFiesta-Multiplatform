package com.rosafiesta.api.domain.exception

import java.lang.RuntimeException

class InvalidCredentialsEx: RuntimeException("The provided credentials are invalid")