package pl.tinylinden.ffc.adapters.omdb

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "adapters.omdb")
data class OmdbApiClientProperties(
    val apiKey: String
)
