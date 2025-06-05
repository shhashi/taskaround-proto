package taskaround.presentation.controller

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.annotation.Single
import taskaround.application.repository.TaskStatusApplicationRepository

@Single
class TaskStatusController(
    private val taskStatusApplicationRepository: TaskStatusApplicationRepository
) {
    suspend fun list(call: RoutingCall) {
        val fetchedTaskStatusFetchDto = taskStatusApplicationRepository.fetchTaskStatuses()
        call.respond(HttpStatusCode.OK, fetchedTaskStatusFetchDto.status)
    }
}
