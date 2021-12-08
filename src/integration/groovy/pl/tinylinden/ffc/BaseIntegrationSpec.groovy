package pl.tinylinden.ffc

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MockServerContainer
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT

@SpringBootTest(webEnvironment = DEFINED_PORT)
abstract class BaseIntegrationSpec extends Specification {

    static MongoDBContainer MONGO =
            new MongoDBContainer(DockerImageName.parse("mongo:4.4.4"))

    static MockServerContainer MOCK_SERVER =
            new MockServerContainer(DockerImageName.parse("mockserver/mockserver:mockserver-5.11.2"))

    def setupSpec() {
        MONGO.start()

        MOCK_SERVER.start()
        MockServerInitializer.init(MOCK_SERVER)
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.port", BaseIntegrationSpec::mongoPort)
        registry.add("adapters.omdb.url", BaseIntegrationSpec::mockServerUrl)
    }

    private static Object mongoPort() {
        MONGO.getMappedPort(27017)
    }

    private static Object mockServerUrl() {
        "${MOCK_SERVER.host}:${MOCK_SERVER.serverPort}"
    }
}
