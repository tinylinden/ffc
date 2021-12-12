package pl.tinylinden.ffc.adapters.rest

import com.atlassian.oai.validator.report.ValidationReport
import com.atlassian.oai.validator.springmvc.InvalidRequestException
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
            is InvalidRequestException -> badRequest(exception.validationReport)
            else -> throw exception
        }

    private fun notFound(): ResponseEntity<Any> = ResponseEntity.notFound().build()

    private fun badRequest(validationReport: ValidationReport): ResponseEntity<Any> =
        ResponseEntity.badRequest().body(validationReport)
}