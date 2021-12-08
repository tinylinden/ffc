package pl.tinylinden.ffc.adapters.rest.v1

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import pl.tinylinden.ffc.adapters.rest.v1.model.MovieDetailsDto
import pl.tinylinden.ffc.adapters.rest.v1.model.ShowingsForDateDto
import pl.tinylinden.ffc.core.model.MovieId
import pl.tinylinden.ffc.core.ports.MovieDetailsProvider
import pl.tinylinden.ffc.core.ports.ShowingsFinder
import java.time.LocalDate

@RestController
class PublicApiEndpoint(
    private val showingsFinder: ShowingsFinder,
    private val movieDetailsProvider: MovieDetailsProvider,
    private val mapper: ApiMapper
) : PublicApi {

    override fun findShowings(from: LocalDate, to: LocalDate): ResponseEntity<List<ShowingsForDateDto>> =
        showingsFinder.findShowings(from, to)
            .map { mapper.toDto(it) }
            .let { ResponseEntity.ok(it) }

    override fun getMovieDetails(id: String): ResponseEntity<MovieDetailsDto> =
        movieDetailsProvider.getMovieDetails(MovieId(id))
            .let { mapper.toDto(it) }
            .let { ResponseEntity.ok(it) }
}
