package pl.tinylinden.ffc.adapters.graphql

import io.leangen.graphql.annotations.GraphQLContext
import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.stereotype.Component
import pl.tinylinden.ffc.core.ports.AverageRatingFinder

@Component
@GraphQLApi
class AverageRatingQuery(
    private val averageRatingFinder: AverageRatingFinder,
    private val mapper: GraphqlApiMapper
) {

    @GraphQLQuery
    fun getAverageRating(@GraphQLContext ctx: MovieDetailsNode): AverageRatingNode =
        averageRatingFinder.findAverageRating(ctx.movieId())
            .let { mapper.toNode(it) }
}
