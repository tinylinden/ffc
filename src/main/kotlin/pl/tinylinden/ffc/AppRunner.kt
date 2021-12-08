package pl.tinylinden.ffc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@ConfigurationPropertiesScan("pl.tinylinden.ffc")
@EnableFeignClients
class AppRunner

fun main(args: Array<String>) {
	runApplication<AppRunner>(*args)
}
