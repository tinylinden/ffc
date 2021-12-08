package pl.tinylinden.ffc.config

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun cacheManager(): CacheManager = ConcurrentMapCacheManager(CACHE_NAME_MOVIE_DETAILS)

    companion object {
        const val CACHE_NAME_MOVIE_DETAILS = "movie-details"
    }
}