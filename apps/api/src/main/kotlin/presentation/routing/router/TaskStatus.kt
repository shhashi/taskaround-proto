package taskaround.presentation.routing.router

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import taskaround.presentation.controller.TaskStatusController
import taskaround.presentation.routing.resource.TaskStatus
import org.koin.ktor.ext.get as inject

fun Application.configureTaskStatusRouting() {
    val taskStatusController: TaskStatusController = inject()

    routing {
        authenticate("auth-jwt") {
            get<TaskStatus> { taskStatusController.list(call) }
        }
    }
}
