plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("kapt") version "1.9.10"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "1.9.25"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("mysql:mysql-connector-java:8.0.33")

	// mapstruct
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")

	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.3")
	implementation("io.projectreactor:reactor-core:3.6.2")

	// influx db
	implementation("com.influxdb:influxdb-client-kotlin:6.10.0")
	implementation("com.influxdb:flux-dsl:6.10.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

noArg {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.Embeddable")
	annotation("jakarta.persistence.MappedSuperclass")
}

kapt {
	correctErrorTypes = true
}

tasks.withType<Test> {
	useJUnitPlatform()
}

val installLocalGitHook = tasks.register<Copy>("installLocalGitHook") {
	from("${rootProject.rootDir}/.github/hooks")
	into(File("${rootProject.rootDir}/.git/hooks"))

	// Set executable permissions for hook files
	eachFile {
		mode = "755".toInt(radix = 8)
	}

	doLast {
		println("✅ Git hooks installed successfully!")
		println("   - prepare-commit-msg: Provides commit message template")
		println("   - commit-msg: Validates conventional commit format")
	}
}

tasks.build {
	dependsOn(installLocalGitHook)
}
