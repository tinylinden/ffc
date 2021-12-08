package pl.tinylinden.ffc.adapters.omdb

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "omdbApiClient", url = "\${adapters.omdb.url}")
interface OmdbApiClient {

    @GetMapping(path = ["/"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getMovieDetails(@RequestParam("apiKey") apiKey: String, @RequestParam("i") imdbId: String): OmdbMovieDetails
}
