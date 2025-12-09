package com.rosafiesta.api.domain.exception

class SamePasswordEx: RuntimeException("The new password cannot be the same as the old password")