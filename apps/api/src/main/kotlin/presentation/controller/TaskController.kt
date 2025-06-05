package taskaround.presentation.controller

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.LocalDate
import org.koin.core.annotation.Single
import taskaround.application.dto.PersonHourUpsertDto
import taskaround.application.dto.TaskUpdateDto
import taskaround.application.service.PersonHourApplicationService
import taskaround.application.service.TaskApplicationService

@Single
class TaskController(
    private val taskApplicationService: TaskApplicationService,
    private val personHourApplicationService: PersonHourApplicationService,
) {
    suspend fun update(call: RoutingCall, taskId: String) {
        val receive = call.receiveParameters()
        val taskUpdateDto = TaskUpdateDto(
            taskId = taskId,
            taskName = receive["taskName"],
            description = receive["description"],
            finishDate = receive["finishDate"],
            statusId = receive["statusId"]?.toInt(),
            accountId = receive["accountId"]?.toInt(),
        )
        taskApplicationService.update(taskUpdateDto)
        call.respond(HttpStatusCode.OK)
    }

    suspend fun savePersonHours(call: RoutingCall, taskId: String) {
        val receive = call.receiveParameters()
        val personHourUpsertDto = PersonHourUpsertDto(
            taskId = taskId,
            accountId = receive["accountId"]?.toInt()!!,
            hour = receive["hour"]!!.toFloat(),
            workedAt = LocalDate.parse(receive["workedAt"]!!),
        )
        personHourApplicationService.savePersonHour(personHourUpsertDto)
        call.respond(HttpStatusCode.OK)
    }
}
