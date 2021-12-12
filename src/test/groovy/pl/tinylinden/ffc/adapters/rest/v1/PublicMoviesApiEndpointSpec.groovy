package pl.tinylinden.ffc.adapters.rest.v1

import org.mapstruct.factory.Mappers
import org.springframework.http.HttpStatus
import pl.tinylinden.ffc.core.model.CoreDataMother
import pl.tinylinden.ffc.core.model.MovieId
import pl.tinylinden.ffc.core.ports.AverageRatingFinder
import pl.tinylinden.ffc.core.ports.MovieDetailsProvider
import pl.tinylinden.ffc.core.ports.RatingManager
import spock.lang.Specification
import spock.lang.Subject

class PublicMoviesApiEndpointSpec extends Specification {

    MovieDetailsProvider movieDetailsProvider = Mock()

    RatingManager ratingManager = Mock()

    AverageRatingFinder averageRatingFinder = Mock()

    ApiMapper apiMapper = Mappers.getMapper(ApiMapper.class)

    @Subject
    PublicMoviesApiEndpoint tested =
            new PublicMoviesApiEndpoint(movieDetailsProvider, ratingManager, averageRatingFinder, apiMapper)

    def "should return movie details"() {
        given:
            def id = "tt0232500"
            def movieDetails = CoreDataMother.movieDetails()

        when:
            def actual = tested.getMovieDetails(id)

        then:
            actual.statusCode == HttpStatus.OK
            actual.body == apiMapper.toDto(movieDetails)

        and:
            movieDetailsProvider.getMovieDetails(new MovieId(id)) >> movieDetails
    }
}
