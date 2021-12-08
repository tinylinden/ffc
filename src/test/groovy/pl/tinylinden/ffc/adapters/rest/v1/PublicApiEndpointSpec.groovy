package pl.tinylinden.ffc.adapters.rest.v1

import org.mapstruct.factory.Mappers
import org.springframework.http.HttpStatus
import pl.tinylinden.ffc.core.model.MovieId
import pl.tinylinden.ffc.core.model.TestDataMother
import pl.tinylinden.ffc.core.ports.MovieDetailsProvider
import pl.tinylinden.ffc.core.ports.ShowingsFinder
import spock.lang.Specification
import spock.lang.Subject

class PublicApiEndpointSpec extends Specification {

    ShowingsFinder showingsFinder = Mock()

    MovieDetailsProvider movieDetailsProvider = Mock()

    ApiMapper mapper = Mappers.getMapper(ApiMapper.class)

    @Subject
    PublicApiEndpoint tested = new PublicApiEndpoint(showingsFinder, movieDetailsProvider, mapper)

    def "should return showings if any was found for given dates"() {
        given:
            def fromDate = TestDataMother.date()
            def toDate = fromDate.plusDays(1)
            def showings = TestDataMother.showingsForDate()

        when:
            def actual = tested.findShowings(fromDate, toDate)

        then:
            actual.statusCode == HttpStatus.OK
            actual.body == [mapper.toDto(showings)]

        and:
            showingsFinder.findShowings(fromDate, toDate) >> [showings]
    }

    def "should return empty list if no showings was found for given dates"() {
        given:
            def fromDate = TestDataMother.date()
            def toDate = fromDate.plusDays(1)

        when:
            def actual = tested.findShowings(fromDate, toDate)

        then:
            actual.statusCode == HttpStatus.OK
            actual.body == []

        and:
            showingsFinder.findShowings(fromDate, toDate) >> []
    }

    def "should return movie details"() {
        given:
            def id = "tt0232500"
            def movieDetails = TestDataMother.movieDetails()

        when:
            def actual = tested.getMovieDetails(id)

        then:
            actual.statusCode == HttpStatus.OK
            actual.body == mapper.toDto(movieDetails)

        and:
            movieDetailsProvider.getMovieDetails(new MovieId(id)) >> movieDetails
    }
}
