package taskaround.presentation

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.http.auth.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import taskaround.config

fun AuthenticationConfig.jwtAuthentication() {
    jwt("auth-jwt") {
        // JWT 検証器
        verifier(
            JWT
                .require(Algorithm.HMAC256(config.jwt.secret))
                .build(),
        )

        // Cookie に JWT を含めているため、 Cookie から読み取りリクエストヘッダに載せた物を検証の対象とする。
        authHeader { call ->
            val cookieValue = call.request.cookies["taskaround-session"] ?: return@authHeader null
            try {
                parseAuthorizationHeader("Bearer $cookieValue")
            } catch (e: IllegalArgumentException) {
                // TODO エラーハンドリングもう少し改良したい
                null
            }
        }
        validate { credentials ->
            JWTPrincipal(credentials.payload)
        }
        challenge { _, _ ->
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}
