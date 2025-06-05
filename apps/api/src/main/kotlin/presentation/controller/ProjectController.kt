package taskaround.presentation.controller

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.annotation.Single
import taskaround.application.dto.ProjectCreateDto
import taskaround.application.dto.ProjectUpdateDto
import taskaround.application.service.ProjectApplicationService

@Single
class ProjectController(
    private val projectApplicationService: ProjectApplicationService
) {
    suspend fun create(call: RoutingCall) {
        val receive = call.receiveParameters()
        val principal = call.principal<JWTPrincipal>()

        val projectId = receive["id"]
        val name = receive["name"]

        if (projectId.isNullOrBlank()) {
            throw IllegalArgumentException("プロジェクトIDが設定されていません")
        }
        if (name.isNullOrBlank()) {
            throw IllegalArgumentException("プロジェクト名が設定されていません")
        }

        val project = ProjectCreateDto(
            projectId = projectId,
            projectName = name,
            createdByAccountId = principal!!.payload.claims["accountId"]!!.asString().toInt(),
        )

        val createdProject = projectApplicationService.create(project)
        return call.respond(createdProject)
    }

    suspend fun update(call: RoutingCall) {
        val receive = call.receiveParameters()

        val projectId = receive["id"]
        val name = receive["name"]

        if (projectId.isNullOrBlank()) {
            throw IllegalArgumentException("プロジェクトIDが設定されていません")
        }
        if (name.isNullOrBlank()) {
            throw IllegalArgumentException("プロジェクト名が設定されていません")
        }

        val project = ProjectUpdateDto(
            projectId = projectId,
            projectName = name,
        )
        val updatedProject = projectApplicationService.update(project)
        return call.respond(updatedProject)
    }
}
