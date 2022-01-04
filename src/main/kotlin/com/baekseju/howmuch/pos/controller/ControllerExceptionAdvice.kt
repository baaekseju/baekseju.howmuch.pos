package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.ErrorDto
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.persistence.EntityNotFoundException
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class ControllerExceptionAdvice {
    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun entityNotFoundHandler(exception: EntityNotFoundException, request: HttpServletRequest) = ErrorDto(
        status = HttpStatus.NOT_FOUND.value(),
        error = HttpStatus.NOT_FOUND.name,
        path = request.requestURI,
        message = exception.message
    )

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun httpMessageNotReadableHandler(exception: HttpMessageNotReadableException, request: HttpServletRequest) = ErrorDto(
        status = HttpStatus.BAD_REQUEST.value(),
        error = HttpStatus.BAD_REQUEST.name,
        path = request.requestURI,
        message = exception.message
    )
}
