import org.flywaydb.gradle.task.AbstractFlywayTask

val flyway_version: String by project
val postgresql_version: String by project

buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-database-postgresql:11.8.0")
        classpath("org.postgresql:postgresql:42.4.5")
    }
}

plugins {
    java
    id("org.flywaydb.flyway") version "11.8.0"
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.flywaydb:flyway-core:$flyway_version")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:$flyway_version")
    runtimeOnly("org.postgresql:postgresql:$postgresql_version")
}

flyway {
    url = "${System.getenv("FLYWAY_URL")}"
    user = "${System.getenv("FLYWAY_USER")}"
    password = "${System.getenv("FLYWAY_PASSWORD")}"
}

/**
 * Flyway タスクは Configuration cache 機能と互換性がないため、 Flyway タスクでは構成キャッシュを利用しないように指定。
 */
tasks.withType(AbstractFlywayTask::class.java) {
    notCompatibleWithConfigurationCache("Could NOT execute flyway task.")
}
