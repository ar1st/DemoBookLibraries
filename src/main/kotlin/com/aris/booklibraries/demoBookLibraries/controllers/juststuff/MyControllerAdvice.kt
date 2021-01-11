package com.aris.booklibraries.demoBookLibraries.controllers.juststuff

import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice(assignableTypes = [BaseController::class])
class MyControllerAdvice: ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [IllegalArgumentException::class, IllegalStateException::class])
    protected fun handleConflict(
        ex: RuntimeException?, request: WebRequest?
    ): ResponseEntity<Any?>? {
        val bodyOfResponse = "This should be application specific"
        return handleExceptionInternal(
            ex!!, bodyOfResponse,
            HttpHeaders(), HttpStatus.CONFLICT, request!!
        )
    }
}