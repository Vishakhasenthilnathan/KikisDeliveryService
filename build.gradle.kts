plugins {
    id("java")
    id ("jacoco")
    // Apply the application plugin to add support for building a CLI application in Java.
    id ("application")
}

group = "org.eve"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.github.stefanbirkner:system-lambda:1.2.1")

}

tasks.test {
    useJUnitPlatform()
}