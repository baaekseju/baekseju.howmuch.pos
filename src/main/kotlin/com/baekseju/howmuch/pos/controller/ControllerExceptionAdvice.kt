package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.ErrorDto
import com.baekseju.howmuch.pos.exception.UserExistException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.persistence.EntityNotFoundException
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ControllerExceptionAdvice {
    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun entityNotFoundHandler(exception: EntityNotFoundException, request: HttpServletRequest): ErrorDto{
        val error = ErrorDto(
            status = HttpStatus.NOT_FOUND.value(),
            error = HttpStatus.NOT_FOUND.reasonPhrase,
            path = request.requestURI
        )

        error.messages.add(exception.message)

        return error
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun httpMessageNotReadableHandler(exception: HttpMessageNotReadableException, request: HttpServletRequest): ErrorDto{
        val error = ErrorDto(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            path = request.requestURI
        )

        error.messages.add(exception.message)

        return error
    }

    @ExceptionHandler(UserExistException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun userExistHandler(exception: UserExistException, request: HttpServletRequest): ErrorDto{
        val error = ErrorDto(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            path = request.requestURI
        )

        error.messages.add(exception.message)

        return error
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun invalidDataHandler(exception: ConstraintViolationException, request: HttpServletRequest): ErrorDto{
        val error = ErrorDto(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            path = request.requestURI
        )

        exception.constraintViolations.map { error.messages.add(it.message) }

        return error
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun invalidDataHandler(exception: MethodArgumentNotValidException, request: HttpServletRequest): ErrorDto{
        val error = ErrorDto(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            path = request.requestURI
        )

        exception.bindingResult.allErrors.map { error.messages.add(it.defaultMessage) }

        return error
    }
}
