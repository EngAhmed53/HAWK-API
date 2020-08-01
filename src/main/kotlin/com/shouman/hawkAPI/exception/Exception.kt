package com.shouman.hawkAPI.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException : RuntimeException {
    constructor() : super() {}
    constructor(message: String?) : super(message) {}
    constructor(message: String?, cause: Throwable?) : super(message, cause) {}
}


@ResponseStatus(HttpStatus.NO_CONTENT)
class NoContentException : RuntimeException {
    constructor() : super() {}
    constructor(message: String?) : super(message) {}
    constructor(message: String?, cause: Throwable?) : super(message, cause) {}
}