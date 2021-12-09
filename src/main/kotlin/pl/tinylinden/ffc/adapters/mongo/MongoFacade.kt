package pl.tinylinden.ffc.adapters.mongo

import org.springframework.stereotype.Component
import pl.tinylinden.ffc.core.model.AverageRating
import pl.tinylinden.ffc.core.model.MovieId
import pl.tinylinden.ffc.core.model.Rating
import pl.tinylinden.ffc.core.model.ShowingsForDate
import pl.tinylinden.ffc.core.ports.AverageRatingFinder
import pl.tinylinden.ffc.core.ports.RatingManager
import pl.tinylinden.ffc.core.ports.ShowingsFinder
import pl.tinylinden.ffc.core.ports.ShowingsManager
import java.time.LocalDate

@Component
class MongoFacade(
    private val showingsRepository: MongoShowingsRepository,
    private val ratingRepository: MongoRatingRepository,
    private val mapper: MongoMapper
) : ShowingsManager, ShowingsFinder, RatingManager, AverageRatingFinder {

    override fun setOrReplaceShowings(showings: List<ShowingsForDate>) {
        showings.map { mapper.toDocument(it) }
            .let { showingsRepository.saveAll(it) }
    }

    override fun findShowings(from: LocalDate, to: LocalDate): List<ShowingsForDate> =
        showingsRepository.findShowings(from, to.plusDays(1))
            .map { mapper.fromDocument(it) }

    override fun setOrReplaceRating(authorFingerprint: String, rating: Rating) {
        mapper.toDocument(authorFingerprint, rating)
            .let { ratingRepository.save(it) }
    }

    override fun findAverageRating(movie: MovieId): AverageRating =
        ratingRepository.findRatings(movie.imdbId)
            .takeIf { it.isNotEmpty() }
            ?.map { it.rating }
            ?.let { AverageRating(movie, it.size, it.average()) }
            // maybe instead of "fake" rating, exception should be thrown and then
            // api could return 404 telling that there are no ratings for given movie.
            // but on the other hand 0 votes count tells exactly the same thing
            ?: AverageRating(movie, 0, 0.0)
}
