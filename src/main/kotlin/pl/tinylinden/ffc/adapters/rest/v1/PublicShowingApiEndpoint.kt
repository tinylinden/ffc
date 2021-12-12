package pl.tinylinden.ffc.adapters.rest.v1

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import pl.tinylinden.ffc.adapters.rest.v1.model.ShowingsForDateDto
import pl.tinylinden.ffc.core.ports.ShowingsFinder
import java.time.LocalDate

@RestController
class PublicShowingApiEndpoint(
    private val showingsFinder: ShowingsFinder,
    private val mapper: ApiMapper
) : PublicShowingsApi {

    override fun getShowings(from: LocalDate, to: LocalDate): ResponseEntity<List<ShowingsForDateDto>> =
        showingsFinder.findShowings(from, to)
            .map { mapper.toDto(it) }
            .let { ResponseEntity.ok(it) }
}
