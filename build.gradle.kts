import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort
import org.gradle.api.plugins.quality.Checkstyle

plugins {
    id("java")
    id("application")
    checkstyle
    id("com.github.spotbugs") version "6.0.25"
    jacoco
    id("info.solidsoft.pitest") version "1.15.0"
}

application {
    mainClass = "ui.Main"
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
    implementation("com.github.spotbugs:spotbugs-annotations:4.8.6")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.easymock:easymock:5.6.0")
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

spotbugs {
    ignoreFailures = false
    showStackTraces = true
    showProgress = true
    effort = Effort.DEFAULT
    reportLevel = Confidence.DEFAULT
    maxHeapSize = "1g"
    extraArgs = listOf("-nested:false")
}

tasks.spotbugsMain {
    reports.create("html") {
        required = true
        outputLocation = layout.buildDirectory.file("reports/spotbugs/spotbugs.html")
        setStylesheet("fancy-hist.xsl")
    }
}

tasks.jacocoTestReport {
    reports {
        xml.required = false
        csv.required = false
        html.outputLocation = layout.buildDirectory.dir("reports/jacoco")
    }
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

pitest {
    targetClasses = setOf("domain.*", "ui.*")
    targetTests = setOf("domain.*", "ui.*")
    junit5PluginVersion = "1.2.1"
    threads = 4
    outputFormats = setOf("HTML")
    timestampedReports = false
    testSourceSets.set(listOf(sourceSets.test.get()))
    mainSourceSets.set(listOf(sourceSets.main.get()))
    jvmArgs.set(listOf("-Xmx1024m"))
    useClasspathFile.set(true)
    exportLineCoverage = true
}