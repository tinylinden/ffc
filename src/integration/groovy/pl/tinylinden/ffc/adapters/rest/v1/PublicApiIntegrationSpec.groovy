package pl.tinylinden.ffc.adapters.rest.v1

import io.restassured.RestAssured
import pl.tinylinden.ffc.BaseIntegrationSpec

class PublicApiIntegrationSpec extends BaseIntegrationSpec {

    def "should return 501 status code as nothing is implemented yet"() {
        given:
            def request = RestAssured.given()
                    .accept("application/vnd.ffc.v1+json")
                    .queryParam("dateFrom", "2021-12-07")
                    .queryParam("dateTo", "2021-12-08")

        expect:
            request.get("http://localhost:8080/public/showings")
                    .then()
                    .statusCode(501);
    }
}
