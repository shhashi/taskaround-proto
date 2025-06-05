package taskaround

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
import com.sksamuel.hoplite.sources.EnvironmentVariablesPropertySource
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.tomcat.jakarta.*
import taskaround.infrastructure.DatabaseFactory
import taskaround.presentation.routing.configureRouting
import taskaround.shared.config.Configuration

// 設定ファイルの読込み
val config =
    ConfigLoaderBuilder
        .default()
        .addResourceSource("/application.yaml")
        .addSource(
            EnvironmentVariablesPropertySource(
                useUnderscoresAsSeparator = true,
                allowUppercaseNames = true,
            ),
        ).build()
        .loadConfigOrThrow<Configuration>()

fun main(args: Array<String>) {
    embeddedServer(
        Tomcat,
        port = config.application.port,
        module = Application::module,
    ).start(wait = true)
}

fun Application.module() {
    // データベース接続
    DatabaseFactory.init()

    configureSerialization()
    configureHTTP()
    configureSecurity()
    configureFrameworks()
    configureMonitoring()
    configureRouting()
}
