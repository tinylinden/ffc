package pl.tinylinden.ffc.adapters.graphql

import io.leangen.graphql.annotations.GraphQLIgnore
import pl.tinylinden.ffc.core.model.MovieId
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

data class ShowingsForDateNode(
    val date: LocalDate,
    val showings: List<ShowingNode>
)

data class ShowingNode(
    val movieId: String,
    val startAt: LocalTime,
    val ticketPrice: PriceNode
)

data class PriceNode(
    val currency: String,
    val amount: BigDecimal
)

data class MovieDetailsNode(
    private val id: String,
    val title: String,
    val plot: String,
    val releaseDate: LocalDate,
    val runtime: String,
    val imdbRating: Double
) {

    @GraphQLIgnore
    fun movieId(): MovieId = MovieId(id)
}

data class AverageRatingNode(
    val votesCount: Int,
    val averageRating: Double
)