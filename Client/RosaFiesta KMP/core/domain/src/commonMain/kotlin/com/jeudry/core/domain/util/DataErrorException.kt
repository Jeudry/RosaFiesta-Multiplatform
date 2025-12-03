package com.jeudry.core.domain.util

class DataErrorException(
    val error: DataError
): Exception()