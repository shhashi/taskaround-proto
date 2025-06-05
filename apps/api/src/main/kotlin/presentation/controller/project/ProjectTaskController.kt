package taskaround.presentation.controller.project

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.annotation.Single
import taskaround.application.dto.TaskCreateDto
import taskaround.application.service.TaskApplicationService

@Single
class ProjectTaskController(
    private val taskApplicationService: TaskApplicationService,
) {
    suspend fun add(call: RoutingCall, projectId: String) {
        val receive = call.receiveParameters()

        val taskCreateDto = TaskCreateDto(
            projectId = projectId,
            taskName = receive["taskName"]!!,
            description = receive["description"],
            finishDate = receive["finishDate"],
            statusId = receive["statusId"]!!.toInt(),
        )
        val createdTask = taskApplicationService.create(taskCreateDto)
        call.respond(HttpStatusCode.OK, mapOf("taskId" to createdTask))
    }
}
