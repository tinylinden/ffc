package pl.tinylinden.ffc.adapters.omdb

import org.springframework.stereotype.Component
import pl.tinylinden.ffc.core.model.MovieDetails
import pl.tinylinden.ffc.core.model.MovieId
import pl.tinylinden.ffc.core.ports.MovieDetailsProvider

@Component
class OmdbMovieDetailsProvider(
    private val properties: OmdbApiClientProperties,
    private val client: OmdbApiClient,
    private val mapper: OmdbMapper
) : MovieDetailsProvider {

    override fun getMovieDetails(id: MovieId): MovieDetails =
        client.getMovieDetails(properties.apiKey, id.imdbId)
            .let { mapper.fromOmdb(it) }
}