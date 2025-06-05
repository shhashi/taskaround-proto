package taskaround

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import taskaround.presentation.jwtAuthentication

fun Application.configureSecurity() {
    install(Authentication) {
        jwtAuthentication()
    }
}
