import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.7"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.0"
	kotlin("plugin.spring") version "1.6.0"
	kotlin("kapt") version "1.6.0"
	groovy
	id("com.coditory.integration-test") version "1.2.1"
	id("com.adarshr.test-logger") version "3.1.0"
	id("org.openapi.generator") version "5.3.0"
}

group = "pl.tinylinden"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2020.0.4"
extra["testcontainersVersion"] = "1.15.2"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation("io.springfox:springfox-boot-starter:3.0.0")
	implementation("javax.validation:validation-api:2.0.0.Final")
	implementation("com.atlassian.oai:swagger-request-validator-springmvc:2.23.0")
	implementation("io.leangen.graphql:graphql-spqr-spring-boot-starter:0.0.6")
	implementation("org.mapstruct:mapstruct:1.4.2.Final")

	kapt("org.mapstruct:mapstruct-processor:1.4.2.Final")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.rest-assured:rest-assured:4.4.0")
	testImplementation("org.skyscreamer:jsonassert:1.5.0")
	testImplementation("org.mock-server:mockserver-client-java:5.11.2")
	// spock
	testImplementation("org.codehaus.groovy:groovy:3.0.9")
	testImplementation("org.spockframework:spock-core:2.0-groovy-3.0")
	testImplementation("org.spockframework:spock-spring:2.0-groovy-3.0")
	// testcontainers
	testImplementation("org.testcontainers:testcontainers:${property("testcontainersVersion")}")
	testImplementation("org.testcontainers:spock:${property("testcontainersVersion")}")
	testImplementation("org.testcontainers:mongodb:${property("testcontainersVersion")}")
	testImplementation("org.testcontainers:mockserver:${property("testcontainersVersion")}")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
		mavenBom("org.spockframework:spock-bom:2.0-groovy-3.0")
	}
}

tasks.withType<KotlinCompile> {
	dependsOn("openApiGenerate")
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

configure<SourceSetContainer> {
	named("main") {
		java.srcDir("$buildDir/generated/sources/openApiGenerate/kotlin/main")
	}
}

openApiGenerate {
	generatorName.set("kotlin-spring")
	inputSpec.set("$rootDir/src/main/resources/static/public/ffc-api.v1.yaml")
	outputDir.set("$buildDir/generated/sources/openApiGenerate/kotlin/main")
	ignoreFileOverride.set("$rootDir/.openapi-generator-ignore")
	apiPackage.set("pl.tinylinden.ffc.adapters.rest.v1")
	modelPackage.set("pl.tinylinden.ffc.adapters.rest.v1.model")
	modelNameSuffix.set("Dto")
	additionalProperties.set(
		mapOf(
			"exceptionHandler" to "false",
			"interfaceOnly" to "true",
			"sourceFolder" to "/",
			"useTags" to "true"
		)
	)
}