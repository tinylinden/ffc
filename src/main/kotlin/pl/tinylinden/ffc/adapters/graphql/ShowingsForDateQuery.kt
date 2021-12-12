package pl.tinylinden.ffc.adapters.graphql

import io.leangen.graphql.annotations.GraphQLContext
import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.stereotype.Component
import pl.tinylinden.ffc.core.ports.ShowingsFinder
import java.time.LocalDate

@Component
@GraphQLApi
class ShowingsForDateQuery(
    private val finder: ShowingsFinder,
    private val mapper: GraphqlApiMapper
) {

    @GraphQLQuery
    fun getShowings(from: LocalDate, to: LocalDate): List<ShowingsForDateNode> =
        finder.findShowings(from, to)
            .map { mapper.toNode(it) }

    @GraphQLQuery
    fun getShowings(from: LocalDate, to: LocalDate, @GraphQLContext ctx: MovieDetailsNode): List<ShowingsForDateNode> =
        finder.findShowings(from, to)
            .map { it.onlyFor(ctx.movieId()) }
            .map { mapper.toNode(it) }
}
