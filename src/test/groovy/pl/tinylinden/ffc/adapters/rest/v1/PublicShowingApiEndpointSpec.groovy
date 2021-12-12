package pl.tinylinden.ffc.adapters.rest.v1

import org.mapstruct.factory.Mappers
import org.springframework.http.HttpStatus
import pl.tinylinden.ffc.core.model.CoreDataMother
import pl.tinylinden.ffc.core.ports.ShowingsFinder
import spock.lang.Specification
import spock.lang.Subject

class PublicShowingApiEndpointSpec extends Specification {

    ShowingsFinder showingsFinder = Mock()

    ApiMapper apiMapper = Mappers.getMapper(ApiMapper.class)

    @Subject
    PublicShowingApiEndpoint tested = new PublicShowingApiEndpoint(showingsFinder, apiMapper)

    def "should return showings if any was found for given dates"() {
        given:
            def fromDate = CoreDataMother.date()
            def toDate = fromDate.plusDays(1)
            def showings = CoreDataMother.showingsForDate()

        when:
            def actual = tested.getShowings(fromDate, toDate)

        then:
            actual.statusCode == HttpStatus.OK
            actual.body == [apiMapper.toDto(showings)]

        and:
            showingsFinder.findShowings(fromDate, toDate) >> [showings]
    }

    def "should return empty list if no showings was found for given dates"() {
        given:
            def fromDate = CoreDataMother.date()
            def toDate = fromDate.plusDays(1)

        when:
            def actual = tested.getShowings(fromDate, toDate)

        then:
            actual.statusCode == HttpStatus.OK
            actual.body == []

        and:
            showingsFinder.findShowings(fromDate, toDate) >> []
    }

}
