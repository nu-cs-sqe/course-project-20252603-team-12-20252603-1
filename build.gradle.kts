import org.gradle.api.plugins.quality.Checkstyle

plugins {
    id("java")
    checkstyle
}

group = "nu.csse.sqe"
version = "1.0"

repositories {
    mavenCentral()
}

checkstyle {
    toolVersion = "10.21.4"
    // Official Google Java Style rules bundled with Checkstyle; see
    // https://checkstyle.sourceforge.io/google_style.html
    configFile = file("config/checkstyle/google_checks.xml")
    configProperties =
        mapOf(
            "org.checkstyle.google.suppressionfilter.config" to
                file("${rootDir}/config/checkstyle/suppressions.xml").absolutePath)
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.easymock:easymock:5.4.0")
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
    jvmArgs("-Djava.awt.headless=true")
}