package pl.tinylinden.ffc.adapters.rest.v1

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import pl.tinylinden.ffc.adapters.rest.v1.model.ShowingsForDateDto
import pl.tinylinden.ffc.core.ports.ShowingsFinder
import java.time.LocalDate

@RestController
class PublicApiEndpoint(
    private val showingsFinder: ShowingsFinder,
    private val mapper: ApiMapper
) : PublicApi {

    override fun findShowings(from: LocalDate, to: LocalDate): ResponseEntity<List<ShowingsForDateDto>> =
        showingsFinder.findShowings(from, to)
            .map { mapper.toDto(it) }
            .let { ResponseEntity.ok(it) }
}