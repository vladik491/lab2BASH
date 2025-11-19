import org.gradle.jvm.toolchain.JavaLanguageVersion
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

application {
    mainClass.set("by.gstu.project.cli.App")
}

dependencies {
    implementation(project(":module-core"))
    implementation("org.fusesource.jansi:jansi:2.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("")
    manifest.attributes["Main-Class"] = "by.gstu.project.cli.App"
}

tasks.named("jar") {
    enabled = false
}

tasks.named("build") {
    dependsOn("shadowJar")
}

// Добавьте эти строки для разрешения конфликта
tasks.named("distZip") {
    dependsOn("shadowJar")
}

tasks.named("distTar") {
    dependsOn("shadowJar")
}

tasks.named("startScripts") {
    dependsOn("shadowJar")
    var classpath = files(tasks.named<ShadowJar>("shadowJar"))
}