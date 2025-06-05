package taskaround.presentation.routing.router.project

import io.ktor.server.application.Application
import io.ktor.server.auth.*
import io.ktor.server.resources.post
import io.ktor.server.routing.routing
import taskaround.presentation.controller.project.ProjectTaskController
import taskaround.presentation.routing.resource.Projects
import org.koin.ktor.ext.get as inject

fun Application.configureProjectTaskRouting() {
    val projectTaskController: ProjectTaskController = inject()

    routing {
        authenticate("auth-jwt") {
            post<Projects.Id.AddTask> { projectTaskController.add(call, it.parent.id) }
        }
    }
}
