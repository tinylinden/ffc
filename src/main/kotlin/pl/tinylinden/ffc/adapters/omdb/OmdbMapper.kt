package pl.tinylinden.ffc.adapters.omdb

import org.mapstruct.Mapper
import pl.tinylinden.ffc.core.model.MovieDetails

@Mapper(componentModel = "spring")
interface OmdbMapper {

    fun fromOmdb(source: OmdbMovieDetails): MovieDetails
}