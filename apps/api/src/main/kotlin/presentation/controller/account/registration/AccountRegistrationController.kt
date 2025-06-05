package taskaround.presentation.controller.account.registration

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.annotation.Single
import taskaround.application.dto.AccountDto
import taskaround.application.service.AccountRegistrationApplicationService
import taskaround.application.service.JwtService

@Single
class AccountRegistrationController(
    private val accountRegistrationApplicationService: AccountRegistrationApplicationService,
    private val jwtService: JwtService,
) {
    suspend fun registerAccount(
        call: RoutingCall,
        registerCode: String,
        loginId: String,
        password: String,
        accountName: String,
    ) {
        val accountDto = AccountDto(
            accountName = accountName,
            loginId = loginId,
            password = password,
        )

        val registeredAccount = accountRegistrationApplicationService.registerAccount(registerCode, accountDto)
        if (registeredAccount != null) {
            // JWT 発行
            val jwt = jwtService.createJwt(UserIdPrincipal(name = registeredAccount.accountId.toString()))

            // JWT を cookie に設定
            call.response.cookies.append(
                "taskaround-session",
                jwt,
                maxAge = 100000,
                httpOnly = true,
                secure = false,
                extensions = mapOf(
                    "SameSite" to "Lax",
                ),
                path = "/",
            )
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
