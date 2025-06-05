package taskaround.presentation.routing.router

import io.ktor.server.application.Application
import io.ktor.server.auth.*
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.routing.routing
import taskaround.presentation.controller.ProjectController
import taskaround.presentation.routing.resource.Projects
import org.koin.ktor.ext.get as inject

fun Application.configureProjectRouting() {
    val projectController: ProjectController = inject()

    routing {
        authenticate("auth-jwt") {
            post<Projects> { projectController.create(call) }
            put<Projects> { projectController.update(call) }
        }
    }
}
