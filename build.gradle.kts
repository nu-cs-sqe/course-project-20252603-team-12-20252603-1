import org.gradle.api.plugins.quality.Checkstyle

plugins {
    id("java")
    checkstyle
    id("com.github.spotbugs") version "6.0.26"
}

group = "nu.csse.sqe"
version = "1.0"

repositories {
    mavenCentral()
}

checkstyle {
    toolVersion = "10.21.4"
    configFile = file("config/checkstyle/checkstyle.xml")
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.named("check") {
    dependsOn("spotbugsMain", "spotbugsTest")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

tasks.compileJava {
    options.release = 11
}

tasks.test {
    useJUnitPlatform()
}