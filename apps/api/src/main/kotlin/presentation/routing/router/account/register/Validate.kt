package taskaround.presentation.routing.router.account.register

import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import taskaround.presentation.controller.account.registration.AccountRegistrationValidationController
import taskaround.presentation.routing.resource.Account
import org.koin.ktor.ext.get as inject

fun Application.configureAccountRegistrationValidationRouting() {
    val accountRegistrationValidationController: AccountRegistrationValidationController = inject()

    routing {
        get<Account.Registration.Validation> {
            accountRegistrationValidationController.validateAccountRegistrationCode(
                call = call,
                registerCode = it.parent.registerCode,
            )
        }
    }
}
