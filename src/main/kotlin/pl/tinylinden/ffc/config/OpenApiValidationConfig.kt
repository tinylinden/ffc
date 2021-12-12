package pl.tinylinden.ffc.config

import com.atlassian.oai.validator.springmvc.OpenApiValidationFilter
import com.atlassian.oai.validator.springmvc.OpenApiValidationInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.core.io.support.EncodedResource
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.nio.charset.StandardCharsets.UTF_8
import javax.servlet.Filter

@Configuration
class OpenApiValidationConfig {

    @Bean
    fun openApiValidationFilter(): Filter =
        OpenApiValidationFilter(true, false)

    @Bean
    fun openApiValidationInterceptor(
        @Value("classpath:/static/public/ffc-api.v1.yaml") specification: Resource
    ): WebMvcConfigurer =
        object : WebMvcConfigurer {
            override fun addInterceptors(registry: InterceptorRegistry) {
                registry.addInterceptor(interceptor(specification))
            }

            private fun interceptor(specification: Resource) =
                OpenApiValidationInterceptor(EncodedResource(specification, UTF_8))
        }
}