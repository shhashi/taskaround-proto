package taskaround.application.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.*
import org.koin.core.annotation.Single
import taskaround.config
import java.util.*

@Single
class JwtService {
    fun createJwt(principal: UserIdPrincipal): String {
        return JWT.create()
            .withAudience("taskaround")
            .withIssuer("taskaround")
            .withClaim("userId", principal.name)
            .withExpiresAt(Date(System.currentTimeMillis() + 3600000)) // 1 hour
            .sign(Algorithm.HMAC256(config.jwt.secret))
    }
}
