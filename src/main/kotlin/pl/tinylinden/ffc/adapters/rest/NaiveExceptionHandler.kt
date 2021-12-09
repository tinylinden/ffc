package pl.tinylinden.ffc.adapters.rest

import feign.FeignException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import pl.tinylinden.ffc.adapters.omdb.OmdbMovieDetailsNotFoundException

@ControllerAdvice
class NaiveExceptionHandler {

    @ExceptionHandler
    fun handleException(exception: Exception): ResponseEntity<Any> =
        when (exception) {
            is FeignException.NotFound -> notFound()
            is OmdbMovieDetailsNotFoundException -> notFound()
            else -> throw exception
        }

    private fun notFound(): ResponseEntity<Any> = ResponseEntity.notFound().build()
}