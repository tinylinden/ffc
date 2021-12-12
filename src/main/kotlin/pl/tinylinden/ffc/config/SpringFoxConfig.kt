package pl.tinylinden.ffc.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import springfox.documentation.swagger.web.*
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SpringFoxConfig {

    @Bean
    fun swaggerUiConfiguration(): UiConfiguration =
        UiConfigurationBuilder.builder()
            .displayOperationId(true)
            .docExpansion(DocExpansion.LIST)
            .build()

    @Bean
    @Primary
    fun swaggerResourceProvider(): SwaggerResourcesProvider =
        SwaggerResourcesProvider {
            listOf(
                swaggerResource("Fast and Furious Cinema API (v1)", "3.0.0", "/public/ffc-api.v1.yaml")
            )
        }

    private fun swaggerResource(name: String, swaggerVersion: String, location: String): SwaggerResource =
        SwaggerResource()
            .also {
                it.name = name
                it.swaggerVersion = swaggerVersion
                it.location = location
            }
}
