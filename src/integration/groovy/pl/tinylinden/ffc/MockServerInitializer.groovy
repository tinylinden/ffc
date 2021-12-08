package pl.tinylinden.ffc

import org.mockserver.client.MockServerClient
import org.mockserver.model.MediaType
import org.testcontainers.containers.MockServerContainer
import pl.tinylinden.ffc.adapters.omdb.OmdbDataMother

import static org.mockserver.model.HttpRequest.request
import static org.mockserver.model.HttpResponse.response

class MockServerInitializer {

    static configureExpectations(MockServerContainer mockServer) {
        def client = new MockServerClient(mockServer.getHost(), mockServer.getServerPort())

        client.when(
                request()
                        .withMethod("GET")
                        .withPath("/")
                        .withQueryStringParameter("i", "tt0232500")
        ).respond(
                response()
                        .withStatusCode(200)
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(OmdbDataMother.fullResponse())
        )
    }
}
