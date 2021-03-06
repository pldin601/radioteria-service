package com.radioteria.web.rest.controllers

import com.radioteria.web.rest.exceptions.RestControllerException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler

abstract class BaseRestController {
    inner class RestErrorResponse(val message: String, val status: Int)

    @ExceptionHandler(RestControllerException::class)
    fun handleException(exception: RestControllerException): ResponseEntity<RestErrorResponse> {
        val error = RestErrorResponse(exception.message ?: "", exception.status.value())
        return ResponseEntity(error, exception.status)
    }
}