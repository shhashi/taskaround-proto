package taskaround.presentation.controller.account.registration

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.RoutingCall
import org.koin.core.annotation.Single
import taskaround.application.service.AccountRegistrationApplicationService

@Single
class AccountRegistrationValidationController(
    private val accountRegistrationApplicationService: AccountRegistrationApplicationService,
) {
    suspend fun validateAccountRegistrationCode(
        call: RoutingCall,
        registerCode: String,
    ) {
        val isValid = accountRegistrationApplicationService.validateAccountRegistrationCode(registerCode)
        if (isValid) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }
}
