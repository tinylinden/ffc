package pl.tinylinden.ffc.adapters.rest.v1

import io.restassured.RestAssured
import org.hamcrest.Matchers
import org.skyscreamer.jsonassert.JSONAssert
import pl.tinylinden.ffc.BaseIntegrationSpec
import spock.lang.Unroll

class ApiIntegrationSpec extends BaseIntegrationSpec {

    def "should find no showings for 2021-12-08"() {
        expect:
            RestAssured.given()
                    .accept("application/vnd.ffc.v1+json")
                    .queryParam("from", "2021-12-08")
                    .queryParam("to", "2021-12-08")
                    .then()
                    .statusCode(200)
                    .body(Matchers.is("[]"));
    }

    @Unroll
    def "should find showings between #from and #to"() {
        given:
            RestAssured.given()
                    .contentType("application/vnd.ffc.v1+json")
                    .accept("application/vnd.ffc.v1+json")
                    .pathParam("date", "2021-12-07")
                    .body(sampleShowings())
                    .put("http://localhost:8080/internal/showings/{date}")
                    .then()
                    .statusCode(202)

        expect:
            def response = RestAssured.given()
                    .accept("application/vnd.ffc.v1+json")
                    .queryParam("from", from)
                    .queryParam("to", to)
                    .get("http://localhost:8080/public/showings")
                    .then()
                    .statusCode(200)

        and:
            JSONAssert.assertEquals(expectedShowings(), response.extract().asString(), false)

        where:
            from         | to
            "2020-12-06" | "2021-12-07"
            "2020-12-07" | "2021-12-07"
            "2020-12-07" | "2021-12-08"
            "2020-12-01" | "2021-12-31"
    }

    private static String sampleShowings() {
        return """
                 |[
                 |  {
                 |    "movie_id": "tt0232500",
                 |    "start_at": "06:00",
                 |    "ticket_price": {
                 |      "currency": "EUR",
                 |      "amount": 0.99
                 |    }
                 |  }
                 |]""".stripMargin()
    }

    private static String expectedShowings() {
        return """
                 |[
                 |  {
                 |    "date": "2021-12-07",
                 |    "showings": [
                 |      {
                 |        "movie_id": "tt0232500",
                 |        "start_at": "06:00",
                 |        "ticket_price": {
                 |          "currency": "EUR",
                 |          "amount": 0.99
                 |        }
                 |      }
                 |    ]
                 |  }
                 |]""".stripMargin()
    }
}
