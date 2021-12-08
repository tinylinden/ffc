package pl.tinylinden.ffc.adapters.rest.v1

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import pl.tinylinden.ffc.adapters.rest.v1.model.ShowingDto
import pl.tinylinden.ffc.core.model.ShowingsForDate
import pl.tinylinden.ffc.core.ports.ShowingsManager
import java.time.LocalDate

@RestController
class InternalApiEndpoint(
    private val showingsManager: ShowingsManager,
    private val mapper: ApiMapper
) : InternalApi {

    override fun setOrReplaceShowings(date: LocalDate, showingDto: List<ShowingDto>): ResponseEntity<Unit> {
        val showings = ShowingsForDate(date, showingDto.map { mapper.fromDto(it) })
        showingsManager.setOrReplaceShowings(listOf(showings))
        return ResponseEntity.accepted().build()
    }
}