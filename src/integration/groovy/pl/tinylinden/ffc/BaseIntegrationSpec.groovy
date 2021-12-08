package pl.tinylinden.ffc

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT

@SpringBootTest(webEnvironment = DEFINED_PORT)
abstract class BaseIntegrationSpec extends Specification {

    static MongoDBContainer MONGO =
            new MongoDBContainer(DockerImageName.parse("mongo:4.4.4"))

    def setupSpec() {
        MONGO.start()
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.port", BaseIntegrationSpec::mongoPort)
    }

    private static Object mongoPort() {
        MONGO.getMappedPort(27017)
    }
}
