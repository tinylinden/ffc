package pl.tinylinden.ffc.adapters.rest.v1

import io.restassured.RestAssured
import pl.tinylinden.ffc.BaseIntegrationSpec

class InternalApiIntegrationSpec extends BaseIntegrationSpec {

    def "should return 501 status code as nothing is implemented yet"() {
        given:
            def body = """
                                |[
                                |  {
                                |    "movie_id": "tt0232500",
                                |    "start_at": "13:30",
                                |    "ticket_price": {
                                |      "currency": "EUR",
                                |      "amount": 4.20
                                |    }
                                |  }
                                |]""".stripMargin()

        and:
            def request = RestAssured.given()
                    .contentType("application/vnd.ffc.v1+json")
                    .accept("application/vnd.ffc.v1+json")
                    .pathParam("date", "2021-12-07")
                    .body(body)

        expect:
            request.put("http://localhost:8080/internal/showings/{date}")
                    .then()
                    .statusCode(501);
    }
}
