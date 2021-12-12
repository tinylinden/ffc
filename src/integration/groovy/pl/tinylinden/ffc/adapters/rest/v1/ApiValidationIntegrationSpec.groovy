package pl.tinylinden.ffc.adapters.rest.v1

import io.restassured.RestAssured
import org.hamcrest.Matchers
import pl.tinylinden.ffc.BaseIntegrationSpec
import spock.lang.Unroll

class ApiValidationIntegrationSpec extends BaseIntegrationSpec {

    @Unroll
    def "should not allow to ask for movie details by invalid id"() {
        expect:
            RestAssured.given()
                    .accept("application/vnd.ffc.v1+json")
                    .pathParam("id", id)
                    .get("http://localhost:8080/public/movies/{id}/details")
                    .then()
                    .statusCode(400)
                    .body("messages[0].key", Matchers.is("validation.request.parameter.schema.pattern"))
                    .body("messages[0].message", Matchers.startsWith("ECMA 262 regex"))

        where:
            id << ["5433138", "tt", "totally-invalid"]
    }

    @Unroll
    def "should not allow to post rating out of range"() {
        expect:
            RestAssured.given()
                    .auth().preemptive().basic("elmo", "secret")
                    .contentType("application/vnd.ffc.v1+json")
                    .accept("application/vnd.ffc.v1+json")
                    .pathParam("id", "tt0232500")
                    .body(sampleRating(rating))
                    .post("http://localhost:8080/public/movies/{id}/ratings")
                    .then()
                    .statusCode(400)
                    .body("messages[0].message", Matchers.startsWith("[Path '/rating'] Numeric instance is"))

        where:
            rating << [-1, 0, 6, 100]
    }

    private static String sampleRating(int rating) {
        return """
                 |{
                 |  "rating": ${rating}
                 |}""".stripMargin()
    }
}
