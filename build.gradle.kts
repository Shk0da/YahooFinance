plugins {
    kotlin("jvm") version "1.3.72"
}

group = "com.github.shk0da.yahoofinance"
version = "0.1"

repositories {
    mavenCentral()
}

apply(plugin = "maven")

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.fasterxml.jackson.core:jackson-databind:2.11.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.0")
    implementation("org.slf4j:jcl-over-slf4j:1.7.30")
    implementation("ch.qos.logback:logback-classic:1.2.3")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}