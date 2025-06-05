package taskaround.presentation.routing.router

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import taskaround.presentation.controller.PersonHoursController
import taskaround.presentation.routing.resource.PersonHours
import org.koin.ktor.ext.get as inject

fun Application.configurePersonHoursRouting() {
    val personHoursController: PersonHoursController = inject()

    routing {
        authenticate("auth-jwt") {
            get<PersonHours.Account.AccountId> {
                personHoursController.list(
                    call,
                    it.accountId,
                    it.dateFrom,
                    it.dateTo
                )
            }
        }
    }
}
