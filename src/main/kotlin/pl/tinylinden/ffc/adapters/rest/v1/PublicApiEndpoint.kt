package pl.tinylinden.ffc.adapters.rest.v1

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.DigestUtils
import org.springframework.web.bind.annotation.RestController
import pl.tinylinden.ffc.adapters.rest.v1.model.AverageRatingDto
import pl.tinylinden.ffc.adapters.rest.v1.model.MovieDetailsDto
import pl.tinylinden.ffc.adapters.rest.v1.model.RatingDto
import pl.tinylinden.ffc.adapters.rest.v1.model.ShowingsForDateDto
import pl.tinylinden.ffc.core.model.MovieId
import pl.tinylinden.ffc.core.ports.AverageRatingFinder
import pl.tinylinden.ffc.core.ports.MovieDetailsProvider
import pl.tinylinden.ffc.core.ports.RatingManager
import pl.tinylinden.ffc.core.ports.ShowingsFinder
import java.time.LocalDate

@RestController
class PublicApiEndpoint(
    private val showingsFinder: ShowingsFinder,
    private val movieDetailsProvider: MovieDetailsProvider,
    private val ratingManager: RatingManager,
    private val averageRatingFinder: AverageRatingFinder,
    private val mapper: ApiMapper
) : PublicApi {

    override fun getShowings(from: LocalDate, to: LocalDate): ResponseEntity<List<ShowingsForDateDto>> =
        showingsFinder.findShowings(from, to)
            .map { mapper.toDto(it) }
            .let { ResponseEntity.ok(it) }

    override fun getMovieDetails(id: String): ResponseEntity<MovieDetailsDto> =
        movieDetailsProvider.getMovieDetails(MovieId(id))
            .let { mapper.toDto(it) }
            .let { ResponseEntity.ok(it) }

    override fun setOrReplaceRating(id: String, ratingDto: RatingDto): ResponseEntity<Unit> {
        mapper.fromDto(id, ratingDto)
            .let { ratingManager.setOrReplaceRating(authorFingerprint(), it) }
        return ResponseEntity.accepted().build()
    }

    override fun getAverageRating(id: String): ResponseEntity<AverageRatingDto> =
        averageRatingFinder.findAverageRating(MovieId(id))
            .let { mapper.toDto(it) }
            .let { ResponseEntity.ok(it) }

    // there are a ton of better ways to calculate author's fingerprint, but
    // this quick-and-dirty one is good enough for now
    private fun authorFingerprint(): String =
        SecurityContextHolder.getContext().authentication.name.toByteArray()
            .let { DigestUtils.md5DigestAsHex(it) }
}
