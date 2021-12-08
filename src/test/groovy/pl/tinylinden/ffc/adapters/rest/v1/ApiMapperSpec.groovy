package pl.tinylinden.ffc.adapters.rest.v1

import org.mapstruct.factory.Mappers
import pl.tinylinden.ffc.core.model.TestDataMother
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import java.time.format.DateTimeFormatter

class ApiMapperSpec extends Specification {

    @Shared
    @Subject
    ApiMapper tested = Mappers.getMapper(ApiMapper.class)

    def "should map Showing to Dto"() {
        given:
            def showing = TestDataMother.showing()

        when:
            def actual = tested.toDto(showing)

        then:
            actual.movieId == showing.movie.imdbId
            actual.startAt == showing.startAt.format(DateTimeFormatter.ofPattern("HH:mm"))
            actual.ticketPrice.currency == showing.ticketPrice.currency
            actual.ticketPrice.amount == showing.ticketPrice.amount.doubleValue()
    }

    def "should map ShowingsForDate to Dto"() {
        given:
            def showings = TestDataMother.showingsForDate()

        when:
            def actual = tested.toDto(showings)

        then:
            actual.date == showings.date

        and:
            actual.showings.size() == showings.showings.size()

        and:
            actual.showings[0].movieId == showings.showings[0].movie.imdbId
            actual.showings[0].startAt == showings.showings[0].startAt.format(DateTimeFormatter.ofPattern("HH:mm"))
            actual.showings[0].ticketPrice.currency == showings.showings[0].ticketPrice.currency
            actual.showings[0].ticketPrice.amount == showings.showings[0].ticketPrice.amount.doubleValue()

    }
}
