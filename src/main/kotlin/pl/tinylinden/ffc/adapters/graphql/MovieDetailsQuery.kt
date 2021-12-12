package pl.tinylinden.ffc.adapters.graphql

import io.leangen.graphql.annotations.GraphQLContext
import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.stereotype.Component
import pl.tinylinden.ffc.core.model.MovieId
import pl.tinylinden.ffc.core.ports.MovieDetailsProvider

@Component
@GraphQLApi
class MovieDetailsQuery(
    private val movieDetailsProvider: MovieDetailsProvider,
    private val mapper: GraphqlApiMapper
) {

    @GraphQLQuery
    fun getMovieDetails(id: String): MovieDetailsNode =
        movieDetailsProvider.getMovieDetails(MovieId(id))
            .let { mapper.toNode(id, it) }

    @GraphQLQuery
    fun getMovieDetails(@GraphQLContext ctx: ShowingNode): MovieDetailsNode =
        getMovieDetails(ctx.movieId)
}