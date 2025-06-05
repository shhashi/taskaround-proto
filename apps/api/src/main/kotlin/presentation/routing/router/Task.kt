package taskaround.presentation.routing.router

import io.ktor.server.application.Application
import io.ktor.server.auth.*
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.routing.routing
import taskaround.presentation.controller.TaskController
import taskaround.presentation.routing.resource.Task
import org.koin.ktor.ext.get as inject

fun Application.configureTaskRouting() {
    val taskController: TaskController = inject()

    routing {
        authenticate("auth-jwt") {
            put<Task.TaskId> { taskController.update(call, it.taskId) }
            post<Task.TaskId.PersonHours> { taskController.savePersonHours(call, it.parent.taskId) }
        }
    }
}
