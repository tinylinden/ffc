package pl.tinylinden.ffc.adapters.graphql

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import pl.tinylinden.ffc.core.model.AverageRating
import pl.tinylinden.ffc.core.model.MovieDetails
import pl.tinylinden.ffc.core.model.Showing
import pl.tinylinden.ffc.core.model.ShowingsForDate

@Mapper(componentModel = "spring")
interface GraphqlApiMapper {

    fun toNode(code: ShowingsForDate): ShowingsForDateNode

    @Mappings(
        Mapping(target = "movieId", source = "movie.imdbId")
    )
    fun toNode(core: Showing): ShowingNode

    @Mappings(
        Mapping(target = "id", source = "movieId")
    )
    fun toNode(movieId: String, core: MovieDetails): MovieDetailsNode

    fun toNode(core: AverageRating): AverageRatingNode
}