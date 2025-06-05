package taskaround.presentation.controller.project

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.annotation.Single
import taskaround.application.dto.ProjectAddMemberDto
import taskaround.application.dto.ProjectListDto
import taskaround.application.service.ProjectApplicationService

@Single
class ProjectMemberController(
    private val projectApplicationService: ProjectApplicationService
) {
    suspend fun add(call: RoutingCall, projectId: String) {
        val receive = call.receiveParameters()

        val accountId = receive["accountId"]

        if (accountId.isNullOrBlank()) {
            throw IllegalArgumentException("参画するアカウントIDが設定されていません")
        }

        val projectAddMemberDto = ProjectAddMemberDto(
            projectId = projectId,
            accountId = accountId.toInt(),
            roleId = "MEMBER", // 初期段階は追加メンバーは MEMBER 固定
        )
        projectApplicationService.addMember(
            projectAddMemberDto = projectAddMemberDto
        )

        call.respond(HttpStatusCode.OK)
    }

    suspend fun list(call: RoutingCall, projectId: String) {
        val projectListDto = ProjectListDto(
            projectId = projectId,
        )
        val members = projectApplicationService.listMembers(
            projectListDto = projectListDto
        )

        call.respond(HttpStatusCode.OK, members.map {
            return@map mapOf(
                "accountId" to it.id,
            )
        })
    }
}
