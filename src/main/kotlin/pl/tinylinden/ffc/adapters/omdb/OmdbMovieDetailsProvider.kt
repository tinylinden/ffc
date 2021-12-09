package pl.tinylinden.ffc.adapters.omdb

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import pl.tinylinden.ffc.config.CacheConfig.Companion.CACHE_NAME_MOVIE_DETAILS
import pl.tinylinden.ffc.core.model.MovieDetails
import pl.tinylinden.ffc.core.model.MovieId
import pl.tinylinden.ffc.core.ports.MovieDetailsProvider

@Component
class OmdbMovieDetailsProvider(
    private val properties: OmdbApiClientProperties,
    private val client: OmdbApiClient,
    private val mapper: OmdbMapper
) : MovieDetailsProvider {

    @Cacheable(CACHE_NAME_MOVIE_DETAILS)
    override fun getMovieDetails(id: MovieId): MovieDetails =
        client.getMovieDetails(properties.apiKey, id.imdbId)
            .takeIf { it.isSuccess }
            ?.let { mapper.fromOmdb(it) }
            ?: throw OmdbMovieDetailsNotFoundException()
}