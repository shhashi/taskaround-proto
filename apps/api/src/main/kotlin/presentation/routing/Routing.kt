package taskaround.presentation.routing

import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import taskaround.presentation.routing.router.account.configureAccountRegistrationRouting
import taskaround.presentation.routing.router.account.register.configureAccountRegistrationValidationRouting
import taskaround.presentation.routing.router.configurePersonHoursRouting
import taskaround.presentation.routing.router.configureProjectRouting
import taskaround.presentation.routing.router.configureTaskRouting
import taskaround.presentation.routing.router.configureTaskStatusRouting
import taskaround.presentation.routing.router.project.configureProjectMemberRouting
import taskaround.presentation.routing.router.project.configureProjectTaskRouting

fun Application.configureRouting() {
    install(Resources)
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }

    configureAccountRegistrationValidationRouting()
    configureAccountRegistrationRouting()
    configureProjectRouting()
    configureProjectMemberRouting()
    configureProjectTaskRouting()
    configureTaskRouting()
    configureTaskStatusRouting()
    configurePersonHoursRouting()
}
