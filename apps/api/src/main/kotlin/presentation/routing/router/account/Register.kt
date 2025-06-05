package taskaround.presentation.routing.router.account

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.receiveParameters
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.routing
import taskaround.presentation.controller.account.registration.AccountRegistrationController
import taskaround.presentation.routing.resource.Account
import kotlin.text.isNullOrBlank
import org.koin.ktor.ext.get as inject

fun Application.configureAccountRegistrationRouting() {
    val accountRegistrationController: AccountRegistrationController = inject()

    routing {
        post<Account.Registration> { registration ->
            val receive = call.receiveParameters()
            if (
                !receive["loginId"].isNullOrBlank() &&
                !receive["password"].isNullOrBlank() &&
                !receive["accountName"].isNullOrBlank()
            ) {
                accountRegistrationController.registerAccount(
                    call = call,
                    registerCode = registration.registerCode,
                    loginId = receive["loginId"]!!,
                    password = receive["password"]!!,
                    accountName = receive["accountName"]!!,
                )
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
